package utils

trait :~>[-A, +B] { self =>
  def apply(a: A): B
  def compose[C](that: B :~> C): A :~> C = new :~>[A, C] {
    def apply(a: A): C = that(self(a))
  }
}

trait :->[-A, +B] {
  def apply(a: A): B
}

object :~> {
  implicit def self[A]: A :~> A = new (A :~> A) {
    def apply(a: A): A = a
  }

  implicit def superclass[A, B >: A]: A :~> B = new (A :~> B) {
    def apply(a: A): B = a
  }

  implicit def transitive[A, B, C](implicit F: A :-> B, G: B :~> C): A :~> C = new (A :~> C) {
    def apply(a: A): C = G(F(a))
  }
}
