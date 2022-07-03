package rss_Server.Model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the NewsArchive database table.
 * 
 */
@Entity
@NamedQuery(name="NewsArchive.findAll", query="SELECT n FROM NewsArchive n")
public class NewsArchive implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String image;

	private String link;

	private Time pubDate;

	private String title;

	//bi-directional many-to-one association to UserChannel
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userChannelID")
	private UserChannel userChannel;

	public NewsArchive() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Time getPubDate() {
		return this.pubDate;
	}

	public void setPubDate(Time pubDate) {
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserChannel getUserChannel() {
		return this.userChannel;
	}

	public void setUserChannel(UserChannel userChannel) {
		this.userChannel = userChannel;
	}

}