import sbt.Keys._

name := "db"

version := "1.0"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.3.174",
  "com.typesafe.play" %% "play" % "2.2.1",
  "org.sorm-framework" % "sorm" % "0.3.12"
)