package rss_Server.Model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the Account database table.
 * 
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String USERNAME = "USERNAME";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String email;

	private String password;

	private String username;

	//bi-directional many-to-one association to MailFrequency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="frequency")
	private MailFrequency mailFrequency;

	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="account", cascade = CascadeType.ALL)
	private List<Category> categories;

	//bi-directional many-to-one association to UserChannel
	@OneToMany(mappedBy="account", cascade = CascadeType.ALL)
	private List<UserChannel> userChannels;

	public Account() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MailFrequency getMailFrequency() {
		return this.mailFrequency;
	}

	public void setMailFrequency(MailFrequency mailFrequency) {
		this.mailFrequency = mailFrequency;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
		category.setAccount(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
		category.setAccount(null);

		return category;
	}

	public List<UserChannel> getUserChannels() {
		return this.userChannels;
	}

	public void setUserChannels(List<UserChannel> userChannels) {
		this.userChannels = userChannels;
	}

	public UserChannel addUserChannel(UserChannel userChannel) {
		getUserChannels().add(userChannel);
		userChannel.setAccount(this);

		return userChannel;
	}

	public UserChannel removeUserChannel(UserChannel userChannel) {
		getUserChannels().remove(userChannel);
		userChannel.setAccount(null);

		return userChannel;
	}

}