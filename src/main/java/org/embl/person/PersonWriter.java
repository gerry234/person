/**
 * Utility class to write Person entities to json strings
 */
package org.embl.person;

import java.util.List;

/**
 * @author Gerardo Dinuzzi
 *
 */
public class PersonWriter 
{
	public String toJSONString(Person p)
	{
		return new StringBuilder("{").
                   append("\"first_name\":\"").append(p.getFirst_name()).append("\",").
                   append("\"last_name\":\"").append(p.getLast_name()).append("\",").
                   append("\"age\":\"").append(Integer.toString(p.getAge())).append("\",").
                   append("\"favourite_colour\":\"").append(p.getFavourite_colour()).append("\"}").
                   toString();
	}
	
	public String toJSONString(List<Person> pl)
	{
		StringBuilder sb = new StringBuilder("{\"person\":[");
		for (Person p : pl) 
		{
			sb = sb.append(toJSONString(p)).append(",");
		}
		sb.setCharAt(sb.length() - 1, ']');
		sb = sb.append("}");
		return sb.toString();
	}
	
}
