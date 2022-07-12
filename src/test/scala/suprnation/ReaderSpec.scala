package suprnation

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReaderSpec extends AnyFlatSpec with Matchers {

  behavior of "Reader"

  it should "result in an empty list if the specified file can't be found" in {
    FileReader.readLines("bad-filename.txt") shouldBe Left(BadSource)
  }
}
