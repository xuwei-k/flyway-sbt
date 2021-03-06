organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test1"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.2.8",
  "org.flywaydb" % "flyway-core" % "5.0.7"
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations += "db/migration"
flywayUrl in Test := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser in Test := "SA"
