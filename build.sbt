import sbt._

import play.Project._

name := "paws"

version := "1.0-SNAPSHOT"

playScalaSettings

lazy val users = project.in(file("modules/users"))

lazy val paws = project.in(file("."))
  .dependsOn(users).aggregate(users)
