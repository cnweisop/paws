import play.Project._

name := "users"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.3.174",
  "com.typesafe.play" %% "play" % "2.2.1",
  "org.sorm-framework" % "sorm" % "0.3.12",
  "ws.securesocial" %% "securesocial" % "2.1.3"
)

playScalaSettings