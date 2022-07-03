package rss_Server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.Controller;
import helpers.FormDataMode;
import rss_Server.Model.Account;
import rss_Server.Model.Category;
import rss_Server.Model.ChannelCategory;
import rss_Server.Model.MailFrequency;
import rss_Server.Model.UserChannel;

import java.util.ArrayList;
import java.util.List;

@Path("/user")

public class Hello
{
	 String hostName = "localhost";
		    String host = "XTREEM7/SQLEXPRESS";
	        String dbName = "RSS";
	        String user = "sa";
	        String password = "Password123";
	        String port = "1433";

	        

	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 @Consumes(MediaType.TEXT_PLAIN)
	  public String sayHtmlHello(@QueryParam("login") String login, @QueryParam("password") String password) {

	                
	    Controller c = new Controller();
	    Account account = c.findAccount(login, password);
	    if(account != null)
	    {
	       	return "login";
        }	
	     
		 return "";
	  }
	 
	 @Path("register")
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 @Consumes(MediaType.TEXT_PLAIN)
	  public String register(@QueryParam("login") String login, @QueryParam("password") String password, @QueryParam("email") String email) {
		System.out.println(login+password+email);
		Account user = new Account();
	    List<UserChannel> defaultList = new ArrayList<>();
	    
	    
	    Controller c = new Controller();
	    user.setMailFrequency(c.getFrequency("W ogóle"));
	                
	   
	    if(c.findAccount(login, password) != null)
	    {
	       	return "Nick";
        }
	    else
	    {
	    	user.setEmail(email);
	    	user.setUsername(login);
	    	user.setPassword(password);
	    	MailFrequency mf = new MailFrequency();
	    	mf.setId(1);
	    	mf.setFrequency("W ogóle");
	    	user.setMailFrequency(mf);
	    	
	    	
	    	UserChannel channel = new UserChannel();
		    channel.setChannelURL("http://rss.cnn.com/rss/edition.rss");
		    channel.setAccount(user);
		    defaultList.add(channel);
		    UserChannel channel2 = new UserChannel();
		    channel2.setChannelURL("https://fakty.interia.pl/feed");
		    channel2.setAccount(user);
		    defaultList.add(channel2);
		    UserChannel channel3 = new UserChannel();
		    channel3.setChannelURL("https://www.tvn24.pl/najnowsze.xml");
		    channel3.setAccount(user);
		    defaultList.add(channel3);
		    user.setUserChannels(defaultList);
		    c.saveAccount(FormDataMode.ADD, user);
	    }
	     
		 return "Success";
	  }
	 
	 @Path("channels")
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.TEXT_PLAIN)
	  public Response getUsersChannels(@QueryParam("login") String login) {

	    Response response = null;
	    Controller c = new Controller();
	    Account account = c.findAccountByLogin(login);
	    List<UserChannel> channels = c.getChannelsForUser(account);
	    JSONObject obj = new JSONObject();
		  try {
	           JSONArray list = new JSONArray();
	            
	            for (int i=0;i<channels.size();i++)
	            {
	            	 list.put(channels.get(i).getChannelURL());
	            }
	            obj.put("url", list);
	           
	            response = Response.status(Status.OK).entity(obj.toString()).build();
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
		return response;
	  }
	 
	 @Path("channelsChanges")
	 @GET
	 @Consumes(MediaType.TEXT_PLAIN)
	 @Produces(MediaType.TEXT_PLAIN)
	  public String setUsersChannels(@QueryParam("login") String login,@QueryParam("added")String added,@QueryParam("deleted") String deleted) {

	    String response = null;
	    Controller c = new Controller();
	    String[] urls =added.split(";");
	    Long id= c.findAccountByLogin(login).getId();
	    if(!added.equals("")) {
	        for (int i = 0; i<urls.length;i++)
	 	    {
	 	    	if(urls[i].charAt(urls[i].length()-1)==';') urls[i]=urls[i].substring(0, urls[i].length()-1);
	 	    	if(c.getChannelsForURL(urls[i],id.toString())==null)
	 	    	{
	 	    		if(!urls[i].equals(""))
	 	    	    {
	 	    	    	UserChannel channel = new UserChannel();
	 	    	    	channel.setChannelURL(urls[i]);
	 	    	    	Account user = c.findAccountByLogin(login);
	 	    	    	channel.setAccount(user);
	 	    	    	c.saveChannel(FormDataMode.ADD, channel);
	 	    	    }
	 	    	}
	 	    }
	    }
	   
	    
	    urls =deleted.split(";");
	    System.out.println(urls[0]);
	    if(!deleted.equals(""))
	    {
	    	for (int i = 0; i<urls.length;i++)
	    	{
	    		if(urls[i].charAt(urls[i].length()-1)==';') urls[i]=urls[i].substring(0, urls[i].length()-1);
	    		List<UserChannel> channel = c.getChannelsForURL(urls[i],id.toString());
	    		c.removeObject(channel.get(0));
	    	
	    	}
	    
	    }
	    response = "Success";
		return response;
	  }
	 
	 @Path("channelsCategories")
	 @GET
	 @Consumes(MediaType.TEXT_PLAIN)
	 @Produces(MediaType.APPLICATION_JSON)
	  public Response getCategoriesForChannel(@QueryParam("login") String login,@QueryParam("url") String url){
		 Response response = null;
		    Controller c = new Controller();
		   
		    List<Category> categories = c.getCategoriesForChannel(url, login);
		    JSONObject obj = new JSONObject();
			  try {
		           JSONArray list = new JSONArray();
		            
		            for (int i=0;i<categories.size();i++)
		            {
		            	 list.put(categories.get(i).getCategory());
		            }
		            obj.put("categories", list);
		           
		            response = Response.status(Status.OK).entity(obj.toString()).build();
		        } catch (JSONException e) {
		            e.printStackTrace();
		        }
			return response;
	  }
	 
	 @Path("categoriesChanges")
	 @GET
	 @Consumes(MediaType.TEXT_PLAIN)
	 @Produces(MediaType.TEXT_PLAIN)
	  public String setUsersCategoriesForChannel(@QueryParam("url") String url, @QueryParam("login") String login,@QueryParam("added")String added,@QueryParam("deleted") String deleted) {

	    String response = null;
	    Controller c = new Controller();
	    String[] categories =added.split(";");
	    Long id= c.findAccountByLogin(login).getId();
	    if(!added.equals("")) {
	        for (int i = 0; i<categories.length;i++)
	 	    {
	 	    	if(categories[i].charAt(categories[i].length()-1)==';') categories[i]=categories[i].substring(0, categories[i].length()-1);
	 	    	if(c.getChannelsForURL(categories[i],id.toString())==null)
	 	    	{
	 	    		if(!categories[i].equals(""))
	 	    	    {
	 	    	    	UserChannel channel = new UserChannel();
	 	    	    	channel = c.getChannelsForURL(url, id.toString()).get(0);
	 	    	    	Account account = new Account();
	 	    	    	account = c.findAccountByLogin(login);
	 	    	    	Category category = new Category();
	 	    	    	category.setCategory(categories[i]);
	 	    	    	category.setAccount(account);
	 	    	    	ChannelCategory chanCat = new ChannelCategory();
	 	    	    	chanCat.setCategory(category);
	 	    	    	chanCat.setUserChannel(channel);
	 	    	    	List<ChannelCategory> list = new ArrayList<ChannelCategory>();
	 	    	    	list.add(chanCat);
	 	    	    	
	 	    	    	category.setChannelCategories(list);
	 	    	    	c.saveCategory(FormDataMode.ADD, category);
	 	    	    }
	 	    	}
	 	    }
	    }
	   
	    
	    categories =deleted.split(";");
	    System.out.println(categories[0]);
	    if(!deleted.equals(""))
	    {
	    	for (int i = 0; i<categories.length;i++)
	    	{
	    		if(categories[i].charAt(categories[i].length()-1)==';') categories[i]=categories[i].substring(0, categories[i].length()-1);
	    		List<Category> categoriesList = c.getCategoriesForChannel(url, login);
	    		
	    		categoriesList.forEach(category->
	    		{
	    			List<ChannelCategory> chList =category.getChannelCategories();
	    			
	    			if(chList.size()==1) c.removeObject(category);
	    		});
	    	
	    	}
	    
	    }
	    response = "Success";
		return response;
	  }
	 
	 
}