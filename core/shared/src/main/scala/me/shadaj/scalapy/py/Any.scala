package me.shadaj.scalapy.py

import scala.language.dynamics
import scala.collection.mutable

trait Any extends scala.Any { self =>
  private[py] def value: PyValue
  
  final def expr: VariableReference = {
    CPythonInterpreter.getVariableReference(value)
  }

  override def toString: String = value.getStringified

  final def as[T: Reader]: T = implicitly[Reader[T]].read(value)
}

object Any {
  def populateWith(v: PyValue): Any = {
    new Any {
      val value = v
    }
  }

  implicit def from[T](v: T)(implicit writer: Writer[T]): Any = {
    Any.populateWith(writer.write(v))
  }
}
