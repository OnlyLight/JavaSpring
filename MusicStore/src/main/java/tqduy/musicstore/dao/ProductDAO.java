package tqduy.musicstore.dao;

import java.util.List;

import tqduy.musicstore.entity.Product;

public interface ProductDAO {
	public List<Product> getListProduct();
	public List<Product> findProduct(String code);
	public void insertMusic(Product product);
	public void editMusic(Product product);
	public void deleteMusic(Product product);
}
