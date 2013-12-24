package org.denigma.graphs

import play.api.{Logger, Plugin}

/**
 * Neo4j plugin to stop server.
 * @author : bsimard
 */
class GraphPlugin(app: play.api.Application) extends Plugin {

  override def onStart{
    Logger.debug("Starting HYPERGRAPH plugin")
    val g = SG.sg.g
  }

  override def onStop(){
    Logger.debug("Stopping HYPERGRAPH plugin")
    SG.sg.g.shutdown()
  }
}
