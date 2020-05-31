package com.br.challenge.similarity.calculate;

import java.util.List;
import java.util.stream.IntStream;

public class EuclideanDistance {
    public static EuclideanDistance start() {
        return new EuclideanDistance();
    }

    List<Integer> fistVector;
    List<Integer> secondVector;

    public EuclideanDistance setFirstVector(List<Integer> fistVector) {
        this.fistVector = fistVector;
        return this;
    }

    public EuclideanDistance setSecondVector(List<Integer> secondVector) {
        this.secondVector = secondVector;
        return this;
    }

    public Double calculate() {
        var min = Math.min(this.fistVector.size(),this.secondVector.size());
        var distance = IntStream.range(0, min)
                .mapToDouble(i -> sumPow(this.fistVector.get(i), this.secondVector.get(i))).sum();
        return sumSqrt(distance);
    }

    private double sumSqrt(double distance) {
        return 1 / (1 + Math.sqrt(distance));
    }

    private Double sumPow(Integer v1, Integer v2) {
        return Math.pow((v2 - v1),2);
    }
}
