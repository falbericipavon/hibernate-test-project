package hibernate.test.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "CLIENTE", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID")})
		@org.hibernate.annotations.Entity(dynamicUpdate = true)
				
		public class ClienteEntity implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer clienteId;
	
	@Column(name = "NOMBRE", unique = true, nullable = false, length = 100)
	private String name;
	
	@Column(name = "EDAD", unique = false, nullable = false, length = 100)
	private int edad;
	
	@Column(name = "FECHA_NACIMIENTO", unique = false, nullable = false, length = 100)
	private Date fNac;

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getfNac() {
		return fNac;
	}

	public void setfNac(Date fechaNac) {
		this.fNac = fechaNac;
	}

	public Integer getClienteEntity(int id) {
		return null;
	}

}