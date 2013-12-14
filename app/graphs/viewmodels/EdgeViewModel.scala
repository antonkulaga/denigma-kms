package graphs.viewmodels

import com.tinkerpop.blueprints.Vertex

/**
 * Created by antonkulaga on 12/13/13.
 */
case class EdgeViewModel(iid:String, val label:String, v:Vertex) extends Container(iid,v) {


 }
