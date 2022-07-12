package suprnation

import scala.annotation.tailrec

trait Solver[I, O] {
  val reader: Reader[I, Triangle]

  def minPath(input: I): Either[TriangleAppError, Path]
  def maxPath(input: I): Either[TriangleAppError, O]
}

class TriangleSolverF[I](val reader: Reader[I, Triangle]) extends Solver[I, Path] {
  def possiblePaths(input: Triangle): Either[TriangleAppError, List[Path]] = {
    @tailrec
    def inner(
               triangle: Triangle,
               accPaths: List[Path] = List.empty
             ): Either[TriangleAppError, List[Path]] = {
      triangle match {
        case Nil if accPaths.nonEmpty => Right(accPaths)
        case Nil => Left(EmptyInput)

        case (root :: Nil) :: tail =>
          inner(tail, List(Path(root, List(root))))

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
          inner(tail, computedPaths)
      }
    }

    inner(input)
  }

  def minPath(input: I): Either[TriangleAppError, Path] =
    for {
      triangle <- reader.readLines(input)
      paths <- possiblePaths(triangle)
    } yield paths.minBy(_.sum)

  def maxPath(input: I): Either[TriangleAppError, Path] =
    for {
      triangle <- reader.readLines(input)
      paths <- possiblePaths(triangle)
    } yield paths.maxBy(_.sum)
}
