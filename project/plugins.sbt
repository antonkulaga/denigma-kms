// Comment to get more information during initialization
//logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

resolvers += "com.bigdata" at "http://systap.com/maven/releases"

resolvers += "Ansvia Releases Repo" at "http://scala.repo.ansvia.com/releases/"

resolvers += "The New Motion Repository" at "http://nexus.thenewmotion.com/content/repositories/releases-public"

resolvers += "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

addSbtPlugin("net.litola" % "play-sass" % "0.3.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-atmos-play" % "0.3.2")

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"



resolvers += Resolver.sonatypeRepo("snapshots")

