package org.denigma.graphs

import scala.collection.immutable.Map

/**
 * class for semantic Graph features

Trait to serialize links to
*/
trait LinkLike{
  val label:String
  val from:String
  val to:String
  val props:Map[String,Any]
  val id:String
  val types:Seq[String]
  def hasVertex = id!=""
}
