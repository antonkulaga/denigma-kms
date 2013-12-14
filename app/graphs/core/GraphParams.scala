package graphs.core

/*
to keep constants

TODO: refactor in future
 */
trait GraphParams extends GraphConsts{
  val CONTEXT = sys+"context"
  val MUST =sys+"must"
  val SHOULD = sys+"should"
  val VERSION = sys+"version"

  val PROPERTY = sys+"property"
  val LINK = sys+"link"

  val DEFAULT = sys+"default"

  val CONSTRAINT = sys+"constraint"

  val OF_TYPE = sys+"subClassOf"
  val CAPTION = sys+"caption"

}

object GP extends GraphParams{

}


trait GraphConsts{
  val sys = "__"
  val ID = sys+"id"
  val TYPE = sys+"type"
  val TITLE = sys+"title"


  val ROOT = "root"
  val NAME = "name"
  val USER = "user"
  val PRIORITY = "priority"
}