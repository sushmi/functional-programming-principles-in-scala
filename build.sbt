course := "progfun1"
scalaVersion := "2.13.0"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v", "-s")


lazy val example = project
  .in(file("example"))

lazy val recfun = project
  .in(file("recfun"))

lazy val funsets = project
  .in(file("funsets"))

lazy val objsets = project
  .in(file("objsets"))
