package org.denigma.graphs.viewmodels

import com.tinkerpop.blueprints.Vertex

import org.denigma.graphs.SG
import SG._
/*
class used to convert hyperedges to json
 */
case class EdgeViewModel(label:String, v:Vertex) extends Container(v.id,v) {


 }
