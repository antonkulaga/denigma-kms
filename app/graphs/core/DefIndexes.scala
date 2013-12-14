package graphs.core

import com.tinkerpop.blueprints.{IndexableGraph, Vertex, Index}


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
    ids = index(GP.ID,fulltext = false,graph)
    names = index(GP.NAME,fulltext = true,graph)
    users = index(GP.USER,fulltext = true,graph)
    types = index(GP.TYPE,fulltext = false,graph)
    roots = index(GP.ROOT,fulltext = false,graph)
  }
}