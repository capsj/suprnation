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

  it should "correctly compute a minimal path" in {
    val expectedPath: Path =
      Path(
        sum = 18,
        nodes = 7 :: 6 :: 3 :: 2 :: Nil)

    TriangleSolver.minPath(triangle) shouldEqual Some(expectedPath)
  }

  it should "be able to compute the minimal path for a 500-row triangle" in {
    val largeTriangle: Triangle =
      Reader.readFile("resources/500_triangle.txt")

    TriangleSolver.minPath(largeTriangle) shouldBe a[Some[_]]
  }
}
