package rss_Server.Model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the UserChannel database table.
 * 
 */
@Entity
@NamedQuery(name="UserChannel.findAll", query="SELECT u FROM UserChannel u")
public class UserChannel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String channelURL;

	//bi-directional many-to-one association to ChannelCategory
	@OneToMany(mappedBy="userChannel",cascade = CascadeType.ALL)
	private List<ChannelCategory> channelCategories;

	//bi-directional many-to-one association to NewsArchive
	@OneToMany(mappedBy="userChannel", cascade = CascadeType.ALL)
	private List<NewsArchive> newsArchives;

	//bi-directional many-to-one association to Account
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userID")
	private Account account;

	public UserChannel() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChannelURL() {
		return this.channelURL;
	}

	public void setChannelURL(String channelURL) {
		this.channelURL = channelURL;
	}

	public List<ChannelCategory> getChannelCategories() {
		return this.channelCategories;
	}

	public void setChannelCategories(List<ChannelCategory> channelCategories) {
		this.channelCategories = channelCategories;
	}

	public ChannelCategory addChannelCategory(ChannelCategory channelCategory) {
		getChannelCategories().add(channelCategory);
		channelCategory.setUserChannel(this);

		return channelCategory;
	}

	public ChannelCategory removeChannelCategory(ChannelCategory channelCategory) {
		getChannelCategories().remove(channelCategory);
		channelCategory.setUserChannel(null);

		return channelCategory;
	}

	public List<NewsArchive> getNewsArchives() {
		return this.newsArchives;
	}

	public void setNewsArchives(List<NewsArchive> newsArchives) {
		this.newsArchives = newsArchives;
	}

	public NewsArchive addNewsArchive(NewsArchive newsArchive) {
		getNewsArchives().add(newsArchive);
		newsArchive.setUserChannel(this);

		return newsArchive;
	}

	public NewsArchive removeNewsArchive(NewsArchive newsArchive) {
		getNewsArchives().remove(newsArchive);
		newsArchive.setUserChannel(null);

		return newsArchive;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}