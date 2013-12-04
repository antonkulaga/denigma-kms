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
object ApplicationBuild extends Build with LibVersions with Macro
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

    "org.apache.commons" % "commons-io" % apacheCommonsVersion,

     "com.assembla.scala-incubator" % "graph-core_2.10" % scalaGraphVersion,

      "org.scalacheck" % "scalacheck_2.10" % scalaCheckVersion

   //   "org.scala-lang" %% "scala-pickling" % picklingVersion,


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

    scalacOptions ++= Seq("-feature", "-language:_"),

    parallelExecution in Test := false,

    organization := "org.denigma",

    coffeescriptOptions := Seq("native", "/usr/local/bin/coffee -p"),

    javaOptions in Test += testOptions,

    scalacOptions in Test += testOptions

  ).settings( play.Project.playScalaSettings ++
    SassPlugin.sassSettings ++
    Seq(SassPlugin.sassOptions := Seq("--compass", "-r", "compass","-r", "semantic-ui-sass", "-r","singularitygs")):_* )
    .dependsOn(macroses)


}

trait Macro{



  lazy val macroses = Project("macro", file("macro") ) settings(

    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-reflect" % _),

    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _)

    //libraryDependencies += "org.scala-lang" % "scala-reflect" % scala


  )
}


trait LibVersions {

  val orientVersion = "1.5.1"

  //val titanVersion = "0.3.2"

  val blueprintsVersion = "2.4.0"//"2.5.0-SNAPSHOT"


  //val scalaTest = "2.0.M8"

  val scalaGraphVersion = "1.7.0"

  val scala = "2.10.3"

  val parboiledVersion = "1.1.6"

  val bcryptVersion = "2.3"

  val pbkdf2Version = "0.2"

  val scalaTimeVersion = "0.6.0"

  val apacheCommonsVersion = "1.3.2"

  val scalaCheckVersion = "1.11.0"

  val picklingVersion = "0.8.0-SNAPSHOT"

}


