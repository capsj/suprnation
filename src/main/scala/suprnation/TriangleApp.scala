package suprnation

object TriangleApp extends App {
  import TriangleOperations._

  println("Please input the desired triangle: ")
  val triangle = readLines()
  val paths = computePaths(triangle)

  println(
    minPath(triangle)
      .fold(
        ifEmpty = s"No minimal path could be found for $triangle")(
        f = p => s"Minimal path is: ${p.prettyString}"))

  println(
    maxPath(triangle)
      .fold(
        ifEmpty = s"No minimal path could be found for $triangle")(
        f = p => s"Maximal path is: ${p.prettyString}"))
}
