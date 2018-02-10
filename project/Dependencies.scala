import sbt._

object Dependencies {
  lazy val awsS3 = "com.amazonaws" % "aws-java-sdk-s3" % "1.11.274"

  lazy val awsLambdaCore   = "com.amazonaws" % "aws-lambda-java-core"   % "1.2.0"
  lazy val awsLambdaEvents = "com.amazonaws" % "aws-lambda-java-events" % "1.2.0"

  lazy val sprayJson = "io.spray" %%  "spray-json" % "1.3.3"
}