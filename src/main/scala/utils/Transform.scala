package utils

trait Transform[A, B] {
  def cast(a: A): B
}
