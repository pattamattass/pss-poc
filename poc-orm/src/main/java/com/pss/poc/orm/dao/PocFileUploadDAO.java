package com.pss.poc.orm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.pss.poc.orm.bean.PocFileUpload;

/**
 * A data access object (DAO) providing persistence and search support for
 * InnomsFileUpload entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.pss.poc.orm.bean.PocFileUpload
 * @author MyEclipse Persistence Tools
 */
@Transactional
@SuppressWarnings({ "rawtypes" })
public class PocFileUploadDAO {
	private static final Logger log = Logger.getLogger(PocFileUploadDAO.class);
	// property constants
	public static final String ALLOCATION_DESC = "allocationDesc";
	public static final String ALLOCATION_POINT_VAL = "allocationPointVal";

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

	public void save(PocFileUpload innomsFileUpload) {
		log.debug("saving InnomsFileUpload instance");
		try {
			getCurrentSession().save(innomsFileUpload);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public PocFileUpload findById(java.lang.String id) {
		log.debug("getting InnomsFileUpload instance with id: " + id);
		try {
			PocFileUpload instance = (PocFileUpload) getCurrentSession().get(
					"com.pss.poc.orm.bean.PocFileUpload", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all InnomsFileUpload instances");
		try {
			String queryString = "from PocFileUpload";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public static PocFileUploadDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PocFileUploadDAO) ctx.getBean("PocFileUploadDAO");
	}
}