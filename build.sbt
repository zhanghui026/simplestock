name := """simplestock"""

version := "1.0"

scalaVersion := "2.11.2"


// Uncomment to use Akka
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.4"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.3.4"


resolvers += "spray repo" at "http://repo.spray.io"
