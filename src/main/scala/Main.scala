import utils.DatabaseException
import utils.HttpException

object Main {
  import utils.Implicit._

  def main(args: Array[String]): Unit = {
    val e1 = Left[DatabaseException, String](DatabaseException("db error"))
    val e2 = Left[HttpException, String](HttpException("http error"))

    // this is DatabaseException
    val e3 = for {
      a <- e1
    } yield ()
    println(e3.toString)

    // this is DatabaseAndHttpException
    import utils.DatabaseAndHttpException._
    val e4 = for {
      a <- e1
      b <- e2
    } yield ()
    println(e4.toString)
  }
}
