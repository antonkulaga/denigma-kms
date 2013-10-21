package models


import com.tinkerpop.blueprints.Direction
import models.graphs._
import models.graphs.StringOf
import models.graphs.DateTimeOf
import models.graphs.HashOf
import models.graphs.OutLinkOf


object Created extends LinkType("Created")
{
  must have DateTimeOf("created")
}

object User extends NodeType(GP.USER)
{
  must have StringOf("username") have HashOf("password")  have StringOf("email")
  should have OutLinkOf("LiveIn",City.name)
  should have OutLinkOf(Role.name)
}

trait CreatedByUser extends NodeType{
  must have OutLinkOf(Created.name,Created.name,GP.USER)
}

case class UserLinkOf(lname:String,lType:String="") extends Link(lname, Direction.OUT,lType,User.name)


object City extends NodeType(GP.USER)
{
  must have StringOf("name")
  //should have OutLinkOf("LiveIn","City")
}