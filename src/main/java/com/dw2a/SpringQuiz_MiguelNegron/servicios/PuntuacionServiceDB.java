package com.dw2a.SpringQuiz_MiguelNegron.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dw2a.SpringQuiz_MiguelNegron.entidades.Puntuacion;
import com.dw2a.SpringQuiz_MiguelNegron.repositories.PuntuacionRepository;

public class PuntuacionServiceDB implements PuntuacionService {

	@Autowired
	PuntuacionRepository repositorio;
	
	@Override
	public Puntuacion add(Puntuacion e) {
		return repositorio.save(e);
	}

	@Override
	public List<Puntuacion> findAll() {
		return repositorio.findAll();
	}

	@Override
	public Puntuacion findById(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	@Override
	public Puntuacion edit(Puntuacion e) {
		return repositorio.save(e);
	}

}
