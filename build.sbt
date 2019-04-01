name := "AkkaHTTPExamples"

version := "1.0"

scalaVersion := "2.11.7"

val akkaHttpV = "2.4.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" %  akkaHttpV,
  "com.typesafe.akka" %% "akka-http-experimental" %  akkaHttpV,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
  "org.scalatest" %% "scalatest" % "2.2.5"
)
    
