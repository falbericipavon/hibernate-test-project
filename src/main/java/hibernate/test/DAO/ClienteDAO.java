package hibernate.test.DAO;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import hibernate.test.HibernateUtil;
import hibernate.test.dto.ClienteEntity;

public class ClienteDAO {

	public void insertOrUpdateCliente(ClienteEntity emp) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(emp);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public List<ClienteEntity> getAllCliente() {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		List<ClienteEntity> cliente = new ArrayList<ClienteEntity>();
		try {
			cliente = sesn.createQuery("From ClienteEntity").list();
		
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return cliente;
	}
	
	public ClienteEntity getCliente(int clienteId) {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		ClienteEntity cli = null;
		List<ClienteEntity> cli1 = new ArrayList<ClienteEntity>();
		try {
			cli1 = sesn.createQuery("From ClienteEntity Where ID =" + clienteId).list();
			if (!cli1.isEmpty()) {
				cli = cli1.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return cli;
	}

	
	public void deleteCliente(ClienteEntity cli) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(cli);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}
}