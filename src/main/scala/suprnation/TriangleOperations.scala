package suprnation

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Success
import scala.util.Try

object TriangleOperations {

  @tailrec
  /** Read lines from StdIn and parse them as rows of Int of increasing size */
  def readLines(previousLines: List[List[Int]] = List.empty): Triangle = {
    val rowOpt: Option[List[Int]] =
      StdIn.readLine() match {
        case null => None
        case "" => None
        case other =>
          Try(
            other.split(" ")
              .map(_.toInt)
              .toList)
            .toOption
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
        row <- Try(line.split(" ").map(_.toInt).toList)
      } yield readLines :+ row) match {
        case Success(lines) => inner(lines)
        case _ => readLines
      }

    inner()
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
