package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import data.ImageSegment;

public class ImageSegmentTest {

    private ImageSegment is;

    @BeforeClass
    public void setup()
    {
        is = new ImageSegment(60, "static", "fade", "C:/Capstone/Messages/WTCC Logo.jpg");
    }

    @Test
    public void testImageSegmentGetters()
    {
        assertEquals("data.ImageSegment test: getSource", "C:/Capstone/Messages/WTCC Logo.jpg", is.getSource());
    }

    @Test
    public void testImageSegmentSetters()
    {
        is.setSource("C:/Capstone/Desktop/WTCC Logo.jpg");

        assertEquals("data.ImageSegment test: setSource", "C:/Capstone/Messages/Flower.jpg", is.getSource());
    }
}
