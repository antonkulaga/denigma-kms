package models
import models.graphs._
import models.graphs.constraints.{NodeType, LinkType, StringOf, DateTimeOf}


object Organization extends NodeType("Organization")
{
  must have StringOf("name")
  should have UserLinkOf("member")
  should have UserLinkOf("founder")
}

object Role extends LinkType("RoleIn")
{
  must have StringOf("name")
  should have StringOf("description")
  should have DateTimeOf("started")
  should have DateTimeOf("finished")

}
//
//Page:
//
//must have prop title
//must have prop text
//must have link  typeof created to typeof author
//must have datetime when published
//should have link lang to typeof page
//
//
//User
//
//must have passw_hash
//must have username
//must have email
//
//should have link typeof (live in)
//
//live in should have link typeof city that has link typeof country
//
//Project
//
//must have name
//must have link created by author
//must have description
//should have In tasks
//
//Task
//
//should have status
//should have assigned
//must have completed
//
//Quest is relationship (from person or org)
