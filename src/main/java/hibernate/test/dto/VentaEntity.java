package hibernate.test.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "VENTA", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID_venta")})
		@org.hibernate.annotations.Entity(dynamicUpdate = true)
		
public class VentaEntity implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_venta", unique = true, nullable = false) 
	private Integer ventaId;
	
	@Column(name = "FECHA", unique = false, nullable = false)
	private Date fechaVenta;
	
	@Column(name = "IMPORTE", unique = false, nullable = false)
	private float importe;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERSONA", unique = false, nullable = false)
	private ClienteEntity clienteEntity;

	public Integer getVentaId() {
		return ventaId;
	}

	public void setVentaId(Integer ventaId) {
		this.ventaId = ventaId;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaV) {
		this.fechaVenta = fechaV;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public ClienteEntity getClienteEntity() {
		return clienteEntity;
	}

	public void setEmpleadoEntity(ClienteEntity clienteEntity) {
		this.clienteEntity = clienteEntity;
	}


}