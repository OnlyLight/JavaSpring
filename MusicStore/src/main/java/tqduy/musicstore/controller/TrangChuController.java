package tqduy.musicstore.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tqduy.musicstore.dao.AccountDAO;
import tqduy.musicstore.dao.ProductDAO;
import tqduy.musicstore.entity.Account;
import tqduy.musicstore.entity.Product;

@Controller
public class TrangChuController {
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	private static Account accountLoginMain;
	private static Account accountAdminLogin;
	
	@RequestMapping("/")
	public String viewIndex(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("loginDone");
		accountLoginMain = account;
		
		checkUserLogin(model);
		
		return "index2";
	}
	
	@RequestMapping("/product-list")
	public String viewProduct(ModelMap model, HttpServletRequest request) {
		List<Product> listProduct = productDAO.getListProduct();
		model.addAttribute("list", listProduct);
		
		if(accountLoginMain == null) System.out.println("Check Product: NULL");
		else System.out.println("Check Product: NOT NULL");
		
		checkUserLogin(model);
		
		return "product-list2";
	}
	
	@RequestMapping("/admin")
	public String viewAdmin(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account accoutAdmin = (Account) session.getAttribute("loginAdmin");
		Account accountLogin = (Account) session.getAttribute("loginDone");
		System.out.println("Account Admin: " + accoutAdmin);
		System.out.println("Account Staff: " + accountLogin);
		
		checkUserLogin(model);
		
		if(accountLogin == null) {
			return "login2";
		}
		else if(accoutAdmin != null) {
			return "admin2";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("/buyProduct")
	public String listProduct(@RequestParam(value = "code", defaultValue = "") String code, ModelMap model) {
		List<Product> list = null;
		if(code != null && code.length() > 0) {
			list = productDAO.findProduct(code);
		}
		model.addAttribute("listProductBuy", list);
		
		checkUserLogin(model);
		
		return "cart2";
	}
	
	@RequestMapping("/login")
	public String checkLogin(ModelMap model, HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		
		Account account = new Account();
		account.setUserName(userName);
		account.setPassWord(passWord);
		
		Account loginDone = (Account) session.getAttribute("loginDone");
		Account loginAdminDone = (Account) session.getAttribute("loginAdmin");
		if(loginDone != null || loginAdminDone != null) {
			System.out.println("login Done !!");
			return "redirect:/";
		}
		
		if(!accountDAO.checkUser(account)) {
			System.out.println("login check !!");
			return "login2";
		}
		
		session.setAttribute("loginDone", account);
		System.out.println("UserName Admin: " + userName + " - Pass: " + passWord);
		if(accountDAO.checkUserAdmin(account)) {
			session.setAttribute("loginAdmin", account);
			accountAdminLogin = account;
			return "admin2";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(ModelMap model, HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		Account accoutLogin = (Account) session.getAttribute("loginDone");
		
		if(accoutLogin == null) {
			return "login2";
		} else {
			session.removeAttribute("loginAdmin");
			session.removeAttribute("loginDone");
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/download")
	public String downLoad(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(accountLoginMain == null) {
			return "login2";
		}
		
		return "redirect:/";
	}
	
	private void checkUserLogin(ModelMap model) {
		if(accountLoginMain == null) {
			model.addAttribute("check", false);
			System.out.println("Check Login: false");
		} else {
			model.addAttribute("check", true);
			model.addAttribute("done", accountLoginMain);
			System.out.println("Check Login: true");
		}
	}
	
	public static void checkAdminLogin(ModelMap model) {
		if(accountAdminLogin == null) {
			model.addAttribute("check", false);
			System.out.println("Account Admin Login page: false");
		} else {
			model.addAttribute("check", true);
			model.addAttribute("done", accountAdminLogin);
			System.out.println("Account Admin Login page: true");
		}
	}
}
