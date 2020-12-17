package com.example.kotlin_reflect

interface IAnimal

class People : IAnimal {

}

class Bird : IAnimal


fun doIt() {
    doJob(Job<Bird>())
}

fun doJob(job: Job<out IAnimal>) {

}