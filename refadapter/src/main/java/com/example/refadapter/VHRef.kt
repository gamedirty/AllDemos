package com.example.refadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses


fun KClass<*>.isInstanceOf(kClass: KClass<*>): Boolean {

    println("目标class：" + kClass.qualifiedName + "," + kClass.hashCode())
    this.superclasses.forEach {
        println("比较class：${it.qualifiedName}" + "," + it.hashCode())
        if (it.hashCode() == kClass.hashCode()) {
            return true
        }
    }

    return false
}