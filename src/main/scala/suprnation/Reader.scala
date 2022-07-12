package suprnation

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Success
import scala.util.Try

object Reader {

  @tailrec
  def readLines(
    previousLines: List[List[Int]] = List.empty
  ): Either[InputError, Triangle] = {
    val rowE: Either[InputError, List[Int]] =
      StdIn.readLine() match {
        case null => Right(List.empty)
        case "" => Right(List.empty)
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

  /** Read lines from a file and parse them as rows of Int */
  def readFile(filename: String): List[List[Int]] = {
    val brTry =
      Try {
        val file = new File(filename)
        new BufferedReader(new FileReader(file))
      }

    @tailrec
    def inner(readLines: List[List[Int]] = List.empty): List[List[Int]] =
      (for {
        br <- brTry
        line <- Try(br.readLine())
        row <- Parser.stringToIntList(line)
      } yield readLines :+ row) match {
        case Success(lines) => inner(lines)
        case _ => readLines
      }

    inner()
  }
}
