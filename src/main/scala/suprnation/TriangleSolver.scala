package suprnation

import scala.annotation.tailrec

trait Solver[I, O] {
  def minPath: I => Either[SolutionError, O]
  def maxPath: I => Either[SolutionError, O]
}

object TriangleSolver extends Solver[Triangle, Path] {

  def possiblePaths(input: Triangle): Either[SolutionError, List[Path]] = {
    @tailrec
    def inner(
      triangle: Triangle,
      accPaths: List[Path] = List.empty
    ): Either[SolutionError, List[Path]] = {
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

  def minPath: Triangle => Either[SolutionError, Path] =
    triangle => possiblePaths(triangle).map(_.minBy(_.sum))

  def maxPath: Triangle => Either[SolutionError, Path] =
    triangle => possiblePaths(triangle).map(_.maxBy(_.sum))
}
