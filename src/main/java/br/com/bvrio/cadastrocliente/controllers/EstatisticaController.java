package br.com.bvrio.cadastrocliente.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.sql.SQLException;

import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/estatisticas")
@Controller
public class EstatisticaController {
	
	@Autowired
	private Statistics statistics;
	
	@RequestMapping(method=GET)
	public String listar(Model model) throws SQLException, InterruptedException{
		model.addAttribute("statistics", statistics);
		return "estatisticas/index";
	}
	
	@RequestMapping("/limpar")
	public String invalidar() {
		statistics.clear();
		
		return "redirect:/estatisticas"; 
	}
		
}