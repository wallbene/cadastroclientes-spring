package br.com.bvrio.cadastrocliente.builders;

import java.util.ArrayList;
import java.util.List;

import br.com.bvrio.cadastrocliente.models.Role;
import br.com.bvrio.cadastrocliente.models.Usuario;

public class UsuarioBuilder {

	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	private static Role roleDefault = new Role("ROLE_ADMIN");

	private UsuarioBuilder(Usuario usuario) {
		this.usuarios.add(usuario);
	}
	public UsuarioBuilder() {
	}
	public static UsuarioBuilder newUsuario(String nome, String email, String senha, Role role) {
		Usuario usuario = create(nome, email, senha, role);
		return new UsuarioBuilder(usuario);
	}
	
	public static UsuarioBuilder newUsuario() {
		Usuario usuario = create("usuario1", "teste@gmail.com", "12345", null);
		return new UsuarioBuilder(usuario);
	}

	private static Usuario create(String nome, String email, String senha, Role role) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);;
		usuario.setEmail(email);
		usuario.setSenha(senha);
		if(role==null) 
			usuario.addRole(roleDefault);
		else 
			usuario.addRole(role);
		
		return usuario;
	}
	
	public UsuarioBuilder more(int n){
		Usuario base = this.usuarios.get(0);
		for(int i = 0; i <= n;i++ ){
			this.usuarios.add(create("usuario"+i, base.getEmail()+i, base.getSenha(), roleDefault));
		}
		return this;
	}
	
	public Usuario buildOne(){
		return this.usuarios.get(0);
	}
	
	public List<Usuario> buildAll(){
		return this.usuarios;
	}

}
