package jp.pigumer.s3

import java.io.{IOException, InputStream, OutputStream}
import java.nio.charset.StandardCharsets

import com.amazonaws.services.lambda.runtime.Context
import spray.json._

case class S3BucketEntity(name: String,
                          arn: String)

case class S3ObjectEntity(key: String,
                          size: Int)

case class S3Entity(bucket: S3BucketEntity,
                    s3Object: S3ObjectEntity)

case class S3EventNotificationRecord(
                                      awsRegion: String,
                                      eventName: String,
                                      eventSource: String,
                                      eventTime: String,
                                      s3: S3Entity
                                    )

case class S3EventNotification(records: Seq[S3EventNotificationRecord])

object S3EventNotificationJsonProtocol extends DefaultJsonProtocol {
  implicit val s3BucketEntityJsonFormat = jsonFormat(
    S3BucketEntity,
    "name",
    "arn"
  )
  implicit val s3ObjectEntityJsonFormat = jsonFormat(
    S3ObjectEntity,
    "key",
    "size"
  )
  implicit val s3EntityJsonFormat = jsonFormat(
    S3Entity,
    "bucket",
    "object"
  )
  implicit val s3EventNotificationRecordJsonFormat = jsonFormat(
    S3EventNotificationRecord,
    "awsRegion",
    "eventName",
    "eventSource",
    "eventTime",
    "s3"
  )
  implicit val s3EventJsonFormat = jsonFormat(S3EventNotification, "Records")
}

class HelloWorld {
  @throws[IOException]
  def handler(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
    import S3EventNotificationJsonProtocol._

    val logger = context.getLogger
    val json = {
      try {
        JsonParser(
          new String(
            Stream.continually(inputStream.read()).takeWhile(_ != -1).map(_.toByte).toArray,
            StandardCharsets.UTF_8
          )
        )
      } finally {
        inputStream.close()
      }
    }
    logger.log(json.compactPrint)
    logger.log(json.convertTo[S3EventNotification].toString)
  }
}