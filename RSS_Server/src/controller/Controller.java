package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;


import helpers.FormDataMode;
import rss_Server.Model.Account;
import rss_Server.Model.Category;
import rss_Server.Model.ChannelCategory;
import rss_Server.Model.MailFrequency;
import rss_Server.Model.UserChannel;






public class Controller {

	static EntityManager em;
	public EntityManager getEntityManager(){
		if(em==null){
			em=Persistence.createEntityManagerFactory("RSS_Server2").createEntityManager();
		}
		return em;
	}
	


	public List<Account> getAllAccounts(){
		return getEntityManager().createQuery("Select a from Account a order by a.id").getResultList();
		}

	
	public void saveAccount(FormDataMode dataMode, Account dataObject) {
		EntityManager em=getEntityManager();
		em.getTransaction().begin();
		if(dataMode==FormDataMode.ADD){
			em.persist(dataObject);
		}
		else{
			em.merge(dataObject);
		}
		em.getTransaction().commit();
		
	}
	
	public Account findAccount(String username, String password)//find account to log in
	{
		List <Account> result = new ArrayList();
		Query query = getEntityManager().createNativeQuery("SELECT * from Account WHERE username = ? AND password = ?",Account.class);
		query.setParameter(1, username);
		query.setParameter(2, password);
		try{
			result = query.getResultList();
			
			
		}catch(javax.persistence.NoResultException e) {
			return null;
		}
		if (result.size()==0)
			return null;
		else
		{
			return  result.get(0);
		}
		
	}
	
	public Account findAccountByLogin(String username)//find account to sign in
	{
		List<Account> result = new ArrayList();
		Query query = getEntityManager().createNativeQuery("SELECT * from Account WHERE username = ?", Account.class);
		query.setParameter(1, username);
		try{
			result = query.getResultList();
			
		}catch(javax.persistence.NoResultException e) {
			return null;
		}
		if (result.size()==0)
			return null;
		else
		{
			return result.get(0);
		}
	}
	
	public void saveChannel(FormDataMode dataMode, UserChannel dataObject) {
		EntityManager em=getEntityManager();
		em.getTransaction().begin();
		if(dataMode==FormDataMode.ADD){
			em.persist(dataObject);
		}
		else{
			em.merge(dataObject);
		}
		em.getTransaction().commit();
		
	}
	
	public MailFrequency getFrequency(String frequency){
		List <MailFrequency> result = new ArrayList();
		Query query = getEntityManager().createNativeQuery("Select * from MailFrequency where frequency = ?", MailFrequency.class);
		query.setParameter(1, frequency);
		try{
			result = query.getResultList();
			
		}catch(javax.persistence.NoResultException e) {
			return null;
		}
		if (result.size()==0)
			return null;
		else
		{
			return result.get(0);
		}
	}
	
	
	public List<UserChannel> getChannelsForUser(Account user) //getting channels for logged in user
	{
		List <UserChannel> result = new ArrayList();
		Query query = getEntityManager().createNativeQuery("SELECT * from UserChannel WHERE userID = ?",UserChannel.class);
		query.setParameter(1, user.getId());
		try{
			result = query.getResultList();

		}catch(javax.persistence.NoResultException e) {
			return null;
		}
		if (result.size()==0)
			return null;
		else
		{
			return  result;
		}
	}
	
	public List<UserChannel> getChannelsForURL(String url, String userid)
	{
		List <UserChannel> result = new ArrayList();
		Query query = getEntityManager().createNativeQuery("SELECT * from UserChannel WHERE channelURL = ? AND userID = ?",UserChannel.class);
		query.setParameter(1, url);
		query.setParameter(2, userid);
		try{
			result = query.getResultList();

		}catch(javax.persistence.NoResultException e) {
			return null;
		}
		if (result.size()==0)
			return null;
		else
		{
			return  result;
		}
	}
	
	public <T> void  removeObject(T dataObject) {
		EntityManager em=getEntityManager();
		em.getTransaction().begin();
		em.remove(dataObject);
		em.getTransaction().commit();
		
	}
	
	public List<Category> getCategoriesForChannel(String url, String login)
	{
		List <Category> result = new ArrayList();
		Query query = getEntityManager().createNativeQuery("SELECT c.* from Category as c INNER JOIN ChannelCategory AS cc ON c.id = cc.categoryID INNER JOIN UserChannel AS uc ON cc.channelID = uc.id INNER JOIN Account AS a on uc.userID = a.id WHERE uc.channelURL = ? AND a.username = ?",Category.class);
		query.setParameter(1, url);
		query.setParameter(2, login);
		try{
			result = query.getResultList();

		}catch(javax.persistence.NoResultException e) {
			return null;
		}
		if (result.size()==0)
			return null;
		else
		{
			return  result;
		}
		
	}
	
	public void saveCategory(FormDataMode dataMode, Category dataObject) {
		EntityManager em=getEntityManager();
		em.getTransaction().begin();
		if(dataMode==FormDataMode.ADD){
			em.persist(dataObject);
		}
		else{
			em.merge(dataObject);
		}
		em.getTransaction().commit();
		
	}
	
	
	public void saveChannelCategory(FormDataMode dataMode, ChannelCategory dataObject) {
		EntityManager em=getEntityManager();
		em.getTransaction().begin();
		if(dataMode==FormDataMode.ADD){
			em.persist(dataObject);
		}
		else{
			em.merge(dataObject);
		}
		em.getTransaction().commit();
		
	}

}
	
	
	

