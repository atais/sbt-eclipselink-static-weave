// Your profile name of the sonatype account. The default is the same with the organization value
sonatypeProfileName := "com.github.atais"

// To sync with Maven central, you need to supply the following information:
publishMavenStyle := true

// License of your choice
licenses := Seq("MIT" -> url("https://github.com/atais/sbt-eclipselink-static-weave/blob/master/LICENSE"))
homepage := Some(url("https://github.com/atais/sbt-eclipselink-static-weave"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/atais/sbt-eclipselink-static-weave"), "https://github.com/atais/sbt-eclipselink-static-weave.git"
  )
)
developers := List(
  Developer(
    id = "atais",
    name = "Michał Siatkowski",
    email = "atais.jr@gmail.com",
    url = url("https://github.com/atais")
  )
)