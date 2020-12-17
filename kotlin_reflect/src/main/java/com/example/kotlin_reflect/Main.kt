package com.example.kotlin_reflect

import java.lang.reflect.ParameterizedType
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import kotlin.reflect.jvm.jvmName

object Main {
    @JvmStatic
    fun main(args: Array<String>) {


        IAnimal::class.hashCode().print()
        People::class.apply {
            this.superclasses.forEach {
                it.hashCode().print()
                (it == IAnimal::class).print()
            }

            println(superclasses.contains(IAnimal::class))
        }

    }


    private fun Any.print() {
        println(this)
    }
}