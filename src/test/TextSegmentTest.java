package test;

import static org.junit.Assert.*;

import data.Message;
import  org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import data.TextSegment;

public class TextSegmentTest {

    private TextSegment ts;

    @BeforeClass
    public void setup()
    {
        ts = new TextSegment(60, "left-scroll-continuous", "fade");
    }

    @Test
    public void testTextSegmentGetters()
    {
        assertEquals("data.TextSegment test: getText", "Welcome to VISION", ts.getText());
    }

    @Test
    public void testTextSegmentSetters()
    {
        ts.setText("Hello World");

        assertEquals("data.TextSegment test: setText", "Hello World", ts.getText());
    }
//
}
