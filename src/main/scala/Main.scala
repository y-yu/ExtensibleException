import utils.DatabaseException
import utils.HttpException
import utils.Transform

object Main {
  import utils.DatabaseAndHttpException._
  import utils.Implicit._

  def main(args: Array[String]): Unit = {
    val e1 = Left[DatabaseException, String](DatabaseException("db error"))
    val e2 = Left[HttpException, String](HttpException("http error"))

    // this is DatabaseException
    val e3 = e1.map(x => ())

    // this is DatabaseAndHttpException
    val e4 = for {
      a <- e1
      b <- e2
    } yield ()
  }
}
