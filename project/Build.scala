import sbt._
import sbt.Keys._
import play.Project._
import com.typesafe.sbt.SbtAtmosPlay.atmosPlaySettings
import org.sbtidea.SbtIdeaPlugin._
import net.litola.SassPlugin
import LibVersions._

/**
 * this files is intended to build the main project
 * it contains links to all dependencies that are needed
 * */
object ApplicationBuild extends Build with Macro with GraphORM with SemanticData with Collaboration //with DenigmaActors
{

  override def graphORMPath  = "./graph-orm"
  override def semanticDataAppPath = "./semantic-data"
  override   def appCollaborationPath = "./collaboration"

  val testOptions = "-Dconfig.file=conf/" + Option(System.getProperty("test.config")).getOrElse("application") + ".conf"

  val appName         = "denigma"
  val appVersion      = "0.03"

  val appDependencies = Seq(
    ///Add your project dependencies here,


     "com.assembla.scala-incubator" % "graph-core_2.10" % scalaGraphVersion
  )

  //play.Project.playScalaSettings ++ SassPlugin.sassSettings

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here

    scalaVersion:=scalaVer,

    scalacOptions ++= Seq("-feature", "-language:_"),

    parallelExecution in Test := false,

    organization := "org.denigma",

    coffeescriptOptions := Seq("native", "/usr/local/bin/coffee -p"),

    javaOptions in Test += testOptions,

    scalacOptions in Test += testOptions

  ).settings( play.Project.playScalaSettings ++
    SassPlugin.sassSettings ++
    Seq(SassPlugin.sassOptions := Seq("--compass", "-r", "compass","-r", "semantic-ui-sass", "-r","singularitygs")):_* )
    .dependsOn(macroses).dependsOn(graphORM).dependsOn(collaboration).dependsOn(semanticData)




}

trait Collaboration
{
  val appCollaborationName         = "collaboration"
  val appCollaborationVersion      = "0.02"

  def appCollaborationPath = "."


  val appCollaborationDependencies = Seq(
    ///Add your project dependencies here,


    ///Scala-graph for inmemory graph work
    "com.assembla.scala-incubator" % "graph-core_2.10" %  scalaGraphVersion,

    ///Scala-graph json to serialize inmemory graph work
    //"com.assembla.scala-incubator" % "graph-json_2.10" % scalaGraphVersion,

    ///Akka actors for async stuff
    "com.typesafe.akka" % "akka-testkit_2.10" % "2.2.0",

    /// Parboiled: PEG parser for org.denigma.semantic quering
    "org.parboiled" % "parboiled-scala_2.10" % parboiledVersion,


    ///ScalaTest for testing
    "org.scalatest" % "scalatest_2.10" % scalaTestVersion//,

  )




  lazy val collaboration = play.Project(appCollaborationName, appCollaborationVersion, appCollaborationDependencies, path = file(this.appCollaborationPath)).settings(
    // Add your own project settings here

    scalaVersion := scalaVer,


    //compiler options
    scalacOptions ++= Seq( "-feature", "-language:_" ),

    sourceDirectory in Compile <<= baseDirectory / (src+"/main/scala"),
    sourceDirectory in Test <<= baseDirectory / (src+"/test/scala"),

    scalaSource in Compile <<= baseDirectory / (src+"/main/scala"),
    scalaSource in Test <<= baseDirectory / (src+"/test/scala"),

    javaSource in Compile <<= baseDirectory / (src+"/main/java"),
    javaSource in Test <<= baseDirectory / (src+"/test/java"),


    parallelExecution in Test := false,

    organization := "org.denigma"

  )
}

trait GraphORM {

  val graphORMName         = "graph-orm"
  val graphORMVersion      = "0.01"

  def graphORMPath = "."

  val graphORMDependencies = Seq(


    "com.github.nscala-time" % "nscala-time_2.10" % scalaTimeVersion,

    "com.tinkerpop.blueprints" % "blueprints-core" % blueprintsVersion,

    //"com.tinkerpop.blueprints" % "blueprints-orient-graph" % blueprintsVersion,

    "com.tinkerpop.blueprints" % "blueprints-neo4j-graph" % blueprintsVersion ,

    "io.github.nremond" %% "pbkdf2-scala" % pbkdf2Version,

    "com.github.t3hnar" % "scala-bcrypt_2.10" % bcryptVersion,

    "org.apache.commons" % "commons-io" % apacheCommonsVersion,

    "org.scalacheck" % "scalacheck_2.10" % scalaCheckVersion
  )

  lazy val graphORM = play.Project(graphORMName, graphORMVersion, graphORMDependencies, path = file(this.graphORMPath)).settings(
    // Add your own project settings here

    scalaVersion := scalaVer,


    //compiler options
    scalacOptions ++= Seq("-feature", "-language:_" ),

    sourceDirectory in Compile <<= baseDirectory / (src+"/main/scala"),
    sourceDirectory in Test <<= baseDirectory / (src+"/test/scala"),

    scalaSource in Compile <<= baseDirectory / (src+"/main/scala"),
    scalaSource in Test <<= baseDirectory / (src+"/test/scala"),

    javaSource in Compile <<= baseDirectory / (src+"/main/java"),
    javaSource in Test <<= baseDirectory / (src+"/test/java"),


    parallelExecution in Test := false,

    organization := "org.denigma"

  )
}

trait SemanticData {

  val semanticDataAppName         = "semantic-data"
  val semanticDataAppVersion      = "0.01"

  def semanticDataAppPath = "."

  val semanticDataAppDependencies = Seq(
    //"com.bigdata" % "bigdata" % "1.3.0"


    "org.openrdf.sesame" % "sesame" % bigDataSesame
  )


  lazy val semanticData = play.Project(semanticDataAppName, semanticDataAppVersion, semanticDataAppDependencies, path = file(this.semanticDataAppPath)).settings(
    // Add your own project settings here

    scalaVersion := scalaVer,


    //compiler options
    scalacOptions ++= Seq( "-feature", "-language:_" ),

    sourceDirectory in Compile <<= baseDirectory / (src+"/main/scala"),
    sourceDirectory in Test <<= baseDirectory / (src+"/test/scala"),

    scalaSource in Compile <<= baseDirectory / (src+"/main/scala"),
    scalaSource in Test <<= baseDirectory / (src+"/test/scala"),

    javaSource in Compile <<= baseDirectory / (src+"/main/java"),
    javaSource in Test <<= baseDirectory / (src+"/test/java"),


    parallelExecution in Test := false,

    organization := "org.denigma"

  )

}

trait Macro{



  lazy val macroses = Project("macro", file("macro") ) settings(

    scalaVersion:=scalaVer,

    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-reflect" % _),

    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _)

    //libraryDependencies += "org.scala-lang" % "scala-reflect" % scala


  )
}


object LibVersions {

  val orientVersion = "1.6.2"

  //val titanVersion = "0.3.2"

  val blueprintsVersion = "2.4.0"//"2.5.0-SNAPSHOT"

  def src = "src"



  //val scalaTest = "2.0.M8"

  val scalaGraphVersion = "1.7.2"

  val scalaVer = "2.10.3"

  val parboiledVersion = "1.1.6"

  val bcryptVersion = "2.3"

  val pbkdf2Version = "0.2"

  val scalaTimeVersion = "0.6.0"

  val apacheCommonsVersion = "1.3.2"

  val scalaCheckVersion = "1.11.0"

  val picklingVersion = "0.8.0-SNAPSHOT"

  val jScalaVersion ="0.3"

  //val bigDataVersion = "1.3.0" //BIGDATA doesnot support latest Sesame version


  val bigDataSesame = "2.6.10" //BIGDATA doesnot support latest Sesame version

  val scalaTestVersion ="2.0"

}


