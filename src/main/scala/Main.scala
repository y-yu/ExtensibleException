import utils._

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

    // this is DatabaseAndHttpException
    val e4 = for {
      a <- e1
      b <- e2.as[DatabaseAndHttpException]
    } yield ()

    val e5 = left(ReadException("file read error"))
    val e6 = left(WriteException("file read error"))

    // this is FileException
    val e7 = for {
      a <- e5
      b <- e6.as[FileException]
    } yield ()

    // this is ReadException
    val e8 = for {
      a <- e5
    } yield ()

    // pattern match
    e7.left.map {
      case ReadException(m)  => println(s"Read Exception: $m")
      case WriteException(m) => println(s"Write Exception: $m")
    }
  }
}
