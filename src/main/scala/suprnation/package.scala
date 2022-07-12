package object suprnation {

  type Triangle = List[List[Int]]

  case class Path(sum: Int, values: Seq[Int]) {
    def addStep(value: Int): Path =
      copy(
        sum = sum + value,
        values = values :+ value)

    def prettyString: String =
      s"${values.mkString(" + ")} = $sum"
  }
}
