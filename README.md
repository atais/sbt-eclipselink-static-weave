# sbt-eclipselink-static-weave [![license](https://img.shields.io/github/license/mashape/apistatus.svg?style=flat)](https://github.com/atais/sbt-eclipselink-static-weave/blob/master/LICENSE)

SBT plugin for [EclipseLink Static Weaving](https://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Advanced_JPA_Development/Performance/Weaving/Static_Weaving).
<br>
Greatly inspired on [eclipselink-staticweave-maven-plugin](https://github.com/craigday/eclipselink-staticweave-maven-plugin)

## Installation

You can find the `sbt-eclipselink-static-weave` plugin on [Maven Central](https://search.maven.org/search?q=g:com.github.atais%20AND%20sbt-eclipselink-static-weave)

1. Add the plugin dependency to your `plugins.sbt`:

   ```
   addSbtPlugin("com.github.atais" % "sbt-eclipselink-static-weave" % "0.1.2")
   ```

   By default EclipseLink `2.5.1` is used by the plugin to access `StaticWeaveProcessor`.
   <br>
   If you would like to override the EclipseLink version, specify it in `plugins.sbt`:
   
   ```
   libraryDependencies += "org.eclipse.persistence" % "org.eclipse.persistence.jpa" % "<version>"
   ```

2. Activate the plugin in your project:

   ```
   enablePlugins(EclipseLinkStaticWeaver)
   ```

3. Enjoy, your should notice weaving step during your build:

   ```
   [info] Done compiling.
   [info] Starting EclipseLink static weaving...
   [info] Finished EclipseLink static weaving in 23345 ms.
   [info] Packaging ...
   ```

## Parameters

### persistenceXmlLocation: String
Location of your `persistence.xml`. 
Make sure this file is copied into `classes` folder before weaving. 
<br>**default**: `"META-INF/persistence.xml"`
 
### staticWeaverLogLevel: Int
Defines Eclipselink logging levels (that are used throughout EclipseLink code) with the following integer values:
- all - 0 
- finest - 1 
- finer - 2 
- fine - 3 
- config - 4 
- info - 5 
- warning - 6 
- severe - 7 
- off - 8

**default**: `5`

### weavedClassesDest: File 
Location into which weaved classes are saved. Also, it is a source for `jar` file building.
<br>**default**: `crossTarget.value / "classes-weaved"`

## [License](https://github.com/atais/sbt-eclipselink-static-weave/blob/master/LICENSE)

## Development

### Release

```
sbt release
```