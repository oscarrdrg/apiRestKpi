package aar;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerListener implements ServletContextListener {

	private static EntityManagerFactory entityManager;
	ServiceDao serviceDao = new ServiceDao();

	Logger log = Logger.getLogger(EntityManagerListener.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		entityManager = Persistence.createEntityManagerFactory("InMemH2DB");

		DatabaseService d = new DatabaseService();
		Kpi kpi = new Kpi("Product Sales");
		Kpi kpi2 = new Kpi("Product Expenses");
		Kpi kpi3 = new Kpi("Return on Assets");
		Kpi kpi4 = new Kpi("Product Expenses Budget");
		Kpi kpi5 = new Kpi("Product Sales Target");
		Kpi kpi6 = new Kpi("Return on Equity");
		Kpi kpi7 = new Kpi("Return on Investment");

		Intercambio intercambio1 = new Intercambio(kpi, kpi2);
		intercambio1.setValorMax(0.0);

		d.insert(kpi);
		d.insert(kpi2);
		d.insert(kpi3);
		d.insert(kpi4);
		d.insert(kpi5);
		d.insert(kpi6);
		d.insert(kpi7);

		d.insertIntercambio(intercambio1);

		log.log(Level.INFO, "Initialized database correctly!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {

		entityManager.close();
		log.log(Level.INFO, "Destroying context.... tomcat stopping!");
	}

	public static EntityManager createEntityManager() {
		if (entityManager == null) {
			throw new IllegalStateException("Context is not initialized yet.");
		}

		return entityManager.createEntityManager();
	}
}
