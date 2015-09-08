package utils

trait :->[-A, +B] {
  def cast(a: A): B
}

object :-> {
  implicit def superclass[A, B >: A] = new :->[A, B] {
    def cast(a: A): B = a
  }
}

