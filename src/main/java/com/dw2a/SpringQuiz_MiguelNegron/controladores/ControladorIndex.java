package com.dw2a.SpringQuiz_MiguelNegron.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dw2a.SpringQuiz_MiguelNegron.entidades.Puntuacion;
import com.dw2a.SpringQuiz_MiguelNegron.servicios.PuntuacionServiceDB;

@Controller
public class ControladorIndex {
	@Autowired
	PuntuacionServiceDB puntuacionService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) session.getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
		}
		
		List<Puntuacion> puntuaciones = puntuacionService.findAll();

		model.addAttribute("sessionRespuestas", respuestas);
		model.addAttribute("puntuaciones", puntuaciones);
		return "index";
	}

	@GetMapping("/juego")
	public String jugarBasico(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) session.getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
		}

		model.addAttribute("sessionRespuestas", respuestas);
		return "juego";
	}
	
	@PostMapping("/empezar")
	public String empezarJuego(@RequestParam("nombre") String nombre, Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) session.getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
		}
				
		respuestas.add(nombre);
		session.setAttribute("RESPUESTAS", respuestas);
		
		model.addAttribute("sessionRespuestas", respuestas);
		return "redirect:/juego";
	}

	@PostMapping("/siguientePregunta")
	public String siguientePregunta(@RequestParam("respuesta") String respuesta, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) request.getSession().getAttribute("RESPUESTAS");
		
		if (respuestas == null) {
			respuestas = new ArrayList<>();
			request.getSession().setAttribute("RESPUESTAS", respuestas);
		}

		respuestas.add(respuesta);
		request.getSession().setAttribute("RESPUESTAS", respuestas);
		
		if(respuestas.size() >= 8) {
			Puntuacion p = new Puntuacion(respuestas.get(0), "lalaal");
			puntuacionService.add(p);
			return "resultados";
		} else {
			return "redirect:/juego";
		}
	}

	@RequestMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
