package org.embl.person;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PersonWriterTest {

	@Test
	public void testToJSONStringPerson() 
	{
		Person p = new Person("John","Brown",25,"red");
		PersonWriter pw = new PersonWriter();
		
		String s = pw.toJSONString(p);
		assertTrue("Person json", s.compareTo("{\"first_name\":\"John\",\"last_name\":\"Brown\"," + 
				                   "\"age\":\"25\",\"favourite_colour\":\"red\"}") == 0);
	}

	@Test
	public void testToJSONStringListOfPerson() 
	{
		Person p1 = new Person("John","Brown",25,"red");
		Person p2 = new Person("Mike","White",32,"blue");
		
		List<Person> pl = new ArrayList<Person>();
		pl.add(p1);
		pl.add(p2);
		
		PersonWriter pw = new PersonWriter();
		String s = pw.toJSONString(pl);
		
		String right = "{\"person\":[{\"first_name\":\"John\",\"last_name\":\"Brown\"," + 
                "\"age\":\"25\",\"favourite_colour\":\"red\"},"+
				"{\"first_name\":\"Mike\",\"last_name\":\"White\"," + 
			    "\"age\":\"32\",\"favourite_colour\":\"blue\"}]}";
		
		System.out.println("Right:"+right);
		
		System.out.println("Out:"+s);
		
		assertTrue("Person list json", s.compareTo(right) == 0);
	}

}
