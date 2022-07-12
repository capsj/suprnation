package suprnation

import java.io.BufferedReader
import java.io.File

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Try

trait Reader[S, O] {
  def readLines(source: S): Either[InputError, O]
}

trait ReaderP[S, O] {
  def readLine(source: S): Either[InputError, O]
}

/** Read triangle lines from StdIn */
class StdInReaderP extends ReaderP[Unit, String] {
  def readLine(u: Unit = ()): Either[InputError, String] = {
    Try(StdIn.readLine())
      .fold(
        fa = _ => Left(UnexpectedInputError),
        fb = Right(_))
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
          Parser.rawStringToRow(line) match {
            case Left(_) if readLines.nonEmpty => Right(readLines)
            case Right(row) => inner(readLines :+ row)
            case _ => Left(ParsingError)
          }
        case _ => Left(BadSource)
      }

    inner()
  }
}
