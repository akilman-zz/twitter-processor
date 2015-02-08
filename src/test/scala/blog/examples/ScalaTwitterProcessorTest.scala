package blog.examples

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import twitter4j.Status

import scala.util.Random

class ScalaTwitterProcessorTest extends FlatSpec with Matchers with MockFactory {

  val generator: Random = new Random

  "An empty STP" should "return an empty java map" in {
    val statusList = Nil
    val javaMap = ScalaTwitterProcessor.homeStreamSizeVsWord(statusList)
    javaMap.isEmpty should be (true)
  }

  "A non-empty STP" should "return a non-empty java map" in {
    val statusList = (1 to 1000).map(i => createStatus(randomText)).toList
    val javaMap = ScalaTwitterProcessor.homeStreamSizeVsWord(statusList)
    println(javaMap)
    javaMap.isEmpty should be (false)
  }

  /**
   * @return Random string of max length N-1 (N=50)
   */
  def randomText = {
    val ceiling: Int = generator.nextInt(50)
    (1 to ceiling).map(i => generator.nextPrintableChar()).mkString
  }

  /**
   * Creates a [[stub]] versions of a [[Status]]
   * @param text string for getText to return
   * @return [[Status]] instance
   */
  def createStatus(text: String): Status = {
    val status = stub[Status]
    (status.getText _) when() returns(text)
    status
  }
}
