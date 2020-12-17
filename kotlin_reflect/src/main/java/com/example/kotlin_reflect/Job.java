package com.example.kotlin_reflect;

public class Job<T extends IAnimal> {

    void fun() {
        Job<IAnimal> peopleJob = new Job<>();
        doJob(peopleJob);
    }

    void doJob(Job<?> simpleJob) {

    }
}
