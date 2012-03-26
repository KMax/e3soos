package ru.ifmo.ailab.e3soos.facts;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author maxim
 */
public class ElementTest {

    public ElementTest() {
    }

    @Test
    public void testEquals() {
        Element expected = new Element(ElementType.B, 1, SurfaceType.V, 1, SurfaceType.V);
        Element actual_0 = new Element(ElementType.B, 2, SurfaceType.A, 2, SurfaceType.A);
        Element actual_1 = new Element(ElementType.B, 1, SurfaceType.V, 1, SurfaceType.V);

        assertTrue(expected.equals(actual_1));
        assertTrue(actual_1.equals(expected));
        assertFalse(expected.equals(actual_0));
        assertFalse(actual_0.equals(expected));
    }

    @Test
    public void testToString() {
        String code = "B1P1A";
        assertEquals(code, ElementFactory.newElement(code).toString());
    }
}
