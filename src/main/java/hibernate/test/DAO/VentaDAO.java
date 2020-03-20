package hibernate.test.DAO;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import hibernate.test.HibernateUtil;
import hibernate.test.dto.ClienteEntity;
import hibernate.test.dto.VentaEntity;

public class VentaDAO {

	public void insertVenta(VentaEntity ven) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(ven);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public List<VentaEntity> getAllventa() {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		List<VentaEntity> venta = new ArrayList<VentaEntity>();
		try {
			venta = sesn.createQuery("From VentaEntity").list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return venta;
	}
	

	public VentaEntity getVenta(int ventaId) {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		VentaEntity ven = null;
		List<VentaEntity> ven1 = new ArrayList<VentaEntity>();
		try {
			ven1 = sesn.createQuery("From VentaEntity Where ID_venta =" + ventaId).list();
			if (!ven1.isEmpty()) {
				ven = ven1.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return ven;
	}
}
