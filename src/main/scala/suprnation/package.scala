package object suprnation {

  type Node = Int
  type Row = List[Node]
  type Triangle = List[Row]

  case class Path(sum: Int, nodes: Seq[Node]) {
    def addNode(value: Int): Path =
      copy(
        sum = sum + value,
        nodes = nodes :+ value)

    def prettyString: String =
      s"${nodes.mkString(" + ")} = $sum"
  }

  sealed trait TriangleAppError

  sealed trait InputError extends TriangleAppError
  case object ParsingError extends InputError
  case object TriangleFormatError extends InputError
  case object UnexpectedInputError extends InputError
  case object EmptyError extends InputError
  case object BadSource extends InputError

  sealed trait SolutionError extends TriangleAppError
  case object EmptyInput extends SolutionError
}
