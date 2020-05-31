package com.br.challenge.similarity.calculate;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EuclideanDistanceTest {
    @Test
    public void testSameDistance2D() {
        var a1 = Arrays.asList(1,1);
        var a2 = Arrays.asList(1,1);
        var similarity = EuclideanDistance.start().setFirstVector(a1).setSecondVector(a2).calculate();
        assertThat(similarity).isEqualTo(1.0);
    }

    @Test
    public void testDiffDistance2D() {
        var a1 = Arrays.asList(1,1);
        var a2 = Arrays.asList(0,0);
        var similarity = EuclideanDistance.start().setFirstVector(a1).setSecondVector(a2).calculate();
        assertThat(similarity).isEqualTo(0.4142135623730951);
    }

    @Test
    public void testDiffDistance3D() {
        var a1 = Arrays.asList(0,0,0);
        var a2 = Arrays.asList(1,1,1);
        var similarity = EuclideanDistance.start().setFirstVector(a1).setSecondVector(a2).calculate();
        assertThat(similarity).isEqualTo(0.36602540378443865);
    }
}
