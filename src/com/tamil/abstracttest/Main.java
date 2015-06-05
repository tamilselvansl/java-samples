package com.tamil.abstracttest;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description :
 *
 */

class Main {
    public static void main(String[] args) {
        Bike tvs = new Tvs();
        Bike bajaj = new Bajaj();
        System.out.println(tvs.getSpeed());
        System.out.println(bajaj.getSpeed());
        Bike.test();
    }
}
