import Dependencies._
import jp.pigumer.sbt.cloud.aws.cloudformation._

lazy val root = (project in file("."))
  .enablePlugins(CloudformationPlugin)
  .settings(
    scalaVersion := "2.12.4",
    name := "s3-lambda",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      awsS3,
      awsLambdaCore,
      awsLambdaEvents,
      sprayJson
    )
  )
  .settings(
    awscfSettings := AwscfSettings(
      region = "ap-northeast-1",
      bucketName = Some(sys.env.getOrElse("BUCKET_NAME", "<YOUR BUCKET NAME>"))
    )
  )