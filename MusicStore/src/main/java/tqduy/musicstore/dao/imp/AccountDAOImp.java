package tqduy.musicstore.dao.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import tqduy.musicstore.dao.AccountDAO;
import tqduy.musicstore.entity.Account;
import tqduy.musicstore.entity.Product;
import tqduy.musicstore.utils.HiberneteUtils;

public class AccountDAOImp implements AccountDAO {
	private static SessionFactory factory = HiberneteUtils.getSessionFactory();

	public List<Account> getListAccount() {
		Session session = factory.getCurrentSession();
		List<Account> accounts = null;

		try {
			session.getTransaction().begin();

			String sql = "from " + Account.class.getName() + " p ";

			Query<Account> query = session.createQuery(sql);

			accounts = query.getResultList();
			for (Account account : accounts) {
				System.out.println("Name: " + account.getUserName() + " - PassWord: " + account.getPassWord());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return accounts;
	}

	public boolean checkUser(Account account) {
		Session session = factory.getCurrentSession();

		try {
			session.getTransaction().begin();

			String sql = "FROM " + Account.class.getName() + "";

			Query<Account> query = session.createQuery(sql);

			List<Account> userNames = query.getResultList();
			for (Account userName : userNames) {
				if (userName.getUserName().equals(account.getUserName())
						&& userName.getPassWord().equals(account.getPassWord())) {
					System.out.println("UserName: " + userName.getUserName() + " - Pass: " + userName.getPassWord());
					return true;
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		return false;
	}

	public boolean checkUserAdmin(Account account) {
		Session session = factory.getCurrentSession();

		try {
			session.getTransaction().begin();

			String sql = "FROM " + Account.class.getName() + " a where a.role = 'admin'";

			Query<Account> query = session.createQuery(sql);

			List<Account> userNames = query.getResultList();
			for (Account userName : userNames) {
				if (userName.getUserName().equals(account.getUserName()) && userName.getPassWord().equals(account.getPassWord())) {
					System.out.println("UserName: " + userName.getUserName() + " - Pass: " + userName.getPassWord() + " - Role: " + userName.getRole());
					return true;
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		System.out.println("Check User Admin Fail !!");
		return false;
	}

	public void insertAccount(Account account) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(account);
			t.commit();
			System.out.println("Success !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
	}

	public void editAccount(Account account) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(account);
			t.commit();
			System.out.println("Success !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
	}

	public void deleteAccount(Account account) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(account);

			t.commit();
			System.out.println("Success !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
	}

	public List<Account> findAccount(int id) {
		Session session = factory.getCurrentSession();
		List<Account> accounts = null;

		try {
			session.getTransaction().begin();

			String sql = "from " + Account.class.getName() + " a where a.id = " + id + "";

			Query<Account> query = session.createQuery(sql);

			accounts = query.getResultList();
			for (Account account : accounts) {
				System.out.println("Find UserName: " + account.getUserName() + " - PassWord: " + account.getPassWord());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return accounts;
	}

}
