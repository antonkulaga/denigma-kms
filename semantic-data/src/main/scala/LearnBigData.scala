import com.bigdata.rdf.model._
import com.bigdata.rdf.sail._
import java.io.File
import scala.util.Try
import com.bigdata.rdf.model._
import com.bigdata.rdf.sail._

object LearnBigData extends App {
  // use one of our pre-configured option-sets or "modes"
  //val properties =loadProperties("fullfeature.properties")

  val journal = File.createTempFile("bigdata", ".jnl");


//  properties.setProperty(
//    BigdataSail.Options.FILE,
//    journal.getAbsolutePath()
//  )

  // instantiate a sail and a Sesame repository
  val sail = new BigdataSail()
  val repo = new BigdataSailRepository(sail)

  val con: BigdataSailRepositoryConnection = repo.getConnection


  val sub: BigdataResource  = new BigdataURIImpl("http://www.bigdata.com/rdf#Mike")
  val pred:BigdataURI = new BigdataURIImpl("http://www.bigdata.com/rdf#loves")
  val obj:BigdataValue = new BigdataURIImpl("http://www.bigdata.com/rdf#RDF")

  val st=  new BigdataStatementImpl(sub,pred,obj,pred,StatementEnum.Explicit,true)


  //con.setAutoCommit(false)

  Try {
    con.commit()
  }.orElse{case ex=>
    println("exception")

  }

  println("hello big data")

}
