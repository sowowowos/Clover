package loverduck.clover.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory qFactory() {
        return new JPAQueryFactory(entityManager);
    }
    
   //Thymeleaf 다중 경로 설정 
	/*
	 * @Bean public ClassLoaderTemplateResolver secondaryTemplateResolver() {
	 * ClassLoaderTemplateResolver secondaryTemplateResolver = new
	 * ClassLoaderTemplateResolver();
	 * secondaryTemplateResolver.setPrefix("templates/mypage/");
	 * secondaryTemplateResolver.setSuffix(".html");
	 * secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
	 * secondaryTemplateResolver.setCharacterEncoding("UTF-8");
	 * secondaryTemplateResolver.setOrder(1);
	 * secondaryTemplateResolver.setCheckExistence(true);
	 * 
	 * return secondaryTemplateResolver; }
	 */
}
