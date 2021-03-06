import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.suprnation"
ThisBuild / organizationName := "suprnation"

assembly / assemblyJarName := "MinTrianglePath.jar"
assembly / mainClass := Some("suprnation.TriangleApp")

lazy val root = (project in file("."))
  .settings(
    name := "suprnation",
    libraryDependencies += scalaTest % Test
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
