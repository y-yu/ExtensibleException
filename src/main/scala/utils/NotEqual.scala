package utils

trait <:!<[A, B]

object <:!< {
  implicit def nsub[A, B]: A <:!< B = new <:!<[A, B] {}
  implicit def nsubAmbig1[A, B >: A]: A <:!< B = sys.error("Unexpected call")
  implicit def nsubAmbig2[A, B >: A]: A <:!< B = sys.error("Unexpected call")
}
