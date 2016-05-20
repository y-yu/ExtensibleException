package utils

trait :->[-A, +B] { self =>
  def cast(a: A): B
  def compose[C](that: B :-> C) = new :->[A, C] {
    def cast(a: A): C = that.cast(self.cast(a))
  }
}

object :-> {
  implicit def self[A] = new :->[A, A] {
    def cast(a: A): A = a
  }

  implicit def superclass[A, B >: A] = new :->[A, B] {
    def cast(a: A): B = a
  }
}

object Transform {
  def trans[A, B, C](implicit F: A :-> B, G: B :-> C): A :-> C =
    implicitly[A :-> B] compose implicitly[B :-> C]
}