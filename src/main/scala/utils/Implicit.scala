package utils

object Implicit {
  implicit class ExceptionEither[L <: Exceptions[RootException], R](val ee: Either[L, R]) {
    def map[L2 >: L <: RootException, R2](f: R => R2): Either[L2, R2] = ee.right.map(f)

    def map[L2, R2](f: R => R2)(implicit L2: Transform[L, L2]): Either[L2, R2] = ee match {
      case Left(e)  => Left(L2.cast(e))
      case Right(v) => Right(f(v))
    }

    def flatMap[L2 >: L <: RootException, R2](f: R => Either[L2, R2]): Either[L2, R2] = ee.right.flatMap(f)

    def flatMap[L2, R2](f: R => Either[L2, R2])(implicit L2: Transform[L, L2]): Either[L2, R2] = ee match {
      case Left(e)  => Left(L2.cast(e))
      case Right(v) => f(v)
    }
  }
}
