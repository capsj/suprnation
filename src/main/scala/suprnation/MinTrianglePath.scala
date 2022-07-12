package suprnation

import scala.annotation.tailrec
import scala.io.StdIn

object MinTrianglePath extends App {

  @tailrec
  /** Read lines from StdIn and parse them as rows of Int of increasing size */
  def readLines(previousLines: List[List[Int]]): Triangle = {
    val rowOpt: Option[List[Int]] =
      StdIn.readLine() match {
        case null => None
        case "" => None
        case other =>
          Option(
            other.split(" ")
              .map(_.toInt)
              .toList)
      }

    rowOpt match {
      case Some(row) =>
        val lastRow = previousLines.lastOption.getOrElse(List.empty)
        val expectedSize = lastRow.size + 1

        /* We are still getting values, keep going */
        if(row.size == expectedSize) readLines(previousLines :+ row)
        /* Not what we expected, break with an exception for now */
        else throw new Exception("Bad input")

      case None => previousLines
    }
  }

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
        val tuples: List[(Int, Int)] = elem.zip(elem.tail)
        val combinations: List[((Int, Int), Path)] = tuples.zip(accPaths)
        val computedPaths: List[Path] =
          combinations.flatMap({
            case ((left, right), path) =>
              path.addStep(left) ::
                path.addStep(right) ::
                Nil
          })
        computePaths(tail, computedPaths)
    }
  }

  println("Please input the desired triangle: ")
  val triangle = readLines(List.empty)
  println(triangle)
  val paths = computePaths(triangle)
  println(paths)
  val minPath = paths.minBy(_.sum)
  println(s"Minimal path is: ${minPath.prettyString}")
  val maxPath = paths.maxBy(_.sum)
  println(s"Maximal path is: ${maxPath.prettyString}")
}
