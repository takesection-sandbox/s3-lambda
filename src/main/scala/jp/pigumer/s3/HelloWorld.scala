package jp.pigumer.s3

import java.io.{IOException, InputStream, OutputStream}
import java.nio.charset.StandardCharsets

import com.amazonaws.services.lambda.runtime.Context
import spray.json._

class HelloWorld {
  @throws[IOException]
  def handler(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
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
  }
}