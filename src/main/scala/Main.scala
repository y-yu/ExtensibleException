object Main {
  import exceptions._
  import utils.Implicit._

  def left[A](e: A) = Left[A, Unit](e)

  def e1 = left(DatabaseException("db error"))

  def e2 = left(HttpException("http error"))

  def e3 = left(ReadException("file read error"))

  def e4 = left(WriteException("file write error"))

  def main(args: Array[String]): Unit = {
    // this is DatabaseException
    val e5 = for {
      a <- e1
    } yield ()

    // this is DatabaseAndHttpException
    {
      import DatabaseAndHttpException._
      val e6 = for {
        a <- e1
        b <- e2.as[DatabaseAndHttpException]
      } yield ()
    }

    // this is FileException
    val e7 = for {
      a <- e3
      b <- e4.as[FileException]
    } yield ()

    // this is ReadException
    val e8 = for {
      a <- e3
    } yield ()

    // chain of :->
    {
      import DatabaseAndHttpException._
      import DatabaseAndHttpAndFileReadException._
      import DatabaseAndHttpAndFileException._
      val e9 = for {
        a <- e1
        b <- e2
        c <- e3
        d <- e4.as[DatabaseAndHttpAndFileException]
      } yield ()
    }
  }
}
