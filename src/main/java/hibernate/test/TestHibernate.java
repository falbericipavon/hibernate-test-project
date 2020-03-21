package hibernate.test;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import hibernate.test.dto.ClienteEntity;
import hibernate.test.dto.VentaEntity;
import hibernate.test.DAO.ClienteDAO;
import hibernate.test.DAO.VentaDAO;
import hibernate.test.DateUtil;

public class TestHibernate {

	static Scanner sc = new Scanner(System.in);
	static ClienteEntity cli = new ClienteEntity();
	static ClienteDAO cliDAO = new ClienteDAO();
	static VentaEntity ven = new VentaEntity();
	static VentaDAO venDAO = new VentaDAO();

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");
		System.out.println("");

		int opcion = mostrarMenu(sc);
		while (opcion != 0) {

			switch (opcion) {
			case 1:
				alta();
				break;
			case 2:
				modificacion();
				break;
			case 3:
				baja();
				break;
			case 4:
				listado();
				break;
			case 5:
				ventas();
				break;
			case 6:
				modificarventa();
				break;
			case 7:
				listadoVenta();
				break;
			default:
				System.out.println("ERROR");
				System.out.println("=====");
				System.out.println("Ingrese numero valido");
				System.out.println("");
				break;
			}
			opcion = mostrarMenu(sc);
		}

		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	private static int mostrarMenu(Scanner sc) {
		System.out.println("");
		System.out.println("MENU OPCIONES: ");
		System.out.println("");
		System.out.println("1: ALTA DE CLIENTE");
		System.out.println("2: MODIFICACION DE CLIENTE");
		System.out.println("3: BAJA DE CLIENTE");
		System.out.println("4: LISTADO DE CLIENTE");
		System.out.println("5: NUEVA VENTA");
		System.out.println("6: MODIFICAR VENTA");
		System.out.println("7: LISTA DE VENTA");
		System.out.println("0: SALIR");
		int opcion = 0;
		opcion = sc.nextInt();
		return opcion;
	}

	private static int calcularEdad(Date fechaNac) {
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(fechaNac);
		int anioActual = hoy.get(Calendar.YEAR);
		int anioNacim = gc.get(Calendar.YEAR);
		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacim = gc.get(Calendar.MONTH);
		int diaActual = hoy.get(Calendar.DATE);
		int diaNacim = gc.get(Calendar.DATE);
		int dif = anioActual - anioNacim;
		if (mesActual < mesNacim) {
			dif = dif - 1;
		} else {
			if (mesActual == mesNacim && diaActual < diaNacim) {
				dif = dif - 1;
			}
		}
		return dif;
	}

	static void errorGeneral(String fname) {
		System.out.println("ERROR");
		System.out.println("=====");
		System.out.println("La " + fname + " elegida es incorrecta.");
		System.out.println("");
	}
	
	static void errorID() {
		System.out.println("ERROR");
		System.out.println("===============");
		System.out.println("El ID no existe elija una nuevo ID");
		System.out.println("");
	}

	static void mostrarEdad() {
		System.out.println("Ingrese fecha nacimiento (yyyy-MM-dd):");
		String fNac = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fechaNac = sdf.parse(fNac);
			int edad = calcularEdad(fechaNac);
			cli.setEdad(edad);
			cli.setfNac(fechaNac);
		} catch (ParseException e1) {
			errorGeneral("FECHA");
		}
	}
	

	private static void alta() {
		System.out.println("ALTA DE CLIENTE");
		System.out.println("===============");
		System.out.println("");
		System.out.println("Ingrese nombre:");
		String nombre = sc.next();
		cli.setName(nombre);
		mostrarEdad();
		cliDAO.insertOrUpdateCliente(cli);
		System.out.println("El usuario se ha dado ALTA correctamente");
	}

	private static void modificacion() {
		System.out.println("MODIFICACION DEL CLIENTE");
		System.out.println("===============");
		System.out.println("");
		System.out.println("INGRESA EL ID DEL CLIENTE QUE QUIERE MODIFICAR: ");
		int clienteId = sc.nextInt();
		cli = cliDAO.getCliente(clienteId);
		if (cli == null) {
			errorID();
			modificacion();
		} else {
			System.out.println("MENU OPCIONES: ");
			System.out.println("");
			System.out.println("1: MODIFICAR NOMBRE ");
			System.out.println("2: MODIFICAR FECHA NACIMIENTO ");
			int opcionDos = sc.nextInt();
			switch (opcionDos) {
			case 1:
				System.out.println("1: MODIFICAR NOMBRE ");
				System.out.println("Ingrese el nuevo nombre:");
				String nombreModificado = sc.next();
				cli.setName(nombreModificado);
				cliDAO.insertOrUpdateCliente(cli);
				System.out.println("El usuario se ha MODIFICADO correctamente");
				break;
			case 2:
				System.out.println("2: MODIFICAR FECHA NACIMIENTO ");
				mostrarEdad();
				cliDAO.insertOrUpdateCliente(cli);
				System.out.println("El usuario se ha MODIFICADO correctamente");
				break;
			default:
				errorGeneral("OPCIÓN");
				modificacion();
				break;
			}

		}
	}

	private static void baja() {
		System.out.println("BAJA DE CLIENTE");
		System.out.println("===============");
		System.out.println("");
		System.out.println("INGRESE ID A BORRAR:");
		int clienteId = sc.nextInt();
		cli = cliDAO.getCliente(clienteId);
		if (cli == null) {
			errorID();
			baja();
		} else {
			cliDAO.deleteCliente(cli);
			System.out.println("El usuario se ha dado BAJA correctamente");
		}
	}

	private static void listado() {
		List<ClienteEntity> listCli = cliDAO.getAllCliente();
		System.out.println("ID| Nombre| Edad | Fecha Nacimiento");
		for (ClienteEntity cli : listCli) {
			System.out.println(cli.getClienteId() + " " + cli.getName() + " " + cli.getEdad() + " " + cli.getfNac());
		}
	}

	

	private static void listadoVenta() {
		List<VentaEntity> listVen = venDAO.getAllventa();
		System.out.println("ID_Venta | Fecha venta | Importe | ID | Nombre Cliente");
		for (VentaEntity ven : listVen) {
			cli = ven.getClienteEntity();
			int id = cli.getClienteId();
			String nombre = cli.getName();
			System.out.println(ven.getVentaId() + " " + ven.getFechaVenta() + " " + ven.getImporte() + " " + id + " "
					+ nombre + ".");
		}
	}

	private static void ventas() {
		System.out.println("VENTAS");
		System.out.println("======");
		System.out.println("");
		System.out.println("Ingrese ID");
		int clienteId = sc.nextInt();
		cli = cliDAO.getCliente(clienteId);
		if (cli == null) {
			errorID();
			ventas();
		} else {
			try {
				System.out.println("La persona seleccionada es:" + cli.getName());
				ven.setEmpleadoEntity(cli);
				System.out.println("Ingrese Monto de la venta");
				float importe = sc.nextFloat();
				ven.setImporte(importe);
				Date fecha = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String fechaHoy = ft.format(fecha);
				Date fechaV = ft.parse(fechaHoy);
				ven.setFechaVenta(fechaV);
				venDAO.insertVenta(ven);
				System.out.println("La VENTA se ha realizado correctamente");
			} catch (ParseException e) {
				errorGeneral("FECHA");
			}
		}
	}

	private static void modificarventa() {
		System.out.println("MODIFICAR VENTAS");
		System.out.println("================");
		System.out.println("");
		System.out.println("INGRESA EL ID DE VENTA QUE QUIERE MODIFICAR: ");
		int ventaId = sc.nextInt();
		ven = venDAO.getVenta(ventaId);
		if (ven == null) {
			errorID();
			modificarventa();
		} else {
			System.out.println("MENU OPCIONES: ");
			System.out.println("");
			System.out.println("1: MODIFICAR ID PERSONA ");
			System.out.println("2: MODIFICAR IMPORTE ");
			int opcionDos = sc.nextInt();
			switch (opcionDos) {
			case 1:
				System.out.println("1: MODIFICAR ID PERSONA ");
				System.out.println("Ingrese el nuevo ID de persona:");
				int clienteIdModificado = sc.nextInt();
				cli = cliDAO.getCliente(clienteIdModificado);
				System.out.println("La persona seleccionada es:" + cli.getName());
				ven.setEmpleadoEntity(cli);
				venDAO.insertVenta(ven);
				System.out.println("La VENTA se ha MODIFICADO correctamente");
				break;
			case 2:
				System.out.println("2: MODIFICAR IMPORTE ");
				System.out.println("Ingrese el nuevo importe:");
				int importeModificado = sc.nextInt();
				ven.setImporte(importeModificado);
				venDAO.insertVenta(ven);
				System.out.println("La VENTA se ha MODIFICADO correctamente");
				break;
			default:
				errorGeneral("OPCIÓN");
				modificarventa();
				break;
			}
		}

	}


}
