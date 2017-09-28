package br.com.bvrio.cadastrocliente.conf;

import java.util.Properties;
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
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.bvrio.cadastrocliente.controllers.HomeController;
import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.services.UsuarioService;
import br.com.bvrio.cadastrocliente.validations.ClienteValidation;

/**
 * @author wallace Benevides
 * 
 * 
 */

//ativa o módulo de MVC para o projeto
//diz para o Spring quais são os pacotes que ele deve escanear a partir das
//classes informadas.
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ClienteDAO.class, ClienteValidation.class, UsuarioService.class, StringToEnumConverterFactory.class})
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	

	@Autowired
	private StringToEnumConverterFactory stringToEnumConverterFactory;
 
	/**
     * Diz para o Spring qual é o prefixo e sulfixo da localização dos arquivos
     * de JSPs dentro da pasta WEB-INF
     * 
     * @Bean (classe gerenciada) do Spring
     * @return {@link InternalResourceViewResolver} 
     */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	 /**
     * Método responsável por configurar a externalização do arquivo padrão de
     * mensagens da aplicação, configurando seu Encoding e também o tempo para
     * recarrega-lo, importante para que não seja necessário o restart da
     * aplicação quando o arquivo de mensagens for modificado.
     * 
     * @return {@link ReloadableResourceBundleMessageSource}
     */
	@Bean
	public MessageSource messageSource(){
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("/WEB-INF/messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    messageSource.setCacheSeconds(1);
	    return messageSource;
	}
	
	
	/**
	 * Método responsável por manipular os padrões de datas no sistema,
     * incluindo seus registradores.
     * 
	 * 
	 * @return {@link DefaultFormattingConversionService} 
	 */
	@Bean
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		return conversionService;
		
	}
	
	 
	
	/**
	 * *O Interceptor OpenEntityManagerInViewFilter abrirá uma conexão com o banco a cada requisição, o que pode causá problemas de
	 * escalabilidade a medida que a aplicação é usada por mais usuários, dessa forma é indicado que se trate esse problema 
	 * com pool de conexões.
	 * Essa abordagem permite o Lazy loading, porém deve ser atentar ao problema de queries N + 1 
	 * que poderá surgir em relacionamento com comportamento lazy, nesse caso uma alternativa é o uso e query planejada com JPQL.
	 * 
	 * Obs: essa configuração não seria necessária nessa aplicação, servindo apenas para fins didáticos
	 * @Bean
	 * @return {@link OpenEntityManagerInViewInterceptor}
	 */
	@Bean
	public OpenEntityManagerInViewInterceptor getOpenEntityManagerInViewInterceptor(){
		return new OpenEntityManagerInViewInterceptor();
	}
	
	/**
	 * Método responsável por implementar um resolvedor de Locale.
	 * A implementação funciona como um interceptador de requisição no qual verifica o locale passado como parametro da requisição
	 *  e adiciona no cookie do usuário.
	 * 
	 * @return {@link CookieLocaleResolver}
	 */
	@Bean
	public LocaleResolver localeResolver(){
		return new CookieLocaleResolver();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());	
		registry.addWebRequestInterceptor(getOpenEntityManagerInViewInterceptor());
	}
	
	/**
	 * Implementação da Interface {@link MailSender}
	 * 
	 * @return link {@link JavaMailSenderImpl} 
	 */
	@Bean
	public MailSender mailSender(){
		System.out.println("criando o MailSender");
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("wallbene@gmail.com");
		mailSender.setPassword("WALLACE1990");
		mailSender.setPort(587);
		System.out.println("colocando os properties");
		
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		
		mailSender.setJavaMailProperties(mailProperties);
		System.out.println("Retornando");
		return mailSender;
	}
	
	
	/**
	 * Método responsável por configurar o Guava, gerenciador de caches da
     * aplicação desenvolvido pelo Google.
     * 
     * Essas configurações devem ser revistas dependendo do contexto da aplicação
     * para um melhor desenpenho dos recursos computacionais.
	 *  
	 * @return link {@link GuavaCacheManager}
	 */
	@Bean
	public CacheManager cacheManager(){
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
															.maximumSize(100)
															.expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);
		return manager;
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
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