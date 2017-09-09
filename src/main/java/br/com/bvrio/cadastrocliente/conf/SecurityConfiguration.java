package br.com.bvrio.cadastrocliente.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.bvrio.cadastrocliente.services.UsuarioService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/clientes/form").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/clientes").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
			.antMatchers("/clientes/**").permitAll()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/usuarios/**").permitAll()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService)
		    .passwordEncoder(new BCryptPasswordEncoder());
	}
}
