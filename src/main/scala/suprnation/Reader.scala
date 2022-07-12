package suprnation

import java.io.BufferedReader
import java.io.File

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Failure
import scala.util.Success
import scala.util.Try

trait Reader[S, O] {
  def readLine(source: S): Either[InputError, O]
}

/** Read triangle lines from StdIn */
object StdInReader extends Reader[Unit, String] {
  def readLine(u: Unit = ()): Either[InputError, String] = {
    Try(StdIn.readLine()) match {
      case Success(null) => Right("")
      case Success(other) => Right(other)
      case Failure(_) => Left(UnexpectedInputError)
    }
  }
}

/** Read String lines from a file */
object FileReader extends Reader[BufferedReader, String] {
  def readLine(source: BufferedReader): Either[InputError, String] =
    Try(source.readLine()) match {
      case Success(null) => Right("")
      case Success(other) => Right(other)
      case Failure(_) => Left(UnexpectedInputError)
    }
}
