package suprnation

import scala.annotation.tailrec
import scala.util.Try

object TriangleSolver {

  @tailrec
  def computePaths(
    triangle: Triangle,
    accPaths: List[Path] = List.empty
  ): List[Path] = {
    triangle match {
      case Nil => accPaths

      case (root :: Nil) :: tail =>
        computePaths(tail, List(Path(root, List(root))))

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
        computePaths(tail, computedPaths)
    }
  }

  val minPath: Triangle => Option[Path] =
    triangle => Try(computePaths(triangle).minBy(_.sum)).toOption

  val maxPath: Triangle => Option[Path] =
    triangle => Try(computePaths(triangle).maxBy(_.sum)).toOption
}
