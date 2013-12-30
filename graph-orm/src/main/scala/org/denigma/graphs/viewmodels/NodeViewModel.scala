package org.denigma.graphs.viewmodels

import com.tinkerpop.blueprints.Vertex
import org.denigma.graphs.SG._


case class NodeViewModel(v:Vertex) extends Container(v.id,v) {


  lazy val incoming = v.allInV.filter(_._2.isLink).map(kv=>EdgeViewModel(kv._1,kv._2))
  lazy val outgoing = v.allOutV.filter(_._2.isLink).map(kv=>EdgeViewModel(kv._1,kv._2))
  //lazy val both = v.allV.filter(_._2.isLink).map(kv=>EdgeViewModel(kv._2.id,kv._1,kv._2))



}

