package br.com.bvrio.cadastrocliente.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.bvrio.cadastrocliente.models.Usuario;

public interface UsuarioService extends UserDetailsService {
				
			void adiciona(Usuario usuario);			
			void remove(Usuario t);
			void atualiza(Usuario t);
			List<Usuario> listaTodos();
			Usuario buscaPorId(String id);
			int contaTodos();
			Usuario buscaPorEmail(String email);

}
