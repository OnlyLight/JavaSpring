package tqduy.musicstore.dao.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import tqduy.musicstore.dao.ProductDAO;
import tqduy.musicstore.entity.Product;
import tqduy.musicstore.utils.HiberneteUtils;

public class ProductDAOImp implements ProductDAO {
	private static SessionFactory factory = HiberneteUtils.getSessionFactory();

	public List<Product> getListProduct() {
		Session session = factory.getCurrentSession();
		List<Product> products = null;

		try {
			session.getTransaction().begin();

			String sql = "from " + Product.class.getName() + " p ";

			Query<Product> query = session.createQuery(sql);

			products = query.getResultList();
			for (Product product : products) {
				System.out.println("Name: " + product.getName() + " - Price: " + product.getPrice());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return products;
	}

	public List<Product> findProduct(String code) {
		Session session = factory.getCurrentSession();
		List<Product> products = null;

		try {
			session.getTransaction().begin();

			String sql = "from " + Product.class.getName() + " p where p.code = '" + code + "'";

			Query<Product> query = session.createQuery(sql);

			products = query.getResultList();
			for (Product product : products) {
				System.out.println("Name: " + product.getName() + " - Price: " + product.getPrice());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return products;
	}

	public void insertMusic(Product product) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(product);
			t.commit();
			System.out.println("Success !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
	}

	public void editMusic(Product product) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(product);
			t.commit();
			System.out.println("Success !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
	}

	public void deleteMusic(Product product) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(product);

			t.commit();
			System.out.println("Success !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
	}

}
