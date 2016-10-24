package edu.costs.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPENDINGS")
public class Spending implements Identifiable {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "{spending.timestamp.null}")
	private LocalDateTime timestamp;
	@Min(value = 0, message = "{spending.amount.min}")
	private double amount;
	private String[] tags;
	@OneToOne
	@NotNull(message = "{spending.owner.null}")
	private User owner;
	@NotNull(message = "{Spending.author.null}")
	@OneToOne
	private User author;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
