package models.graphs

import com.tinkerpop.blueprints.{IndexableGraph, Vertex, Index}

trait GraphConsts{
  val sys = "__"
  val ID = sys+"id"
  val TYPE = sys+"type"
  val ROOT = "root"
}

trait DefIndexes[T<: IndexableGraph] extends GraphConsts{

  var ids: Index[Vertex] = null
  var names: Index[Vertex] = null
  var users: Index[Vertex] = null
  var types: Index[Vertex] = null
  var roots: Index[Vertex] = null

  def index(name:String,fulltext:Boolean, graph:T): Index[Vertex]

  /*
  will not work without it
   */
  protected def initIndexes(graph:T) = {
    ids = index(ID,fulltext = false,graph)
    names = index("name",fulltext = true,graph)
    users = index("user",fulltext = true,graph)
    types = index("type",fulltext = false,graph)
    roots = index(ROOT,fulltext = false,graph)
  }
}