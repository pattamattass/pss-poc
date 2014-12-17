package com.pss.poc.orm.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.pss.poc.orm.bean.ClientDetails;

@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClientDetailsDAO {
	private static final Logger log = Logger.getLogger(ClientDetailsDAO.class);
	// property constants
	public static final String FILE_NAME = "fileName";
	public static final String FILE_TYPE = "fileType";
	public static final String FILE_SIZE = "fileSize";
	public static final String FILE_BLOB = "fileBlob";

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	public void save(ClientDetails transientInstance) {
		log.debug("saving ClientDetails instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientDetails persistentInstance) {
		log.debug("deleting ClientDetails instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientDetails findById(java.lang.String id) {
		log.debug("getting ClientDetails instance with id: " + id);
		try {
			ClientDetails instance = (ClientDetails) getCurrentSession().get("com.pss.poc.orm.bean.ClientDetails", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ClientDetails> findByExample(ClientDetails instance) {
		log.debug("finding ClientDetails instance by example");
		try {
			List<ClientDetails> results = (List<ClientDetails>) getCurrentSession().createCriteria("com.pss.poc.orm.bean.ClientDetails").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ClientDetails instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from ClientDetails as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ClientDetails> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<ClientDetails> findByFileType(Object fileType) {
		return findByProperty(FILE_TYPE, fileType);
	}

	public List<ClientDetails> findByFileSize(Object fileSize) {
		return findByProperty(FILE_SIZE, fileSize);
	}

	public List<ClientDetails> findByFileBlob(Object fileBlob) {
		return findByProperty(FILE_BLOB, fileBlob);
	}

	public List findAll() {
		log.debug("finding all ClientDetails instances");
		try {
			String queryString = "from ClientDetails";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientDetails merge(ClientDetails detachedInstance) {
		log.debug("merging ClientDetails instance");
		try {
			ClientDetails result = (ClientDetails) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientDetails instance) {
		log.debug("attaching dirty ClientDetails instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientDetails instance) {
		log.debug("attaching clean ClientDetails instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ClientDetailsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ClientDetailsDAO) ctx.getBean("ClientDetailsDAO");
	}
}