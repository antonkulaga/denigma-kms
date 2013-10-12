package models

import com.tinkerpop.blueprints._
import scala.collection.JavaConversions._
import models.graphs.SG

object Menu {


  def addMenu(g:Graph) = {
    val v = g.addVertex(null,null)
    //g.getIndex("name",classOf[Vertex]).put("menu",vv)
    v.setProperty("name","menu")
    val child:String = "child"
    for(x:Int <- 0 to 10)
    {
      val v2 =g.addVertex(null,null)
      v2.setProperty("name",s"subitem $x")
      v.addEdge(child,v2)
      for(y:Int <-1 to 10)
      {
        val v3 = g.addVertex(null,null)
        v3.setProperty("name",s"subitem $y")
        v2.addEdge(child,v3)
      }
    }
    //v.save()
    g

  }

  def getMenuRoot(menu:String = "denigma") = {

    val g  = SG.g
    val iter = g.getVertices("name","menu").iterator()
    val v = if(iter.hasNext) iter.next() else {
      val vv = g.addVertex(null,null)
      //g.getIndex("name",classOf[Vertex]).put("menu",vv)
      vv.setProperty("name","menu")
      //vv.save()

      vv
    }
    v

  }

  def clear = {
    val g = SG.g

  }

  def get(mv:String): Menu = {

    val g = SG.g


    val v = getMenuRoot()
    getMenu(v)
  }


  def getMenu(v:Vertex):Menu= {

    val child:String = "child"
    Menu(v.getProperty("name"),v.getVertices(Direction.OUT,child).map(e=>this.getMenu(e)).toList)
  }

}

case class Menu(name:String,children:List[Menu])  {
  def items = children.map(c=>c.name)
}
