package exceptions

import utils.:->
import utils.Transform

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

case class DatabaseAndHttpAndFileReadException(m: String) extends RootException
object DatabaseAndHttpAndFileReadException {
  implicit val databaseAndHttpException = new :->[DatabaseAndHttpException, DatabaseAndHttpAndFileReadException] {
    def cast(a: DatabaseAndHttpException): DatabaseAndHttpAndFileReadException =
      DatabaseAndHttpAndFileReadException(s"database and http: ${a.m}")
  }

  implicit val fileReadException = new :->[ReadException, DatabaseAndHttpAndFileReadException] {
    def cast(a: ReadException): DatabaseAndHttpAndFileReadException =
      DatabaseAndHttpAndFileReadException(s"file read: ${a.m}")
  }

  implicit val a = Transform.trans[HttpException, DatabaseAndHttpException, DatabaseAndHttpAndFileReadException]
  implicit val b = Transform.trans[DatabaseException, DatabaseAndHttpException, DatabaseAndHttpAndFileReadException]
}
