package models.graphs


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

}

object GP extends GraphParams{

}


trait GraphConsts{
  val sys = "__"
  val ID = sys+"id"
  val TYPE = sys+"type"

  val ROOT = "root"
  val NAME = "name"
  val USER = "user"
}