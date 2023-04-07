package fr.lernejo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CovTest {
    @Test
    void testAddition() {
        Sample sample = new Sample();
        int result = sample.op(Sample.Operation.ADD, 2, 3);
        assertEquals(5, result);
    }

    @Test
    void testMultiplication() {
        Sample sample = new Sample();
        int result = sample.op(Sample.Operation.MULT, 2, 3);
        assertEquals(6, result);
    }

    @Test
    void testFactorialWithZero() {
        Sample sample = new Sample();
        int result = sample.fact(0);
        assertEquals(1, result);
    }

    @Test
    void testFactorialWithPositiveNumber() {
        Sample sample = new Sample();
        int result = sample.fact(5);
        assertEquals(120, result);
    }

    @Test
    void testFactorialWithNegativeNumber() {
        Sample sample = new Sample();
        assertThrows(IllegalArgumentException.class, () -> sample.fact(-1));
    }
}
