package com.dw2a.SpringQuiz_MiguelNegron.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Puntuacion {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String nombre;
	private String puntuacion;
	
	public Puntuacion() {
		super();
	}
	
	public Puntuacion(String nombre, String puntuacion) {
		super();
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}

	public Puntuacion(long id, String nombre, String puntuacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((puntuacion == null) ? 0 : puntuacion.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Puntuacion other = (Puntuacion) obj;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (puntuacion == null) {
			if (other.puntuacion != null)
				return false;
		} else if (!puntuacion.equals(other.puntuacion))
			return false;
		return true;
	}
	
	

}
