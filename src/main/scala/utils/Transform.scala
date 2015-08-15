package utils

trait ~>[A, B] {
  def cast(a: A): B
}

object ~> {
  implicit def self[A]: A ~> A = new ~>[A, A] {
    def cast(a: A): A = a
  }
}