ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

coverageEnabled := true
semanticdbEnabled := true

lazy val root = (project in file("."))
  .settings(name := "scala-kuva", idePackagePrefix := Some("com.example"))

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"

libraryDependencies += "org.typelevel" %% "spire" % "0.18.0"

enablePlugins(JmhPlugin)
