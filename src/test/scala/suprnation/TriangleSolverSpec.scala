package suprnation

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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

    val mockReader = new Reader[Unit, Triangle] {
      override def readLines(source: Unit): Either[InputError, Triangle] = Right(triangle)
    }

    new TriangleSolverF[Unit](mockReader).minPath() shouldEqual Right(expectedPath)
  }

  it should "be able to produce the minimal path for a 500-row triangle" in {
    new TriangleSolverF[String](FileReader).minPath("resources/500_triangle.txt") shouldBe a[Right[_, _]]
  }
}
