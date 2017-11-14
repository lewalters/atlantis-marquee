
package test;

import static org.junit.Assert.*;

import data.Dot;
import javafx.scene.paint.Color;
import org.junit.BeforeClass;
import org.junit.Test;

public class DotTest {

	private static Dot d;
	
	@BeforeClass
	public static void setUp()
	{
		d = new Dot(Color.PINK, 1);
	}
	
	@Test
	public void testDotGetters() 
	{
		assertEquals("data.Dot test: getColor",Color.PINK, d.getColor());
		
		assertEquals("data.Dot test: getIntensity", 1, d.getIntensity());
	}
	
	@Test
	public void testDotSetters() 
	{
		d.setColor(Color.PINK);
		d.setIntensity(2);
		
		assertEquals("data.Dot test: setColor", Color.PINK, d.getColor());
		
		assertEquals("data.Dot test: setIntensity", 2, d.getIntensity());
	}


	////

}

