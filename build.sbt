name := "denigma-kms"

version := "0.21"

scalaVersion := "2.10.3"

resolvers ++= Seq(
    "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
    "Ansvia repo" at "http://scala.repo.ansvia.com/releases/",
    "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/"
    )

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)
