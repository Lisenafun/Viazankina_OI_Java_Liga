package com.bad.code2;

/**
 * Класс куб имплементирует интерфейс объем
 */
public class Cube implements Volume {

    /**
     * длина ребра куба
     */
    private final double edgeLength;


    /**
     * Конструктор класса
     *
     * @param edgeLength длина ребра куба
     */
    public Cube(double edgeLength) {
        this.edgeLength = edgeLength;
    }

    /**
     * Получение объема куба
     *
     * @return объем куба
     */
    @Override
    public double getVolume() {
        return Math.pow(edgeLength, 3);
    }
}
