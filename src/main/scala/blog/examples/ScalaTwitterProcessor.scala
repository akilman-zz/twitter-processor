package blog.examples

import java.util

import twitter4j.{Status, Twitter}
import collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable

class ScalaTwitterProcessor(twitter: Twitter) extends ATwitterProcessor(twitter) {

  override def homeStreamSizeVsWord(): util.Map[Integer, util.List[String]] = {
    val timeLine = twitter.getHomeTimeline.toList
    ScalaTwitterProcessor.homeStreamSizeVsWord(timeLine)
  }

  override def homeStreamUrls(): util.List[String] = {
    val timeLine = twitter.getHomeTimeline.toList
    ScalaTwitterProcessor.homeStreamUrls(timeLine)
  }

  override def parallelHomeStreamSizeVsWord(): util.Map[Integer, util.List[String]] = ???

  override def getInterestingTimelineTweets_v2: util.List[Status] = ???

  override def getInterestingTimelineTweets_v1: util.List[Status] = ???

  override def parallelHomeStreamUrls(): util.List[String] = ???
}

object ScalaTwitterProcessor {

  val extractText: (Status) => String = _.getText
  val removePunctuation: String => String = _.replaceAll("[^a-zA-Z ]", "")
  val toLowerCase: String => String = _.toLowerCase
  val transformText = extractText andThen removePunctuation andThen toLowerCase

  val splitWords: (String) => mutable.ArrayOps[String] = _.split(" ")

  val empty: (String) => Boolean = !_.isEmpty
  val notHttpLink: (String) => Boolean = !_.startsWith("http")
  val httpLink: (String) => Boolean = _.startsWith("http")
  val length: (String) => Int = _.length

  def homeStreamSizeVsWord(timeLine: List[Status]): util.Map[Integer, util.List[String]] = {
    timeLine.map(transformText)
      .flatMap(splitWords)
      .filter(empty)
      .filter(notHttpLink)
      .groupBy(length)
      .asJava
      .asInstanceOf[util.Map[Integer, util.List[String]]]
  }

  def homeStreamUrls(timeLine: List[Status]): util.List[String] = {
    timeLine.map(extractText)
      .map(toLowerCase)
      .flatMap(splitWords)
      .filter(empty)
      .filter(httpLink)
      .asJava
  }
}
