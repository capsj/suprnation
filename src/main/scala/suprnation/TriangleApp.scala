package suprnation

object TriangleApp extends App {

  println("Please input the desired triangle: ")
  val result: Solver[Unit, Path] => String =
    _.minPath()
      .fold(
        fa = err => s"No minimal path could be found due to $err",
        fb = p => s"Minimal path is: ${p.prettyString}")

  println(result(new TriangleSolverF[Unit](StdInReader)))
}
