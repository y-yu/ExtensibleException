package utils

trait :->[-A, +B] {
  def cast(a: A): B
}

object :-> {
  implicit def self[A] = new :->[A, A] {
    def cast(a: A): A = a
  }

  implicit def superclass[A, B >: A] = new :->[A, B] {
    def cast(a: A): B = a
  }
}

