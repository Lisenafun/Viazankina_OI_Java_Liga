package com.bad.code2;

public class CleanCode {
    public static void main(String... args) {
        Cube cube = new Cube(10d);
        System.out.println("Cube volume: " + cube.getVolume());

        Square square = new Square(5d);
        System.out.println("Square perimeter: " + square.getPerimeter());
    }
}
