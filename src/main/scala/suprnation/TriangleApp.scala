package suprnation

object TriangleApp extends App {

  println("Please input the desired triangle: ")
  val result: (Reader[Unit, Triangle], Solver[Triangle, Path]) => String = {
    (reader, solver) =>
      (for {
        triangle <- reader.readLines()
        minPath <- solver.minPath(triangle)
      } yield minPath)
        .fold(
          fa = err => s"No minimal path could be found due to $err",
          fb = p => s"Minimal path is: ${p.prettyString}")
  }

  println(result(StdInReader, TriangleSolver))
}
