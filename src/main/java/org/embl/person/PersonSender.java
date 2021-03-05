/**
 * Utility class to upload Person entities to a remote RESTFull service
 */

package org.embl.person;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Gerardo Dinuzzi
 *
 */
public class PersonSender 
{
	private String serverurl = "http://localhost:8080/insert";
	private String jsonvar = "person";
	private String servermethod = "POST";
	
	private URI serverURI;
	private URL serverURL;
	private int retcode;
	private String retmsg;
	
	public String getServerurl() {
		return serverurl;
	}
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	public String getJsonvar() {
		return jsonvar;
	}
	public void setJsonvar(String jsonvar) {
		this.jsonvar = jsonvar;
	}
	public String getServermethod() {
		return servermethod;
	}
	public void setServermethod(String servermethod) {
		this.servermethod = servermethod;
	}
	public int getRetcode() {
		return retcode;
	}
	public String getRetmsg() {
		return retmsg;
	}
	public PersonSender() throws URISyntaxException, MalformedURLException {
		super();
		serverURI = new URI(serverurl);
		serverURL = serverURI.toURL();
	}
	public PersonSender(String serverurl, String jsonvar, String servermethod) throws URISyntaxException, MalformedURLException {
		super();
		this.serverurl = serverurl;
		this.jsonvar = jsonvar;
		this.servermethod = servermethod;
		
		serverURI = new URI(serverurl);
		serverURL = serverURI.toURL();
	}
	
	public int upload(String jsonperson) throws IOException
	{
		HttpURLConnection huc =(HttpURLConnection) serverURL.openConnection();
		
    	huc.setUseCaches(false);
    	huc.setRequestMethod(servermethod);
    	huc.setRequestProperty(jsonvar,jsonperson);
    	huc.setFollowRedirects(true);
    	
    	huc.connect();
    	retcode = huc.getResponseCode();
    	retmsg  = huc.getResponseMessage();
    	huc.disconnect();
    	
    	return retcode;
	}
}
