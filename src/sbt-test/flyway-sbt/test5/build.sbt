organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test5"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.5.0",
  "org.flywaydb" % "flyway-database-hsqldb" % "10.21.0",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayValidateMigrationNaming := true
