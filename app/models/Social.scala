package models


import com.tinkerpop.blueprints.Direction

import org.denigma.graphs.schemes._
import org.denigma.graphs.schemes.constraints.{HashOf, DateTimeOf, TextOf, StringOf}
import org.denigma.graphs.SG._
import org.denigma.graphs.core.GP

object Created extends LinkType("Created")
{
  must have DateTimeOf("created")
}


object User extends NodeType(GP.USER)
{
  val username: StringOf =  must be StringOf("username")
  val password: HashOf = must be HashOf("password")
  val email: StringOf = must be StringOf("email","""\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,8}\b""")

  val liveIn: LinkOf = should be OutLinkOf("LiveIn",City.name)
  val role: LinkOf = should be OutLinkOf(Role.name)

//  must have StringOf("username") have HashOf("password")  have StringOf("email")
//  should have OutLinkOf("LiveIn",City.name)
//  should have OutLinkOf(Role.name)
}



trait CreatedByUser extends NodeType{
  val created = must be OutLinkOf(Created.name,Created.name,GP.USER)
}

case class UserLinkOf(lname:String,lType:String="") extends LinkOf(lname, Direction.OUT,lType,User.name)


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

