package tqduy.musicstore.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tqduy.musicstore.dao.imp.AccountDAOImp;
import tqduy.musicstore.dao.imp.ProductDAOImp;
import tqduy.musicstore.entity.Account;
import tqduy.musicstore.entity.Product;

@Controller
public class TrangChuController {
	
	@RequestMapping("/")
	public String viewIndex(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("loginDone");
		if(account == null) {
			model.addAttribute("check", false);
			System.out.println("Check Login: false");
		} else {
			model.addAttribute("check", true);
			model.addAttribute("done", account);
			System.out.println("Check Login: true");
		}
		
		return "index";
	}
	
	@RequestMapping("/product-list")
	public String viewProduct(ModelMap model) {
		List<Product> listProduct = new ProductDAOImp().getListProduct();
		model.addAttribute("list", listProduct);
		return "product-list";
	}
	
	@RequestMapping("/admin")
	public String viewAdmin(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account accoutAdmin = (Account) session.getAttribute("loginAdmin");
		Account accountLogin = (Account) session.getAttribute("loginDone");
		System.out.println("Account Admin: " + accoutAdmin);
		System.out.println("Account Staff: " + accountLogin);
		
		if(accountLogin == null) {
			return "login";
		}
		else if(accoutAdmin != null) {
			return "admin";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("/buyProduct")
	public String listProduct(@RequestParam(value = "code", defaultValue = "") String code, ModelMap model) {
		List<Product> list = null;
		if(code != null && code.length() > 0) {
			list = new ProductDAOImp().findProduct(code);
		}
		model.addAttribute("listProductBuy", list);
		return "cart";
	}
	
	@RequestMapping("/login")
	public String checkLogin(ModelMap model, HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		
		Account account = new Account();
		AccountDAOImp accountDAOImp = new AccountDAOImp();
		account.setUserName(userName);
		account.setPassWord(passWord);
		
		Account loginDone = (Account) session.getAttribute("loginDone");
		Account loginAdminDone = (Account) session.getAttribute("loginAdmin");
		if(loginDone != null || loginAdminDone != null) {
			System.out.println("login Done !!");
			return "redirect:/";
		}
		
		if(!accountDAOImp.checkUser(account)) {
			System.out.println("login check !!");
			return "login";
		}
		
		session.setAttribute("loginDone", account);
		System.out.println("UserName Admin: " + userName + " - Pass: " + passWord);
		if(accountDAOImp.checkUserAdmin(account)) {
			session.setAttribute("loginAdmin", account);
			return "admin";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(ModelMap model, HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		Account accoutLogin = (Account) session.getAttribute("loginDone");
		
		if(accoutLogin == null) {
			return "login";
		} else {
			session.removeAttribute("loginAdmin");
			session.removeAttribute("loginDone");
		}
		
		return "redirect:/";
	}
}
