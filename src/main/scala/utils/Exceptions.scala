package utils

trait RootException extends Throwable

case class DatabaseException(m: String) extends RootException

case class HttpException(m: String) extends RootException

case class DatabaseAndHttpException(m: String) extends RootException

object DatabaseAndHttpException extends {
  implicit val databaseException = new ~>[DatabaseException, DatabaseAndHttpException] {
    def cast(a: DatabaseException): DatabaseAndHttpException =
      DatabaseAndHttpException(s"database: ${a.m}")
  }

  implicit val httpException = new ~>[HttpException, DatabaseAndHttpException] {
    def cast(a: HttpException): DatabaseAndHttpException =
      DatabaseAndHttpException(s"http: ${a.m}")
  }
}

trait FileException extends RootException
object FileException {
  implicit val readToSuper = new ~>[ReadException, FileException] {
    def cast(a: ReadException): FileException = a
  }

  implicit val writeToSuper = new ~>[WriteException, FileException] {
    def cast(a: WriteException): FileException = a
  }
}

case class ReadException(m: String) extends FileException

case class WriteException(m: String) extends FileException

