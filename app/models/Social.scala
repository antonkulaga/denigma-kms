package models


import com.tinkerpop.blueprints.Direction
import graphs.core._
import graphs.schemes.constraints._
import graphs.schemes._
import graphs.schemes.LinkType
import graphs.schemes.OutLinkOf


object Created extends LinkType("Created")
{
  must have DateTimeOf("created")
}

object User extends NodeType(GP.USER)
{
  val username: StringOf =  must be StringOf("username")
  val password: HashOf = must be HashOf("password")
  val email: StringOf = must be StringOf("email","""\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,8}\b""")

  val liveIn: Link = should be OutLinkOf("LiveIn",City.name)
  val role: Link = should be OutLinkOf(Role.name)

//  must have StringOf("username") have HashOf("password")  have StringOf("email")
//  should have OutLinkOf("LiveIn",City.name)
//  should have OutLinkOf(Role.name)
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

