package com.github.atais

import java.io.File
import java.net.URLClassLoader
import java.util.concurrent.TimeUnit

import org.eclipse.persistence.tools.weaving.jpa.StaticWeaveProcessor
import sbt.Keys._
import sbt._
import sbt.internal.util.ManagedLogger

import scala.concurrent.duration.Duration

object EclipseLinkStaticWeaver extends AutoPlugin {

  object autoImport {
    val persistenceXmlLocation = settingKey[String]("persistence.xml location")
    val staticWeaverLogLevel = settingKey[Int]("StaticWeaver log level (0 ALL -> 5 INFO -> 8 OFF)")
    val weavedClassesDest = settingKey[File]("Output dir for weaved sources")
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    persistenceXmlLocation := "META-INF/persistence.xml",
    staticWeaverLogLevel := 5,
    weavedClassesDest := crossTarget.value / "classes-weaved",

    // https://stackoverflow.com/a/26003967/1549135
    productDirectories in Compile := Seq(weavedClassesDest.value),

    // https://www.scala-sbt.org/1.0/docs/Howto-Dynamic-Task.html#build.sbt+v2
    compile in Compile := Def.taskDyn {
      val c = (compile in Compile).value
      Def.task {
        (copyResources in Compile).value // we need to copy META-INF folder first, https://github.com/sbt/sbt/issues/3934
        weaveTask.value
        c
      }
    }.value
  )

  // https://github.com/empulse-gmbh/eclipselink-static-weave-plugin/blob/master/src/main/java/de/empulse/eclipselink/mojo/EclipselinkStaticWeaveMojo.java
  def weaveTask: Def.Initialize[Task[Unit]] = Def.task {
    val t0 = System.nanoTime()
    val logger: ManagedLogger = streams.value.log
    val inputDir = (classDirectory in Compile).value
    val outputDir = weavedClassesDest.value
    val dependenciesClassPath = (dependencyClasspath in Compile).value

    logger.info("Starting EclipseLink static weaving...")
    val weaver = new StaticWeaveProcessor(inputDir.getAbsolutePath, outputDir.getAbsolutePath)
    weaver.setClassLoader(projectClassLoader(inputDir, dependenciesClassPath))
    weaver.setPersistenceXMLLocation(persistenceXmlLocation.value)
    weaver.setLog(new LogWriter(logger))
    weaver.setLogLevel(staticWeaverLogLevel.value)
    weaver.performWeaving()
    val t1 = System.nanoTime()
    val d = Duration(t1 - t0, TimeUnit.NANOSECONDS)
    logger.info(s"Finished EclipseLink static weaving in ${d.toMillis} ms.")
  }

  def projectClassLoader(outputDir: File, dependencies: Classpath): URLClassLoader = {
    val files = (recursiveListFiles(outputDir) ++ dependencies.files).map(_.toURI.toURL)
    new URLClassLoader(files, Thread.currentThread.getContextClassLoader)
  }

  // https://stackoverflow.com/a/2638109/1549135
  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  class LogWriter(out: ManagedLogger) extends java.io.Writer {
    override def write(cbuf: Array[Char], off: Int, len: Int): Unit = out.info(cbuf.mkString)

    override def flush(): Unit = Unit

    override def close(): Unit = Unit
  }

}