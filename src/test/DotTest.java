package test;

import static org.junit.Assert.*;

import data.Dot;
import org.junit.BeforeClass;
import org.junit.Test;

public class DotTest {

	private static Dot d;
	
	@BeforeClass
	public static void setUp()
	{
		d = new Dot("000000", 1);
	}
	
	@Test
	public void testDotGetters() 
	{
		assertEquals("data.Dot test: getColor", "000000", d.getColor());
		
		assertEquals("data.Dot test: getIntensity", 1, d.getIntensity());
	}
	
	@Test
	public void testDotSetters() 
	{
		d.setColor("201DE2");
		d.setIntensity(2);
		
		assertEquals("data.Dot test: setColor", "201DE2", d.getColor());
		
		assertEquals("data.Dot test: setIntensity", 2, d.getIntensity());
	}


	////

}
