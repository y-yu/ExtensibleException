import utils.DatabaseException
import utils.HttpException
import utils.ReadException
import utils.WriteException

object Main {
  import utils.Implicit._

  def left[A](e: A) = Left[A, Unit](e)

  def main(args: Array[String]): Unit = {
    val e1 = left(DatabaseException("db error"))
    val e2 = left(HttpException("http error"))

    // this is DatabaseException
    val e3 = for {
      a <- e1
    } yield ()

    import utils.DatabaseAndHttpException._

    // this is DatabaseAndHttpException
    val e4 = for {
      a <- e1
      b <- e2
    } yield ()

    val e5 = left(ReadException("file read error"))
    val e6 = left(WriteException("file read error"))

    import utils.FileException._

    // this is FileException
    val e7 = for {
      a <- e5
      b <- e6
    } yield ()
  }
}
