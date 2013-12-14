package models

/*
There I store all types connected to work with content
 */

import graphs.schemes.constraints._
import graphs.schemes._

object Page extends NodeType("Page") with CreatedByUser
{
  val title = must be StringOf("title")
  val text = must be TextOf("text")
  val published = must be DateTimeOf("published")

  //  must have OutLinkOf(Created.name,Created.name,GP.USER)
}

object Comment extends NodeType("Post") with CreatedByUser
{
  must have StringOf("text")
  must have DateTimeOf("published")
  should have StringOf("title")
  should have OutLinkOf("Previous",Previous.name)
  //  must have OutLinkOf(Created.name,Created.name,GP.USER)
}

object Previous extends NodeType("Previous") with CreatedByUser
{
   should have OutLinkOf("context")
}

object Discussion extends NodeType("Post") with CreatedByUser
{
    must have StringOf("title")
}