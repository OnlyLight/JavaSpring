package tqduy.musicstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tqduy.musicstore.dao.ProductDAO;
import tqduy.musicstore.dao.imp.ProductDAOImp;
import tqduy.musicstore.entity.Product;

@Controller
@RequestMapping("/admin/music")
public class UpgradeMusicController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping()
	public String viewMusic(ModelMap model, HttpServletRequest request) {
		loadList(model);
		TrangChuController.checkAdminLogin(model);
		
		return "music2";
	}
	
	@RequestMapping(params="add", method=RequestMethod.POST)
	public String upMusic(ModelMap model, HttpServletRequest request) {
		insert(model, request);
		
		TrangChuController.checkAdminLogin(model);
		
		loadList(model);
		return "music2";
	}
	
	@RequestMapping(params="edit", method=RequestMethod.POST)
	public String editMusic(ModelMap model, HttpServletRequest request) {
		edit(model, request);
		
		TrangChuController.checkAdminLogin(model);
		
		loadList(model);
		return "music2";
	}
	
	@RequestMapping(params="delete", method=RequestMethod.POST)
	public String deleteMusic(ModelMap model, HttpServletRequest request) {
		delete(model, request);
		
		TrangChuController.checkAdminLogin(model);
		
		loadList(model);
		return "music2";
	}
	
	private void insert(ModelMap model, HttpServletRequest request) {
		int code = 0;
		String name = null;
		float price = 0;
		String url = null;
		try {
			code = Integer.parseInt(request.getParameter("code"));
			name = request.getParameter("name");
			price = Float.parseFloat(request.getParameter("price"));
			url = request.getParameter("url");
		} catch (Exception e) {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
		if(name.isEmpty()) return;
		productDAO.insertMusic(new Product(code, name, price, url));
	}
	
	private void edit(ModelMap model, HttpServletRequest request) {
		int code = 0;
		String name = null;
		float price = 0;
		String url = null;
		try {
			code = Integer.parseInt(request.getParameter("code"));
			name = request.getParameter("name");
			price = Float.parseFloat(request.getParameter("price"));
			url = request.getParameter("url");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
		List<Product> list = new ProductDAOImp().findProduct(String.valueOf(code));
		if(!list.isEmpty()) {
			productDAO.editMusic(new Product(code, name, price, url));
		} else {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Number not found !!");
		}
	}
	
	private void delete(ModelMap model, HttpServletRequest request) {
		int code = 0;
		try {
			code = Integer.parseInt(request.getParameter("code"));
		} catch (Exception e) {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
		List<Product> list = new ProductDAOImp().findProduct(String.valueOf(code));
		if(!list.isEmpty()) {
			Product product = null;
			
			for (Product productSub : list) {
				product = new Product(productSub.getCode(), productSub.getName(), productSub.getPrice(), productSub.getUrl());
			}
//			new ProductDAOImp().deleteMusic(product);
			productDAO.deleteMusic(product);
		} else {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Number not found !!");
		}
	}
	
	private void loadList(ModelMap model) {
		List<Product> listProduct = productDAO.getListProduct();
		model.addAttribute("listProduct", listProduct);
	}
}
