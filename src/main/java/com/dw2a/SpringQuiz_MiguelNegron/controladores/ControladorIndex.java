package com.dw2a.SpringQuiz_MiguelNegron.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorIndex {

	@RequestMapping("/session")
	public String index() {
		return "index";
	}

	@GetMapping("")
	public String process(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) session.getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
		}

		model.addAttribute("sessionRespuestas", respuestas);
		return "index";
	}

	@PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) request.getSession().getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
			request.getSession().setAttribute("RESPUESTAS", respuestas);
		}

		respuestas.add(msg);
		request.getSession().setAttribute("RESPUESTAS", respuestas);
		return "redirect:/session";
	}

	@PostMapping("/enviarRespuesta")
	public String enviarRespuesta(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> respuestas = (List<String>) request.getSession().getAttribute("RESPUESTAS");
		if (respuestas == null) {
			respuestas = new ArrayList<>();
			request.getSession().setAttribute("RESPUESTAS", respuestas);
		}

		respuestas.add(msg);
		request.getSession().setAttribute("RESPUESTAS", respuestas);
		return "redirect:/session";
	}

	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/session";
	}
}
