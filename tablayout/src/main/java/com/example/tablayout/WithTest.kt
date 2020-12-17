package com.example.tablayout

class WithTest {
}

fun main() {
    val a = 10
    val p = People()
    val name = with(p) {

        if (age > 10) {
        }

        "不是"
    }
    print(name)
}

class People {

    val age = 19
    val name = "我是哈哈"
}