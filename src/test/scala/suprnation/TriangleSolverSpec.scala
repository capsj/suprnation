package suprnation

import java.io.BufferedReader
import java.io.File

import scala.util.Try

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import suprnation.TriangleFromFile.fileSolver
import suprnation.TriangleFromFile.readerE

class TriangleSolverSpec extends AnyFlatSpec with Matchers {
  val triangle: Triangle =
    List(7) ::
      List(6, 3) ::
      List(3, 8, 5) ::
      List(10, 2, 11, 9) ::
      Nil

  behavior of "TriangleSolver"

  it should "correctly produce a minimal path" in {
    val expectedPath: Path =
      Path(
        sum = 18,
        nodes = 7 :: 6 :: 3 :: 2 :: Nil)

    val mockReaderP: Reader[Unit, String] =
      new Reader[Unit, String] {
        var elements = triangle

        def readLine(u: Unit): Either[InputError, String] = {
          elements match {
            case Nil => Right("")
            case head :: tail =>
              val res = head.mkString(" ")
              elements = tail
              Right(res)
          }
        }
      }

    val mockParser = TriangleRowParser

    new TriangleSolver(mockReaderP, mockParser).minPath(()) shouldEqual Right(expectedPath)
  }

  it should "be able to produce the minimal path for a 500-row triangle" in {
    val file = new File("resources/500_triangle.txt")
    val br = new BufferedReader(new java.io.FileReader(file))

    new TriangleSolver[BufferedReader, String](FileReader, TriangleRowParser)
      .minPath(br) shouldBe a[Right[_, _]]
  }
}
