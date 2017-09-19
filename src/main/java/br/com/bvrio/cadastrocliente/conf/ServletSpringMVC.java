package br.com.bvrio.cadastrocliente.conf;

import javax.servlet.Filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SecurityConfiguration.class,
							 AppWebConfiguration.class,
							 	JPAConfiguration.class,
							 	JPAProductionConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter, new OpenEntityManagerInViewFilter()};
	}
	
	//Esse bloco deverá ser comitido em contexto de produção para que o profile de "prod" seja visualizado;
	//por um motivo ainda não identificado o heroku não está deployando com a propriedade de "dev" mesmo com o arquivo Procfile configurado.
	//Devido a esse motivo foi setado o valor "prod" aqui para que seja utilizado o profile de prod ao realizar o deploy no heroku,
	//mas ATENÇÂO *quando estiver no contexto de desenvolvimento sete a propriedade abaixo como "dev".
	/*@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(new RequestContextListener());
		servletContext.setInitParameter("spring.profiles.active", "dev");
	}*/
	
}