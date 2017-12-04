name := "myApp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
	"com.google.code.gson" % "gson" % "2.3.1",
	"net.sf.flexjson" % "flexjson" % "2.1",
	"org.json" % "json" % "20090211",
	"org.json" % "json" % "20131018"
)    






play.Project.playJavaSettings
