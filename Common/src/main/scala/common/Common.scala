//contians all the common classes used by client and server
package common

import scala.util.Random
import scala.concurrent._
import scala.concurrent.duration._

 case object Start
 case class Message(msg: String)
  case class Mine(start:String) 
  case class Work(start:String) 
  case class Result(sha:Array[String]) 
  case class MiningDone(arr: Array[String],duration:Duration)
  case class RemoteMiningDone(arr: Array[String])
  case class Remotemine(start:String, numberremote :Int) 
  case class Shutdownremote()
  case class RemoteWork(start:String,numberremote :Int,duration:Long)
   case class RemoteResult(sha:Array[String],numberremote :Int,duration:Long) 

   //////////////////////////////////
  /////////////////////////////////
  //////////GENERATE STRING///////
  ///////////////////////////////
  //////////////////////////////

  class Useful{

  def generateString() :String={
    
    
    def randomString(length: Int) = {
      val ran = new scala.util.Random
      val str = new StringBuilder
      for (i <- 1 to length) {
        str.append(ran.nextPrintableChar)
      }
      str.toString
    }
    var n = Random.nextInt(100)
    var s = randomString(n)
    s="siddhant36568046"+ s
    
   s
  }
  //////////////////////////////////
  /////////////////////////////////
  //////////SHA256////////////////
  ///////////////////////////////
  //////////////////////////////
  

  def sha256(s: String): String = {
    // Besides "MD5", "SHA-256", and other hashes are available
    var str=""
    val m = java.security.MessageDigest.getInstance("SHA-256").digest(s.getBytes("UTF-8"))
   
    m.map("%02x".format(_)).mkString
     
  }

}

