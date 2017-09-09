package br.com.bvrio.cadastrocliente.conf;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SpringSecurityFilterConfiguration extends AbstractSecurityWebApplicationInitializer {
	
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		Dynamic filter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		filter.setInitParameter("encoding", "UTF-8");
		filter.setInitParameter("forceEcoding", "true");
		filter.addMappingForUrlPatterns(null, false, "/*");
		
	}
	

}
