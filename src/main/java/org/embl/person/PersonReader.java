package org.embl.person;

import java.util.Map;
import java.util.TreeMap;


/**
 *  Utility class to parse Person entities from comma separated strings
 */

/**
 * @author Gerardo Dinuzzi
 *
 */
public class PersonReader 
{
	private String[] headers= {"first_name","surname","age","nationality","favourite_colour"};
	private Map<String,String> mapper;

	public void setHeaders(String heads)
	{
		headers = heads.split(",");
		for (int i = 0; i < headers.length; i++) 
		{
			headers[i] = headers[i].trim();
		}
	}
	
	public Person parse(String csvperson)
	{
		if (mapper == null) 
		{
			mapper = new TreeMap<String, String>();
		}
		else 
		{
			mapper.clear();
		}
		
		String[] values = csvperson.split(",");
		if (values.length < headers.length) 
		{
			throw new IllegalArgumentException("Not enough fields");
		}
		
		for (int i = 0; i < headers.length; i++) 
		{
			mapper.put(headers[i], values[i].trim());
		}
		
		String fname = mapper.get("first_name");
		String sname = mapper.get("surname");
		int age = Integer.parseInt(mapper.get("age"));
		String colour = mapper.get("favourite_colour");
		
		if ((fname.length()==0) || (sname.length()==0) || (colour.length()==0)) 
		{
			throw new IllegalArgumentException("Fields are mandatory");
		}
		
		return new Person(fname, sname, age, colour);
	}
}
