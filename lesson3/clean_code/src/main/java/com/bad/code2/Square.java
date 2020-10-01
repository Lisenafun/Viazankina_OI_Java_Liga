package com.bad.code2;

/**
 * Класс квадрат имплементирует интерфейс периметр
 */
public class Square implements Perimeter {

    /**
     * Длина стороны квадрата
     */
    private final double sideLength;

    /**
     * Конструктор класса
     *
     * @param sideLength длина стороны квадрата
     */
    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    /**
     * Получение периметра квадрата
     *
     * @return периметр квадрата
     */
    @Override
    public double getPerimeter() {
        return sideLength * 4;
    }
}
