package ru.ifmo.ailab.e3soos.facts;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 */
public class ElementFactoryTest {

    public ElementFactoryTest() {
    }

    @Test
    public void testNewElement() {
        assertEquals(new Element(ElementType.B, 1, SurfaceType.P, 3, SurfaceType.P),
                ElementFactory.newElement("B1P3P"));
        assertEquals(new Element(ElementType.C, 2, SurfaceType.A, 3, SurfaceType.I),
                ElementFactory.newElement("C2A3I"));
    }
}
