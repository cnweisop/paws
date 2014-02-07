import sbt._

import play.Project._

name := "paws"

version := "1.0-SNAPSHOT"

playScalaSettings

lazy val db = project.in(file("modules/db"))

lazy val common = project.in(file("modules/common"))
  .dependsOn(db).aggregate(db)

lazy val users = project.in(file("modules/users"))
  .dependsOn(common, db).aggregate(common, db)

lazy val paws = project.in(file("."))
  .dependsOn(common, db, users).aggregate(common, db, users)
