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

	String[] tiposDeAmigo = { "Amigo agradable", "Amigo maternal", "Amigo emo", "Amigo gracioso" };

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) session.getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
		}

		List<Puntuacion> puntuaciones = puntuacionService.findAll();
		
		while(puntuaciones.size() > 5) {
			puntuaciones.remove(0);
		};

		model.addAttribute("sessionRespuestas", respuestas);
		model.addAttribute("puntuaciones", puntuaciones);
		
		session.invalidate();
		
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

		return "redirect:/juego";

	}

	@PostMapping("/fin")
	public String finalizarQuiz(@RequestParam(value = "respuesta", required = false) List<String> respuesta, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) request.getSession().getAttribute("RESPUESTAS");

		if (respuestas == null) {
			respuestas = new ArrayList<>();
			request.getSession().setAttribute("RESPUESTAS", respuestas);
		}
		
		for (String s : respuesta) {
			respuestas.add(s);
		}
		
		Puntuacion p = new Puntuacion(respuestas.get(0), tiposDeAmigo[deduceTipo(respuestas)]);
		puntuacionService.add(p);

		request.getSession().invalidate();
		return "fin";

	}

	@RequestMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
	

	private int deduceTipo(List<String> respuestas) {
		int tipo;
		int ceros = 0;
		int unos = 0;
		int doses = 0;
		int treses = 0;
		
		for (String string : respuestas) {
			switch (string) {
			case "0":
				ceros++;
				break;
			case "1":
				unos++;
				break;
			case "2":
				doses++;
				break;
			case "3":
				treses++;
				break;

			default:
				break;
			}
		}

		// En los empates se dara el primer tipo que salga porque no veo manera objetiva
		// de desempatarlos. De hecho no hay ningun tipo de relacion entre lo que
		// hechamos a un tostada y el tipo de amigos que somos.
		if (ceros > unos) {
			tipo = 0;

			if (Integer.compare(doses, treses) > 0) {
				if (ceros > doses) {
					tipo = 0;
				} else {
					tipo = 2;
				}
			} else {
				if (ceros > treses) {
					tipo = 0;
				} else {
					tipo = 3;
				}
			}
		} else {
			tipo = 1;

			if (Integer.compare(doses, treses) > 0) {
				if (unos > doses) {
					tipo = 1;
				} else {
					tipo = 2;
				}
			} else {
				if (unos > treses) {
					tipo = 1;
				} else {
					tipo = 3;
				}
			}
		}
		return tipo;
	}
}
