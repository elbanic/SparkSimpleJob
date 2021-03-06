package com.aws.sample

import org.apache.spark._
import org.apache.spark.sql._

/**
  * example for word count
  */

object SparkSimpleJob {

  def main (arg: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local")
      .appName(getClass.getSimpleName)
      .getOrCreate()

    val input = arg(0)
    val output = arg(1)

    wordcount(spark, input, output)
  }

  def wordcount (spark: SparkSession, input: String, output: String): Unit ={
    val textfile = spark.sparkContext.textFile(input)

    val counter = textfile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counter.coalesce(1).saveAsTextFile(output)
  }
}