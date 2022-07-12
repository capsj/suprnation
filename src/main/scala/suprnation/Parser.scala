package suprnation

import scala.util.Failure
import scala.util.Success
import scala.util.Try

object Parser {

  def stringToEitherIntList: String => Either[InputError, List[Int]] = {
    raw =>
      Try(
        raw.split(" ")
          .map(_.toInt)
          .toList) match {
        case Success(row) => Right(row)
        case Failure(_) => Left(ParsingError)
      }
  }
}
