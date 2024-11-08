val flywayVersion = "9.22.0"

ThisBuild / organization := "com.github.sbt"
ThisBuild / version := {
  val orig = (ThisBuild / version).value
  if (orig.startsWith("0.0") && orig.endsWith("-SNAPSHOT")) {
    "9.0.0-SNAPSHOT"
  } else orig
}
lazy val root = (project in file ("."))
    .enablePlugins(SbtPlugin)
    .settings(
      name := "flyway-sbt",
      libraryDependencies ++= Seq(
        "org.flywaydb" % "flyway-core" % flywayVersion
      ),
      scalacOptions ++= Seq(
        "-deprecation",
        "-unchecked",
        "-Xfuture"
      ),
      pluginCrossBuild / sbtVersion := {
        scalaBinaryVersion.value match {
          case "2.12" => "1.5.8"
          case _      => "2.0.0-M2"
        }
      },
      Compile / doc / scalacOptions ++= {
        Seq(
          "-sourcepath",
          (LocalRootProject / baseDirectory).value.getAbsolutePath,
          "-doc-source-url",
          s"""https://github.com/sbt/flyway-sbt/tree/${sys.process.Process("git rev-parse HEAD").lineStream_!.head}€{FILE_PATH}.scala"""
        )
      },
      scriptedLaunchOpts := { scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
      },
      scriptedBufferLog := false,
      publishMavenStyle := true,
  )

ThisBuild / developers := List(
  Developer(id="davidmweber", name="David Weber", email="dave@veryflatcat.com", url=url("https://davidmweber.github.io/flyway-sbt-docs/"))
)
ThisBuild / licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / publishTo := sonatypePublishTo.value
ThisBuild / githubWorkflowBuild := Seq(WorkflowStep.Sbt(List("test", "scripted")))
ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))
ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    commands = List("ci-release"),
    name = Some("Publish project"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)
ThisBuild / githubWorkflowOSes := Seq("ubuntu-latest", "macos-latest", "windows-latest")
ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.temurin("8"),
  JavaSpec.temurin("17")
)
ThisBuild / githubWorkflowBuildMatrixExclusions += MatrixExclude(Map("java" -> "temurin@8", "os" -> "macos-latest"))
