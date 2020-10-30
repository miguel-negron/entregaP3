package com.dw2a.SpringQuiz_MiguelNegron.servicios;

import java.util.List;

import com.dw2a.SpringQuiz_MiguelNegron.entidades.Puntuacion;

public interface PuntuacionService {
	public Puntuacion add(Puntuacion e);

	public List<Puntuacion> findAll();

	public Puntuacion findById(Long id);

	public Puntuacion edit(Puntuacion e);
}
