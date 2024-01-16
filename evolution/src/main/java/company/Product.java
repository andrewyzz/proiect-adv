package company;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Product {
	public Product() {

	}

	enum Status{
		IN_STOCK,
		OUT_OF_STOCK,
		DISCONTINUED
	}
	private @Id @GeneratedValue Long id;
	private String name;
	private Integer price;

	private Integer quantity;
	private Boolean available;

	private Status product_status;

	Product(String name, Integer price, Integer quantity, Boolean available, Status productStatus) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.available = available;
		this.product_status = productStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Status getProductStatus() {
		return product_status;
	}

	public void setProductStatus(Status productStatus) {
		this.product_status = productStatus;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Product))
			return false;
		Product product = (Product) o;
		return Objects.equals(this.id, product.id) && Objects.equals(this.name, product.name)
				&& Objects.equals(this.price, product.price) && Objects.equals(this.quantity, product.quantity)
				&& Objects.equals(this.available,product.available) && Objects.equals(this.product_status,product.product_status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.price, this.quantity,this.available,this.product_status);
	}

	@Override
	public String toString() {
		return "Product{" + "id=" + this.id + ", Name='" + this.name + '\'' + ", price='" + this.price
				+ '\'' + ", quantity='" + this.quantity + '\''  + ", available='" + this.available + '\''  + ", status='" + this.product_status + '}';
	}
}
