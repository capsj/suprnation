package suprnation

import java.io.BufferedReader
import java.io.File

import scala.util.Try

object TriangleApp extends App {
  val solver: TriangleSolver[Unit, String] =
    new TriangleSolver[Unit, String](StdInReader, TriangleRowParser)

  println("Please input the desired triangle: ")
  println(
    solver.minPath(()).fold(
      fa = err => s"No minimal path could be found due to $err",
      fb = p => s"Minimal path is: ${p.prettyString}"))
}

object TriangleFromFile extends App {

  val readerE: Either[InputError, BufferedReader] =
    Try({
      val file = new File("resources/500_triangle.txt")
      new BufferedReader(new java.io.FileReader(file))
    }).fold(
      fa = _ => Left(BadSource),
      fb = Right(_))

  val fileSolver: TriangleSolver[BufferedReader, String] =
    new TriangleSolver[BufferedReader, String](
      FileReader,
      TriangleRowParser)
  val minPath =
    for {
      br <- readerE
      path <- fileSolver.minPath(br)
    } yield path

  println(minPath.fold(
    fa = err => s"No minimal path could be found due to $err",
    fb = p => s"Minimal path is: ${p.prettyString}"))
}
