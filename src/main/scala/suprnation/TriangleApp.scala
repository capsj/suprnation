package suprnation

object TriangleApp extends App {

  println("Please input the desired triangle: ")
//  val result: Solver[Unit, Triangle] => String =
//    _.minPath(())
//      .fold(
//        fa = err => s"No minimal path could be found due to $err",
//        fb = p => s"Minimal path is: ${p.prettyString}")
//
//  println(result(new TriangleSolver[Unit](StdInReader)))


  println(
    new TriangleSolver[Unit, String](
      new StdInReaderP,
      TriangleRowParser
    ).minPath(()).fold(
      fa = err => s"No minimal path could be found due to $err",
      fb = p => s"Minimal path is: ${p.prettyString}"))
}
