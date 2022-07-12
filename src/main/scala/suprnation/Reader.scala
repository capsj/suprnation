package suprnation

import java.io.BufferedReader
import java.io.File

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Try

trait Reader[S, O] {
  def readLines(source: S): Either[InputError, O]
}

/** Read triangle lines from a StdIn */
object StdInReader extends Reader[Unit, Triangle] {
  def readLines(u: Unit = ()): Either[InputError, Triangle] = {
    @tailrec
    def inner(previousLines: Triangle): Either[InputError, Triangle] = {
      val rowE: Either[InputError, List[Int]] =
        StdIn.readLine() match {
          case "" | null => Right(List.empty)
          case other => Parser.stringToEitherIntList(other)
        }

      rowE match {
        case Right(Nil) => Right(previousLines)

        case Right(row) =>
          val lastRow = previousLines.lastOption.getOrElse(List.empty)
          val expectedSize = lastRow.size + 1

          if(row.size == expectedSize) inner(previousLines :+ row)
          else Left(TriangleFormatError)

        case Left(err) => Left(err)
      }
    }

    inner(List.empty)
  }
}

/** Read triangle lines from a file */
object FileReader extends Reader[String, Triangle] {
  def readLines(source: String): Either[InputError, Triangle] = {
    val readerE: Either[InputError, BufferedReader] =
      Try({
        val file = new File(source)
        new BufferedReader(new java.io.FileReader(file))
      }).fold(
        fa = _ => Left(BadSource),
        fb = Right(_))

    @tailrec
    def inner(readLines: List[List[Int]] = List.empty): Either[InputError, Triangle] =
      (for {
        br <- readerE
        line <-
          Try(br.readLine())
            .fold(
              fa = _ => Right(""),
              fb = Right(_))
      } yield line) match {
        case Right(line) =>
          Parser.stringToEitherIntList(line) match {
            case Left(_) if readLines.nonEmpty => Right(readLines)
            case Right(row) => inner(readLines :+ row)
            case _ => Left(ParsingError)
          }
        case _ => Left(BadSource)
      }

    inner()
  }
}
