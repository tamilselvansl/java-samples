package com.tamil.producerconsumer;

import java.util.List;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description :
 *
 */

public class Producer implements Runnable {
    private List<String> list;
    public Producer(List<String> pList) {
        list = pList;
    }
    public void run() {
        System.out.println("Producer started");
        for (int i = 0; i < 5000; i++) {
            list.add(Integer.toString(i));
        }
        System.out.println("Producer completed");
    }
}
