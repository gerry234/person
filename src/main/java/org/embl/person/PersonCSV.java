package org.embl.person;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

public class PersonCSV 
{
	private static boolean quiet  = false;
	private static boolean verbose= false;
	private static boolean props  = false;
	private static String propsfile;
	private static boolean batch  = false;
	private static boolean header = false;
	private static boolean file   = false;
	private static String filename;
	private static String action  = "filter";

	public static void main(String[] args) 
	{
		parseArgs(args);
		
		PersonCSV pcsv;
		try 
		{
			pcsv = new PersonCSV();
			pcsv.go();
		} 
		catch (Exception e) 
		{
			System.err.println(e.getLocalizedMessage());
		} 
		
	}

	private static void parseArgs(String[] args) 
	{
		int i = 0;
		while (i < args.length) 
		{
			if (args[i].compareTo("-q") == 0) 
			{
				quiet = true;
				verbose = false;
				i++;
			}
			else if (args[i].compareTo("-v") == 0) 
			{
				quiet = false;
				verbose = true;
				i++;
			}
			else if (args[i].compareTo("-p") == 0) 
			{
				props = true;
				i++;
				propsfile = args[i];
				i++;
			}
			else if (args[i].compareTo("-b") == 0) 
			{
				batch = true;
				i++;
			}
			else if (args[i].compareTo("-h") == 0) 
			{
				header = true;
				i++;
			}
			else if (args[i].compareTo("-f") == 0) 
			{
				file = true;
				i++;
				filename = args[i];
				i++;
			}
			else if (args[i].compareTo("-help") == 0) 
			{
				printUsage();
			}
			else 
			{
				action = args[i];
				i++;
			}
		}
	}

	private static void printUsage() 
	{
		System.out.println("Usage: java -jar org.embl.person [-q] [-v] [-p propsfile] [-b] [-h] [-f inputfile] [filter|upload]");
		System.out.println(" -q - quiet mode");
		System.out.println(" -v - verbose mode");
		System.out.println(" -p - use properties from propfile (default: internal)");
		System.out.println(" -b - batch mode: stores persons parsed, then process them when input is done");
		System.out.println(" -h - use first line of input as header list (default: \"first_name\",\"surname\",\"age\",\"nationality\",\"favourite_colour\")");
		System.out.println(" -f - use filename as input");
		System.out.println(" filter - parse person entities, then send them to console as json strings");
		System.out.println(" upload - parse person entities, then upload them to a remote server");
		System.out.println(" -help - print this help, then exit");
		System.exit(0);
	}

	private Properties properties;
	private PersonReader reader;
	private PersonWriter writer;
	private ArrayList<Person> personlist;
	private BufferedReader console;

	public PersonCSV() throws FileNotFoundException, IOException
	{
		parseProps();
	}
	public void go() throws URISyntaxException, IOException
	{
		String s = properties.getProperty("personreader.headers", "first_name,surname,age,nationality,favourite_colour");
		reader = new PersonReader();
		reader.setHeaders(s);
		
		writer = new PersonWriter();
		
		if (batch) 
		{
			personlist = new ArrayList<Person>();
			debug("batch is " + batch);
		}
		if (file) 
		{
			InputStream is = new FileInputStream(filename);
			System.setIn(is);
		}
		console = new BufferedReader(new InputStreamReader(System.in));//System.console();
		if (header) 
		{
			String heads = console.readLine();
			reader.setHeaders(heads);
			debug("Headers: " + heads);
		}
		if (action.compareTo("filter") == 0) 
		{
			debug("Action is "+action);
			filter();
		}
		else if (action.compareTo("upload") == 0) 
		{
			debug("Action is "+action);
			upload();
		}
		else 
		{
			throw new IllegalArgumentException("Action unknown");
		}
	}

	private void upload() throws URISyntaxException, IOException 
	{
		String s = properties.getProperty("personsender.serverurl", "http://localhost:8080/insert");
		String jv = properties.getProperty("personsender.jsonvar", "person");
		String m = properties.getProperty("personsender.servermethod", "POST");
		PersonSender ps = new PersonSender(s, jv, m);
		
		String record;
		Person p;
		
		while ((record = console.readLine()) != null) 
		{
			p = reader.parse(record);
			if (batch) 
			{
				info("Person read: "+ p);
				personlist.add(p);
			}
			else
			{
				int code = ps.upload(writer.toJSONString(p));
				info("Uploaded "+ p +": " + code +" - "+ps.getRetmsg());
			}
		}
		
		if (batch) 
		{
			int code = ps.upload(writer.toJSONString(personlist));
			info("Upload done: server returned code "+code+" - "+ ps.getRetmsg());
		}
		
	}

	private void filter() throws IOException 
	{
        String record;
		Person p;
		
		while ((record = console.readLine()) != null) 
		{
			p = reader.parse(record);
			if (batch) 
			{
				info("Person read: "+ p);
				personlist.add(p);
			}
			else
			{
				System.out.printf("%s\n", writer.toJSONString(p));
			}
		}
		
		if (batch) 
		{
			System.out.printf("%s\n", writer.toJSONString(personlist).replaceAll("},", "},\n"));
		}
	}

	private void info(String infomsg) 
	{
		if (!quiet) 
		{
			System.out.printf("%s\n", infomsg);
		}
	}

	private void debug(String dbgmsg) 
	{
		if (verbose) 
		{
			System.err.println(dbgmsg);
		}
	}
	
	private void parseProps() throws FileNotFoundException, IOException 
	{
		properties = new Properties();
		if (props) 
		{
			try(InputStream is = new FileInputStream(propsfile))
			{
				properties.load(is);
				is.close();
			}
		}
		else 
		{
			try(InputStream is = getClass().getResourceAsStream("/personcsv.properties"))
			{
				debug("Default Stream Opened");
				properties.load(is);
				is.close();
			}
		}
	}

}
