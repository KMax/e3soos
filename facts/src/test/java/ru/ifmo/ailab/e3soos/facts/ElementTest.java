package ru.ifmo.ailab.e3soos.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        Element expected = new Element(ElementType.BASIC, 1, SurfaceType.V, 1, SurfaceType.V);
        Element actual_0 = new Element(ElementType.BASIC, 2, SurfaceType.A, 2, SurfaceType.A);
        Element actual_1 = new Element(ElementType.BASIC, 1, SurfaceType.V, 1, SurfaceType.V);
        
        assertTrue(expected.equals(actual_1));
        assertTrue(actual_1.equals(expected));
        assertFalse(expected.equals(actual_0));
        assertFalse(actual_0.equals(expected));
    }
}
