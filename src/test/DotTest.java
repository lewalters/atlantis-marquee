package test;

import static org.junit.Assert.*;

import data.Dot;
import org.junit.BeforeClass;
import org.junit.Test;

public class DotTest {

	private Dot d;
	
	@BeforeClass
	public void setup()
	{
		d = new Dot("Black", 1);
	}
	
	@Test
	public void testDotGetters() 
	{
		assertEquals("data.Dot test: getColor", "Black", d.getColor());
		
		assertEquals("data.Dot test: getIntensity", 1, d.getIntensity());
	}
	
	@Test
	public void testDotSetters() 
	{
		d.setColor("Blue");
		d.setIntensity(2);
		
		assertEquals("data.Dot test: setColor", "Blue", d.getColor());
		
		assertEquals("data.Dot test: setIntensity", 2, d.getIntensity());
	}

}
