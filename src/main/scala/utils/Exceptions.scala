package utils

trait Exceptions[T <: Exceptions[T]] extends Throwable

trait RootException extends Exceptions[RootException]

case class DatabaseException(m: String) extends Exceptions[RootException]

case class HttpException(m: String) extends Exceptions[RootException]

case class DatabaseAndHttpException(m: String) extends Exceptions[RootException]

object DatabaseAndHttpException {
  implicit val databaseException = new Transform[DatabaseException, DatabaseAndHttpException] {
    def cast(a: DatabaseException): DatabaseAndHttpException =
      DatabaseAndHttpException(s"database: ${a.m}")
  }

  implicit val httpException = new Transform[HttpException, DatabaseAndHttpException] {
    def cast(a: HttpException): DatabaseAndHttpException =
      DatabaseAndHttpException(s"http: ${a.m}")
  }
}
