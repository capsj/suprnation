package suprnation

import java.io.BufferedReader
import java.io.File

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Try

trait Reader[F[_, _], S, O] {
  def readLines(source: S): F[InputError, O]
}

object StdInReader extends Reader[Either, Triangle, Triangle] {

  @tailrec
  def readLines(previousLines: Triangle): Either[InputError, Triangle] = {
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

        if(row.size == expectedSize) readLines(previousLines :+ row)
        else Left(TriangleFormatError)

      case Left(err) => Left(err)
    }
  }
}

object FileReader extends Reader[Either, String, Triangle] {
  override def readLines(source: String): Either[InputError, Triangle] = {
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
            case left => Left(ParsingError)
          }
        case left => Left(BadSource)
      }

    inner()
  }
}
