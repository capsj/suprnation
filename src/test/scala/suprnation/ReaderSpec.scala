package suprnation

import java.io.ByteArrayInputStream

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReaderSpec extends AnyFlatSpec with Matchers {

  behavior of "StdInReader"

  it should "accept valid triangles" in {
    val triangle: Triangle =
      List(7) ::
        List(6, 3) ::
        List(3, 8, 5) ::
        List(10, 2, 11, 9) ::
        Nil
    val stringInput: String =
      triangle
        .map(_.mkString(" "))
        .mkString("\n")
    val in: ByteArrayInputStream =
      new ByteArrayInputStream(stringInput.getBytes)
    val readerP: ReaderP[Unit, String] =
      new StdInReaderP

    Console.withIn(in) {
      triangle.foreach(r =>
        readerP.readLine(()) shouldBe Right(r.mkString(" ")))
    }
  }

  behavior of "FileReader"

  it should "indicate if the specified file couldn't be found" in {
    FileReader.readLines("bad-filename.txt") shouldBe Left(BadSource)
  }
}
