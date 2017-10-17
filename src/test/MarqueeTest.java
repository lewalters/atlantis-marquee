package test;

import data.Marquee;
import jdk.vm.ci.meta.Local;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class MarqueeTest {
    private static Marquee m;

    @BeforeClass
    public static void setUp() {
        m = new Marquee(1, 2, 3);
    }

    @Test
    public void testMarqueeSetters() {
        assertEquals("data.Message Test: getWidth", 1, m.getWidth());
        assertEquals("data.Message Test: getHeight", 2, m.getHeight());
        assertEquals("data.Message Test: getLedGap", 3, m.getLedGap());

    }

@Test
public  void testLocalTime(){
    LocalTime t = new LocalTime(1,2,3);
    t.compareTo();
    }
}

}
