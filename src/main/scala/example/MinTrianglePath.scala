package example

import scala.annotation.tailrec
import scala.io.StdIn

object MinTrianglePath extends App {

  @tailrec
  /** Read lines from StdIn and parse them as rows of Int of increasing size */
  def readLines(previousLines: Seq[Seq[Int]]): Seq[Seq[Int]] = {
    val rowOpt: Option[Seq[Int]] =
      StdIn.readLine() match {
        case null => None
        case "" => None
        case other =>
          Option(
            other.split(" ")
              .map(_.toInt)
              .toSeq)
      }

    rowOpt match {
      case Some(row) =>
        val lastRow = previousLines.lastOption.getOrElse(Seq.empty)
        val expectedSize = lastRow.size + 1

        /* We are still getting values, keep going */
        if(row.size == expectedSize) readLines(previousLines :+ row)
        /* Not what we expected, break with an exception for now */
        else throw new Exception("Bad input")

      case None => previousLines
    }
  }

  println("Please input the desired triangle: ")
  val triangle = readLines(Seq.empty)
  println(triangle)
}
