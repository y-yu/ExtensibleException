package utils

trait RootException extends Throwable
trait ExceptionsImplicit {
  implicit def self[A, B >: A] = new ~>[A, B] {
    def cast(a: A): B = a
  }
}

case class DatabaseException(m: String) extends RootException
object DatabaseException extends ExceptionsImplicit

case class HttpException(m: String) extends RootException
object HttpException extends ExceptionsImplicit

case class DatabaseAndHttpException(m: String) extends RootException

object DatabaseAndHttpException extends ExceptionsImplicit {
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
object FileException extends ExceptionsImplicit {
  implicit def superclass[A <: FileException] = new ~>[A, FileException] {
    def cast(a: A): FileException = a
  }
}

case class ReadException(m: String) extends FileException
object ReadException extends FileException


case class WriteException(m: String) extends FileException
object WriteException extends FileException

