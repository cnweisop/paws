import sbt.Keys._
import sbt._

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "so.paws",
    version := "1.1.0",
    scalaVersion := "2.10.4"
  )
}
