import org.denigma.graphs.SemanticGraph
import org.specs2.specification.SpecificationName
import play.Play
import io.github.nremond.PBKDF2
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import org.specs2.mutable._

/*some basic repeated function*/
trait SemanticSpec extends Specification{

  def prepareGraph: SemanticGraph = {
    Play.application().isTest must beTrue
    val sg = new SemanticGraph()
    sg.url must contain ("/db/test")
    sg.clearLocalDb()
    //sg.g.commit()
    val g = sg.g
    //sg.initIndexes()
    sg
  }


}
