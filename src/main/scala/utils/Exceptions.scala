package utils

trait RootException extends Throwable

case class DatabaseException(m: String) extends RootException

case class HttpException(m: String) extends RootException

case class DatabaseAndHttpException(m: String) extends RootException
object DatabaseAndHttpException {
  implicit val databaseException = new :->[DatabaseException, DatabaseAndHttpException] {
    def cast(a: DatabaseException): DatabaseAndHttpException =
      DatabaseAndHttpException(s"database: ${a.m}")
  }

  implicit val httpException = new :->[HttpException, DatabaseAndHttpException] {
    def cast(a: HttpException): DatabaseAndHttpException =
      DatabaseAndHttpException(s"http: ${a.m}")
  }
}

trait FileException extends RootException

case class ReadException(m: String) extends FileException

case class WriteException(m: String) extends FileException

// defined by macro
case class FileAndHttpException(cause: Throwable) extends RootException
object FileAndHttpException {
  implicit val fileException = Transform.castable[FileException, FileAndHttpException]
  implicit val httpException = Transform.castable[HttpException, FileAndHttpException]
}

