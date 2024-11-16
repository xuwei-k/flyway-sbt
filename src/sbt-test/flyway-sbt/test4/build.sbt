organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test4"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.5.0",
  "org.flywaydb" % "flyway-database-hsqldb" % "10.21.0",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayCallbacks := Seq(Callback)
flywayCleanDisabled := false
Test / flywayCleanDisabled := false
