package aar;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.logging.Logger;

/**
 * This class performs the insert, read, delete and search operations
 *
 *
 */
public class DatabaseService {

	Logger log = Logger.getLogger(DatabaseService.class.getName());

	public int insert(Kpi kpi) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(kpi);
			entityManager.flush();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return kpi.getId();
	}

	public int insertIntercambio(Intercambio intercambio) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(intercambio);
			entityManager.flush();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return intercambio.getId();
	}

	public Kpi read(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		Kpi product = null;
		try {
			product = entityManager.find(Kpi.class, id);
		} finally {
			entityManager.close();

			if (product == null)
				log.warning("No records records were found with given id value");

		}
		return product;
	}

	public Intercambio readIntercambio(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		Intercambio product = null;
		try {
			product = entityManager.find(Intercambio.class, id);
		} finally {
			entityManager.close();

			if (product == null)
				log.warning("No records records were found with given id value");

		}
		return product;
	}

	public boolean delete(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		boolean result = false;
		try {
			entityManager.getTransaction().begin();
			Kpi entity = null;

			entity = entityManager.find(Kpi.class, id);
			if (entity != null) {
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
				result = true;
			} else {
				log.warning("No records records were found with given id value !!");
				result = false;
			}

		} catch (Exception e) {
			result = false;
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return result;
	}

	public boolean addValorMax(Intercambio intercambio) {

		EntityManager entityManager = EntityManagerListener.createEntityManager();
		boolean result = false;
		entityManager.getTransaction().begin();
		
		entityManager.merge(intercambio);
		entityManager.getTransaction().commit();
		entityManager.close();
		

		return result;

	}

	public boolean deleteIntercambio(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		boolean result = false;
		try {
			entityManager.getTransaction().begin();
			Intercambio entity = null;

			entity = entityManager.find(Intercambio.class, id);
			if (entity != null) {
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
				result = true;
			} else {
				log.warning("No records records were found with given id value !!");
				result = false;
			}

		} catch (Exception e) {
			result = false;
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return result;
	}

	public List<Kpi> findAll() {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Kpi> criteriaQuery = criteriaBuilder.createQuery(Kpi.class);

			Root<Kpi> rootEntry = criteriaQuery.from(Kpi.class);
			CriteriaQuery<Kpi> all = criteriaQuery.select(rootEntry);
			TypedQuery<Kpi> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();
		} finally {
			entityManager.close();

		}
	}

	public List<Intercambio> findAllIntercambios() {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Intercambio> criteriaQuery = criteriaBuilder.createQuery(Intercambio.class);

			Root<Intercambio> rootEntry = criteriaQuery.from(Intercambio.class);
			CriteriaQuery<Intercambio> all = criteriaQuery.select(rootEntry);
			TypedQuery<Intercambio> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();
		} finally {
			entityManager.close();

		}
	}

}
