package rss_Server.Model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String category;

	//bi-directional many-to-one association to Account
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userID")
	private Account account;

	//bi-directional many-to-one association to ChannelCategory
	@OneToMany(mappedBy="category", cascade = CascadeType.ALL)
	private List<ChannelCategory> channelCategories;

	public Category() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<ChannelCategory> getChannelCategories() {
		return this.channelCategories;
	}

	public void setChannelCategories(List<ChannelCategory> channelCategories) {
		this.channelCategories = channelCategories;
	}

	public ChannelCategory addChannelCategory(ChannelCategory channelCategory) {
		getChannelCategories().add(channelCategory);
		channelCategory.setCategory(this);

		return channelCategory;
	}

	public ChannelCategory removeChannelCategory(ChannelCategory channelCategory) {
		getChannelCategories().remove(channelCategory);
		channelCategory.setCategory(null);

		return channelCategory;
	}

}