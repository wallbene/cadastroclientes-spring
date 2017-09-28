package br.com.bvrio.cadastrocliente.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bvrio.cadastrocliente.daos.RoleDAO;
import br.com.bvrio.cadastrocliente.daos.UsuarioDAO;
import br.com.bvrio.cadastrocliente.models.Role;
import br.com.bvrio.cadastrocliente.models.Usuario;

@Service(value="usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Autowired
	private RoleDAO roleDao;
	
	//método usado pelo Spring Security
		@Override
		public UserDetails loadUserByUsername(String email){
			Usuario usuario = buscaPorEmail(email);	
			if(usuario == null) throw new UsernameNotFoundException("Usuario " + email + "não foi encontrado");
			
			return usuario;
		}				
	
		@Override
		public void adiciona(Usuario usuario) {
			Role role = buscaOuCriaRole("ROLE_ADMIN");
			usuario.addRole(role);
			usuarioDao.adiciona(usuario);
		}
		@Override
		public void remove(Usuario t) {
			usuarioDao.remove(t);
		}
		@Override
		public void atualiza(Usuario t) {
			usuarioDao.atualiza(t);
		}
		@Override
		public List<Usuario> listaTodos() {
			return usuarioDao.listaTodos();
		}
		@Override
		public Usuario buscaPorId(String id) {
			return usuarioDao.buscaPorId(id);
		}
		@Override
		public int contaTodos() {
			return usuarioDao.contaTodos();
		}
		@Override
		public Usuario buscaPorEmail(String email) {			
			return usuarioDao.buscaPorEmail(email);
		}
		
		/**
		 * Método que busca uma role no banco, caso seja o primeiro usuário a ser cadastrado no sistema e não haja uma role
		 * ela será criada e persistida no banco.
		 * @param nome da role a ser buscada ou criada;
		 * @return {@link Role} buscada no banco ou criada;
		 */
		private Role buscaOuCriaRole(String nome) {
			//busca a Role, associa ao usuario e persiste no banco
			Role role = roleDao.buscaPorId(nome);
			if(role == null){
				role = new Role(nome);
				roleDao.adiciona(role);
				role = roleDao.buscaPorId(role.getNome());				
			}
			else{
				System.out.println("Retornou a role corretamente " + role.getNome());
			}
			return role;
		}

}
