package org.denigma.graphs

/**
 * Created by antonkulaga on 12/24/13.
 */
case class Link(label:String,from:String,to:String,props:Map[String,Any]=Map.empty[String,Any],types:Seq[String]=List.empty[String], id:String="") extends LinkLike
