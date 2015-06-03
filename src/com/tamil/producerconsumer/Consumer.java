package com.tamil.producerconsumer;

import java.util.List;

public class Consumer implements Runnable {
    private List list;
    public Consumer(List pList) {
        list = pList;
    }
    public void run() {
        System.out.println("Consumer started");
        for (int i = 0; i < 5000; i++) {
            while (!list.remove(Integer.toString(i))) {
                // Just iterating till an element is removed
            }
        }
        System.out.println("Consumer completed");
    }
}
