name := "test"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.7"

lazy val eclipselink = "org.eclipse.persistence" % "org.eclipse.persistence.jpa" % "2.5.1"
libraryDependencies += eclipselink

enablePlugins(EclipseLinkStaticWeaver)