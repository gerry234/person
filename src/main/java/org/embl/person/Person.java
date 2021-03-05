package org.embl.person;

import java.io.Serializable;

/**
 * Entity representing Persons
 */

/**
 * @author Gerardo Dinuzzi
 *
 */
public class Person implements Serializable 
{
	private String first_name;
	private String last_name;
	private int age;
	private String favourite_colour;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getFavourite_colour() {
		return favourite_colour;
	}
	public void setFavourite_color(String favourite_colour) {
		this.favourite_colour = favourite_colour;
	}
	
	public Person() {}
	public Person(String first_name, String last_name, int age, String favourite_colour) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.favourite_colour = favourite_colour;
	}
	@Override
	public String toString() {
		return "Person [first_name=" + first_name + ", last_name=" + last_name + ", age=" + age + ", favourite_color="
				+ favourite_colour + "]";
	};
}
