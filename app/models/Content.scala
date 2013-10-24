package models

/*
There I store all types connected to work with content
 */

import models.graphs.constraints.{NodeType, OutLinkOf, StringOf, DateTimeOf}

object Page extends NodeType("Page") with CreatedByUser
{
  must have StringOf("title") have StringOf("text")
  must have DateTimeOf("published")
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