package org.embl.person;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonReaderTest {

	@Test
	public void testSetHeaders() 
	{
		PersonReader pr = new PersonReader();
		pr.setHeaders("surname,first_name,place of birth,favourite_colour,age,nationality");
		Person p = pr.parse("Brown,John,London,red,25,British");
		
		assertTrue("Wrong name",p.getFirst_name().compareTo("John") == 0);
		assertTrue("Wrong surname",p.getLast_name().compareTo("Brown") == 0);
		assertTrue("Wrong age",p.getAge() == 25);
		assertTrue("Wrong color",p.getFavourite_colour().compareTo("red") == 0);
	}

	@Test
	public void testParse() 
	{
		PersonReader pr = new PersonReader();
		Person p = pr.parse("John,Brown,25,British,red");
		
		assertTrue("Wrong name",p.getFirst_name().compareTo("John") == 0);
		assertTrue("Wrong surname",p.getLast_name().compareTo("Brown") == 0);
		assertTrue("Wrong age",p.getAge() == 25);
		assertTrue("Wrong color",p.getFavourite_colour().compareTo("red") == 0);
	}

}
