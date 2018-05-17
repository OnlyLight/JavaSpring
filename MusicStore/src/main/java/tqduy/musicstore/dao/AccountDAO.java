package tqduy.musicstore.dao;

import java.util.List;

import tqduy.musicstore.entity.Account;

public interface AccountDAO {
	public List<Account> getListAccount();
	public boolean checkUser(Account account);
	public boolean checkUserAdmin(Account account);
	public void insertAccount(Account account);
	public void editAccount(Account account);
	public void deleteAccount(Account account);
	public List<Account> findAccount(int id);
}
