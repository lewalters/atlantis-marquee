
package test;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import data.ImageSegment;

public class ImageSegmentTest {

    private ImageSegment is;

    @Before
    public  void setUp()
    {
        is = new ImageSegment();
    }

    @Test
    public void testImageSegmentGetters()
    {

        assertEquals("data.ImageSegment test: getSource", "", is.getSource());
    }

    @Test
    public void testImageSegmentSetters()
    {
        is.setSource("");

        assertEquals("data.ImageSegment test: setSource", "", is.getSource());
    }
}

