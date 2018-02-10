package jp.pigumer.s3

import com.amazonaws.services.lambda.runtime.Context

class HelloWorld {
  def handler(s3Event: Object, context: Context): Unit = {
    val logger = context.getLogger
    logger.log(s3Event.toString)
  }
}