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

import com.pss.poc.orm.bean.FileUpload;

/**
 * A data access object (DAO) providing persistence and search support for
 * FileUpload entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.pss.poc.orm.bean.FileUpload
 * @author MyEclipse Persistence Tools
 */
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FileUploadDAO {
	private static final Logger log = Logger.getLogger(FileUploadDAO.class);
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

	public void save(FileUpload transientInstance) {
		log.debug("saving FileUpload instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FileUpload persistentInstance) {
		log.debug("deleting FileUpload instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FileUpload findById(java.lang.String id) {
		log.debug("getting FileUpload instance with id: " + id);
		try {
			FileUpload instance = (FileUpload) getCurrentSession().get("com.pss.poc.orm.bean.FileUpload", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FileUpload> findByExample(FileUpload instance) {
		log.debug("finding FileUpload instance by example");
		try {
			List<FileUpload> results = (List<FileUpload>) getCurrentSession().createCriteria("com.pss.poc.orm.bean.FileUpload").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FileUpload instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from FileUpload as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FileUpload> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<FileUpload> findByFileType(Object fileType) {
		return findByProperty(FILE_TYPE, fileType);
	}

	public List<FileUpload> findByFileSize(Object fileSize) {
		return findByProperty(FILE_SIZE, fileSize);
	}

	public List<FileUpload> findByFileBlob(Object fileBlob) {
		return findByProperty(FILE_BLOB, fileBlob);
	}

	public List findAll() {
		log.debug("finding all FileUpload instances");
		try {
			String queryString = "from FileUpload";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FileUpload merge(FileUpload detachedInstance) {
		log.debug("merging FileUpload instance");
		try {
			FileUpload result = (FileUpload) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FileUpload instance) {
		log.debug("attaching dirty FileUpload instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FileUpload instance) {
		log.debug("attaching clean FileUpload instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FileUploadDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FileUploadDAO) ctx.getBean("FileUploadDAO");
	}
}