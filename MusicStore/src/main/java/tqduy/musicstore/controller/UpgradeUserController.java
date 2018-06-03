package tqduy.musicstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tqduy.musicstore.dao.AccountDAO;
import tqduy.musicstore.dao.imp.AccountDAOImp;
import tqduy.musicstore.entity.Account;

@Controller
@RequestMapping("/admin/user")
public class UpgradeUserController {
	
	@Autowired
	private AccountDAO accountDAO;
	
	@RequestMapping()
	public String viewMusic(ModelMap model, HttpServletRequest request) {
		loadList(model);
		
		TrangChuController.checkAdminLogin(model);
		
		return "user2";
	}
	
	@RequestMapping(params="addAccount", method=RequestMethod.POST)
	public String upAccount(ModelMap model, HttpServletRequest request) {
		insert(model, request);
		
		TrangChuController.checkAdminLogin(model);
		
		loadList(model);
		return "user2";
	}
	
	@RequestMapping(params="editAccount", method=RequestMethod.POST)
	public String editAccount(ModelMap model, HttpServletRequest request) {
		edit(model, request);
		
		TrangChuController.checkAdminLogin(model);
		
		loadList(model);
		return "user2";
	}
	
	@RequestMapping(params="deleteAccount", method=RequestMethod.POST)
	public String deletAccount(ModelMap model, HttpServletRequest request) {
		delete(model, request);
		
		TrangChuController.checkAdminLogin(model);
		
		loadList(model);
		return "user2";
	}
	
	private void insert(ModelMap model, HttpServletRequest request) {
		int id = 0;
		String userName = null;
		String passWord = null;
		String role = null;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			userName = request.getParameter("userName");
			passWord = request.getParameter("passWord");
			role = request.getParameter("role");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
//		new AccountDAOImp().insertAccount(new Account(id, userName, passWord, role));
		if(userName.isEmpty() || passWord.isEmpty() || role.isEmpty()) {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
		accountDAO.insertAccount(new Account(id, userName, passWord, role));
	}
	
	private void edit(ModelMap model, HttpServletRequest request) {
		int id = 0;
		String userName = null;
		String passWord = null;
		String role = null;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			userName = request.getParameter("userName");
			passWord = request.getParameter("passWord");
			role = request.getParameter("role");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
		List<Account> list = new AccountDAOImp().findAccount(id);
		if(!list.isEmpty()) {
//			new AccountDAOImp().editAccount(new Account(id, userName, passWord, role));
			accountDAO.editAccount(new Account(id, userName, passWord, role));
		} else {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Number not found !!");
		}
	}
	
	private void delete(ModelMap model, HttpServletRequest request) {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Please enter again !!");
			return;
		}
		List<Account> list = new AccountDAOImp().findAccount(id);
		if(!list.isEmpty()) {
			Account account = null;
			
			for (Account accountSub : list) {
				account = new Account(accountSub.getId(), accountSub.getUserName(), accountSub.getPassWord(), accountSub.getRole());
			}
//			new AccountDAOImp().deleteAccount(account);
			accountDAO.deleteAccount(account);
		} else {
			model.addAttribute("checkError", true);
			model.addAttribute("error", "Number not found !!");
		}
	}

	private void loadList(ModelMap model) {
		List<Account> list = accountDAO.getListAccount();
		for (Account account : list) {
			System.out.println("List Account: \nID: " + account.getId() + " - UserName: " + account.getUserName() + " - " + account.getPassWord());
		}
		model.addAttribute("listAccount", list);
	}
}
