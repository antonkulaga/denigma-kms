package models


import com.tinkerpop.blueprints.Direction
import models.graphs._
import models.graphs.constraints._
import models.graphs.constraints.StringOf
import models.graphs.constraints.DateTimeOf
import models.graphs.constraints.HashOf
import models.graphs.constraints.OutLinkOf


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


object City extends NodeType("City")
{
  must have StringOf("name")
  should have OutLinkOf("situated_in")
  //should have OutLinkOf("LiveIn","City")
}

object Country extends NodeType("Country")
{
  must have StringOf("name")
}
