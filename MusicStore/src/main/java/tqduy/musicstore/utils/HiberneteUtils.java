package tqduy.musicstore.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HiberneteUtils {
	private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
			.buildSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
