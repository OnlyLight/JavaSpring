package tqduy.musicstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Product")
public class Product {
	@Id
	@Column(name="Code")
	private int code;
	
	@Column(name="Name", nullable = false)
	private String name;
	
	@Column(name="Price", nullable = false)
	private float price;
	
	@Column(name="URL")
	private String url;

	public Product() {
	}

	public Product(int code, String name, float price, String url) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.url = url;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
