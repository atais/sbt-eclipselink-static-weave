import ReleaseTransformations._

lazy val eclipselink = "org.eclipse.persistence" % "eclipselink" % "2.5.1"

lazy val main = (project in file("."))
  .settings(
    name := "sbt-eclipselink-static-weave",
    organization := "com.github.atais",
    scalaVersion := "2.12.6",
    sbtVersion := "1.1.6",
    sbtPlugin := true,

    libraryDependencies += eclipselink,

    // release settings
    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),

    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommand("publishSigned"),
      setNextVersion,
      commitNextVersion,
      releaseStepCommand("sonatypeReleaseAll"),
      pushChanges
    )
  )