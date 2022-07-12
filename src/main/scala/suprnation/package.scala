package object suprnation {

  type Node = Int
  type Triangle = List[List[Node]]

  case class Path(sum: Int, nodes: Seq[Node]) {
    def addNode(value: Int): Path =
      copy(
        sum = sum + value,
        nodes = nodes :+ value)

    def prettyString: String =
      s"${nodes.mkString(" + ")} = $sum"
  }
}
