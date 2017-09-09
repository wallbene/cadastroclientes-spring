package br.com.bvrio.cadastrocliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bvrio.cadastrocliente.daos.UsuarioDAO;
import br.com.bvrio.cadastrocliente.models.Usuario;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioDAO dao;
	

		//método usado pelo Spring Security
		@Override
		public Usuario loadUserByUsername(String email){
			 	Usuario usuario = dao.buscaPorEmail(email);	
			if(usuario == null) throw new UsernameNotFoundException("Usuario " + email + "não foi encontrado");
			
			return usuario;
		}

}
