package rss_Server.Model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MailFrequency database table.
 * 
 */
@Entity
@NamedQuery(name="MailFrequency.findAll", query="SELECT m FROM MailFrequency m")
public class MailFrequency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String frequency;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="mailFrequency", cascade = CascadeType.ALL)
	private List<Account> accounts;

	public MailFrequency() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setMailFrequency(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setMailFrequency(null);

		return account;
	}

}