import sbt._
import sbt.Keys._
import play.Project._
import com.typesafe.sbt.SbtAtmosPlay.atmosPlaySettings
import org.sbtidea.SbtIdeaPlugin._
import net.litola.SassPlugin


/**
 * this files is intended to build the main project
 * it contains links to all dependencies that are needed
 * */
object ApplicationBuild extends Build with LibVersions
{

  val testOptions = "-Dconfig.file=conf/" + Option(System.getProperty("test.config")).getOrElse("application") + ".conf"

  val appName         = "denigma"
  val appVersion      = "0.03"

  val appDependencies = Seq(
    ///Add your project dependencies here,

    "com.github.nscala-time" % "nscala-time_2.10" % scalaTimeVersion,

    "com.tinkerpop.blueprints" % "blueprints-core" % blueprintsVersion,

    //"com.tinkerpop.blueprints" % "blueprints-orient-graph" % blueprintsVersion,

    "com.tinkerpop.blueprints" % "blueprints-neo4j-graph" % blueprintsVersion ,

    "io.github.nremond" %% "pbkdf2-scala" % pbkdf2Version,

    "com.github.t3hnar" % "scala-bcrypt_2.10" % bcryptVersion,

    "org.apache.commons" % "commons-io" % "1.3.2"

    //    "com.thinkaurelius.titan" % "titan-core" % titanVersion,
    //    "com.thinkaurelius.titan" % "titan-cassandra" % titanVersion,
    //    "com.thinkaurelius.titan" % "titan-berkeleyje" % titanVersion,
    //    "com.thinkaurelius.titan" % "titan-es" % titanVersion,
    //    "com.thinkaurelius.titan" % "titan-lucene" % titanVersion,
  )
  //play.Project.playScalaSettings ++ SassPlugin.sassSettings

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here

    scalaVersion:="2.10.3",

    parallelExecution in Test := false,

    organization := "org.denigma",

    javaOptions in Test += testOptions,

    scalacOptions in Test += testOptions

  ).settings( play.Project.playScalaSettings ++ SassPlugin.sassSettings ++ Seq(SassPlugin.sassOptions := Seq("--compass", "-r", "compass","-r", "zurb-foundation", "-r","susy")):_* )
}


trait LibVersions {

  val orientVersion = "1.5.1"

  val titanVersion = "0.3.2"

  val blueprintsVersion = "2.5.0-SNAPSHOT"
  //"2.4.0"

  //val scalaTest = "2.0.M8"

  val scalaGraphVersion = "1.6.1"

  val scala = "2.10.3"

  val parboiledVersion = "1.1.6"

  val bcryptVersion = "2.3"

  val pbkdf2Version = "0.2"

  val scalaTimeVersion = "0.6.0"

}


