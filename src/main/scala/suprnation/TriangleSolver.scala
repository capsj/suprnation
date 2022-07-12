package suprnation

import scala.annotation.tailrec

trait Solver[I, M] {
  val reader: Reader[I, M]

  def minPath: I => Either[TriangleAppError, Path]
  def maxPath: I => Either[TriangleAppError, Path]
}

class TriangleSolver[I](val reader: Reader[I, Triangle]) extends Solver[I, Triangle] {

  @tailrec
  private def possiblePaths(
    triangle: Triangle,
    accPaths: List[Path] = List.empty
  ): Either[TriangleAppError, List[Path]] = {
    triangle match {
      case Nil if accPaths.nonEmpty => Right(accPaths)
      case Nil => Left(EmptyInput)

      case (root :: Nil) :: tail =>
        possiblePaths(tail, List(Path(root, List(root))))

      case elem :: tail =>
        val tuples: List[(Node, Node)] = elem.zip(elem.tail)
        val combinations: List[((Node, Node), Path)] = tuples.zip(accPaths)
        val computedPaths: List[Path] =
          combinations.flatMap({
            case ((left, right), path) =>
              path.addNode(left) ::
                path.addNode(right) ::
                Nil
          })
        possiblePaths(tail, computedPaths)
    }
  }

  def paths(input: I): Either[TriangleAppError, List[Path]] =
    for {
      triangle <- reader.readLines(input)
      paths <- possiblePaths(triangle)
    } yield paths

  def minPath: I => Either[TriangleAppError, Path] =
    paths(_).map(_.minBy(_.sum))

  def maxPath: I => Either[TriangleAppError, Path] =
    paths(_).map(_.maxBy(_.sum))
}

class TriangleSolverP[I, M, O](
  reader: ReaderP[I, M],
  parser: Parser[M, Row]
) {
  def paths(input: I): Either[TriangleAppError, List[Path]] = {
    @tailrec
    def loop(accPaths: List[Path] = List.empty): Either[TriangleAppError, List[Path]] = {
      (for {
        line <- reader.readLine(input)
        lastRow = accPaths.lastOption.map(_.nodes).getOrElse(List.empty)
        expectedSize = lastRow.size + 1
        row <- parser.parse(_.size == expectedSize)(line)
      } yield row) match {
        case Right(Nil) => Right(accPaths)

        case Right(row) =>
          val tuples: List[(Node, Node)] = row.zip(row.tail)
          val combinations: List[((Node, Node), Path)] = tuples.zip(accPaths)
          val computedPaths: List[Path] =
            combinations.flatMap({
              case ((left, right), path) =>
                path.addNode(left) ::
                  path.addNode(right) ::
                  Nil
            })
          loop(computedPaths)

        case Left(err) => Left(err)
      }
    }

    for {
      rawRoot <- reader.readLine(input)
      rootRow <- parser.parse(_.size == 1)(rawRoot)
      paths <- loop(rootRow.map(Path(_, rootRow)))
    } yield paths
  }

  def minPath: I => Either[TriangleAppError, Path] =
    paths(_).map(_.minBy(_.sum))

  def maxPath: I => Either[TriangleAppError, Path] =
    paths(_).map(_.maxBy(_.sum))
}
