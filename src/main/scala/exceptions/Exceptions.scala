package exceptions

import utils.:->

trait RootException extends Throwable

case class DatabaseException(m: String) extends RootException

case class HttpException(m: String) extends RootException

case class DatabaseAndHttpException(m: String) extends RootException

object DatabaseAndHttpException {
  implicit val databaseException = new (DatabaseException :-> DatabaseAndHttpException) {
      def apply(a: DatabaseException): DatabaseAndHttpException =
        DatabaseAndHttpException(s"database: ${a.m}")
    }

  implicit val httpException = new (HttpException :-> DatabaseAndHttpException) {
      def apply(a: HttpException): DatabaseAndHttpException =
        DatabaseAndHttpException(s"http: ${a.m}")
    }
}

trait FileException extends RootException

case class ReadException(m: String) extends FileException

case class WriteException(m: String) extends FileException

case class DatabaseAndHttpAndFileReadException(m: String) extends RootException
object DatabaseAndHttpAndFileReadException {
  implicit val databaseAndHttpException = new (DatabaseAndHttpException :-> DatabaseAndHttpAndFileReadException) {
    def apply(a: DatabaseAndHttpException): DatabaseAndHttpAndFileReadException =
      DatabaseAndHttpAndFileReadException(s"database and http: ${a.m}")
  }

  implicit val fileReadException = new (ReadException :-> DatabaseAndHttpAndFileReadException) {
    def apply(a: ReadException): DatabaseAndHttpAndFileReadException =
      DatabaseAndHttpAndFileReadException(s"file read: ${a.m}")
  }
}

case class DatabaseAndHttpAndFileException(m: String) extends RootException
object DatabaseAndHttpAndFileException {
  implicit val databaseAndHttpAndFileReadExcepion = new (DatabaseAndHttpAndFileReadException :-> DatabaseAndHttpAndFileException) {
    def apply(a: DatabaseAndHttpAndFileReadException): DatabaseAndHttpAndFileException =
      DatabaseAndHttpAndFileException(s"database and http and file read: ${a.m}")
  }

  implicit val fileWriteException = new (WriteException :-> DatabaseAndHttpAndFileException) {
    def apply(a: WriteException): DatabaseAndHttpAndFileException =
      DatabaseAndHttpAndFileException(s"file write: ${a.m}")
  }
}
