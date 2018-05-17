package tqduy.musicstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tqduy.musicstore.dao.imp.ProductDAOImp;
import tqduy.musicstore.entity.Account;
import tqduy.musicstore.entity.Product;

@Controller
@RequestMapping("/admin/music")
public class UpgradeMusicController {
	@RequestMapping()
	public String viewMusic(ModelMap model, HttpServletRequest request) {
		loadList(model);
		return "music";
	}
	
	@RequestMapping(params="add", method=RequestMethod.POST)
	public String upMusic(ModelMap model, HttpServletRequest request) {
		insert(model, request);
		
		loadList(model);
		return "music";
	}
	
	@RequestMapping(params="edit", method=RequestMethod.POST)
	public String editMusic(ModelMap model, HttpServletRequest request) {
		edit(model, request);
		
		loadList(model);
		return "music";
	}
	
	@RequestMapping(params="delete", method=RequestMethod.POST)
	public String deleteMusic(ModelMap model, HttpServletRequest request) {
		delete(model, request);
		
		loadList(model);
		return "music";
	}
	
	private void insert(ModelMap model, HttpServletRequest request) {
		int code = 0;
		String name = null;
		float price = 0;
		try {
			code = Integer.parseInt(request.getParameter("code"));
			name = request.getParameter("name");
			price = Float.parseFloat(request.getParameter("price"));
		} catch (Exception e) {
			return;
		}
		new ProductDAOImp().insertMusic(new Product(code, name, price));
	}
	
	private void edit(ModelMap model, HttpServletRequest request) {
		int code = 0;
		String name = null;
		float price = 0;
		try {
			code = Integer.parseInt(request.getParameter("code"));
			name = request.getParameter("name");
			price = Float.parseFloat(request.getParameter("price"));
		} catch (Exception e) {
			return;
		}
		List<Product> list = new ProductDAOImp().findProduct(String.valueOf(code));
		if(!list.isEmpty()) {
			new ProductDAOImp().editMusic(new Product(code, name, price));
		}
	}
	
	private void delete(ModelMap model, HttpServletRequest request) {
		int code = 0;
		try {
			code = Integer.parseInt(request.getParameter("code"));
		} catch (Exception e) {
			return;
		}
		List<Product> list = new ProductDAOImp().findProduct(String.valueOf(code));
		if(!list.isEmpty()) {
			Product product = null;
			
			for (Product productSub : list) {
				product = new Product(productSub.getCode(), productSub.getName(), productSub.getPrice());
			}
			new ProductDAOImp().deleteMusic(product);
		}
	}
	
	private void loadList(ModelMap model) {
		List<Product> listProduct = new ProductDAOImp().getListProduct();
		model.addAttribute("listProduct", listProduct);
	}
}
