package rss_Server.Model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ChannelCategory database table.
 * 
 */
@Entity
@NamedQuery(name="ChannelCategory.findAll", query="SELECT c FROM ChannelCategory c")
public class ChannelCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	//bi-directional many-to-one association to Category
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoryID")
	private Category category;

	//bi-directional many-to-one association to UserChannel
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="channelID")
	private UserChannel userChannel;

	public ChannelCategory() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public UserChannel getUserChannel() {
		return this.userChannel;
	}

	public void setUserChannel(UserChannel userChannel) {
		this.userChannel = userChannel;
	}

}