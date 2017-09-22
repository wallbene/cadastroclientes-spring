package br.com.bvrio.cadastrocliente.conf;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.bvrio.cadastrocliente.controllers.HomeController;
import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.services.UsuarioService;
import br.com.bvrio.cadastrocliente.validation.ClienteValidation;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ClienteDAO.class, ClienteValidation.class, UsuarioService.class, StringToEnumConverterFactory.class})
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	

	@Autowired
	private StringToEnumConverterFactory stringToEnumConverterFactory;
 

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public MessageSource messageSource(){
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("/WEB-INF/messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    messageSource.setCacheSeconds(1);
	    return messageSource;
	}
	@Bean
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		return conversionService;
		
	}
	
	/* O Interceptor OpenEntityManagerInViewFilter abrirá uma conexão com o banco a cada requisição, o que pode causá problemas de
	 * escalabilidade a medida que a aplicação é usada por mais usuários, dessa forma é indicado que se trate esse problema 
	 * com pool de conexões.
	 * Essa abordagem permite o Lazy loading, porém deve ser atentar ao problema de queries N + 1 
	 * que poderá surgir em relacionamento com comportamento lazy, nesse caso uma alternativa é o uso e query planejada com JPQL.
	 * 
	 * Obs: essa configuração não seria necessária nessa aplicação, servindo apenas para fins didáticos
	 */
	@Bean
	public OpenEntityManagerInViewInterceptor getOpenEntityManagerInViewInterceptor(){
		return new OpenEntityManagerInViewInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(getOpenEntityManagerInViewInterceptor());
	}
	
	
	/*Essas configurações devem ser revistas dependendo do contexto da aplicação
	para um melhor desenpenho dos recursos computacionais.*/ 
	@Bean
	public CacheManager cacheManager(){
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
															.maximumSize(100)
															.expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);
		return manager;
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(stringToEnumConverterFactory);
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}