package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	@Id
	@Column(name="idprod")
	private String codigo;
	
	@Column(name="descripcion")
	private String descrip;
	
	@Column(name="stock")
	private int stock;
	
	@Column(name="precio")
	private double precio;
	
	@Column(name="idcategoria")
	private int idcat;
	
	@Column(name="estado")
	private int estado;
		
}
