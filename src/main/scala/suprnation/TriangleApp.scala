package suprnation

object TriangleApp extends App {

  println("Please input the desired triangle: ")
  val result: String =
    (for {
      triangle <- StdInReader.readLines(List.empty)
      minPath <- TriangleSolver.minPath(triangle).toRight(EmptyError)
    } yield minPath)
      .fold(
        fa = err => s"No minimal path could be found due to $err",
        fb = p => s"Minimal path is: ${p.prettyString}")
  println(result)
}
