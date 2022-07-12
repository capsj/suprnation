package suprnation

import scala.util.Try

trait Parser[I, O] {
  def parse(condition: O => Boolean): I => Either[InputError, O]
}

/** Translate [[String]]s to [[Row]]s */
object TriangleRowParser extends Parser[String, Row] {
  def parse(condition: Row => Boolean): String => Either[InputError, Row] =
    raw => {
      Try(
        raw match {
          case "" => List.empty
          case other =>
            other.split(" ")
              .map(_.toInt)
              .toList
        })
        .fold(
          fa = _ => Left(ParsingError),
          fb = Right(_))
        .filterOrElse(
          p = row => condition(row) || row.isEmpty,
          zero = TriangleFormatError)
    }
}
