package suprnation

import java.io.ByteArrayInputStream

import scala.util.Try

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParserSpec extends AnyFlatSpec with Matchers {

  behavior of "TriangleRowParser"

  it should "accept valid triangles" in {
    val triangle: Triangle =
      List(7) ::
        List(6, 3) ::
        List(3, 8, 5) ::
        List(10, 2, 11, 9) ::
        Nil

    triangle
      .zipWithIndex
      .map({
        case (row, 0) =>
          TriangleRowParser.parse(_.size == 1)(row.mkString(" "))

        case (row, i) =>
          val expectedSize: Node =
            Try(triangle(i - 1).size)
              .toOption
              .getOrElse(0) + 1
          val rowString: String = row.mkString(" ")

          TriangleRowParser.parse(_.size == expectedSize)(rowString)
      })
      .filter(_.isLeft) shouldBe empty
  }

  it should "reject invalid triangles" in {
    val invalidTriangle: Triangle =
      List(7) ::
        List(6, 3) ::
        List(3, 8, 5) ::
        List(2, 11, 9) ::
        Nil

    invalidTriangle
      .zipWithIndex
      .map({ case (row, i) =>
        val expectedSize: Node =
          Try(invalidTriangle(i - 1).size)
            .toOption
            .getOrElse(0)

        TriangleRowParser.parse(_.size == expectedSize)(row.mkString(" "))
      }) should contain(Left(TriangleFormatError))
  }
}
