package suprnation.util

import scala.util.Random

/** Helper object to generate a 500-row triangle */
object TriangleGenerator extends App {

  import java.io._

  def writeFile(filename: String, lines: List[List[Int]]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- lines) {
      bw.write(line.mkString(" ") + "\n")
    }
    bw.close()
  }

  val lines: List[List[Int]] =
    List.range(1, 500).map({
      i => List.fill(i)(Random.between(1, 15))
    })

  writeFile("resources/500_triangle.txt", lines)
}
