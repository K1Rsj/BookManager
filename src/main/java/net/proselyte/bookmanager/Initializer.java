package net.proselyte.bookmanager;

import com.mysql.jdbc.Driver;
import net.proselyte.bookmanager.model.Book;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan("net.proselyte.bookmanager")
public class Initializer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/pages/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;
    }

    @Bean
    public static DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getCanonicalName());
        dataSource.setUrl("jdbc:mysql://localhost:3306/bookmanager");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Book.class)
                .addProperties(jpaProps());

        return config.buildSessionFactory();
    }

    private Properties jpaProps() {
        Properties jpaProps = new Properties();
        jpaProps.put(Environment.DIALECT, MySQL5Dialect.class.getCanonicalName());
        jpaProps.put(Environment.DRIVER, Driver.class.getCanonicalName());
        jpaProps.put(Environment.URL, "jdbc:mysql://localhost:3306/bookmanager");
        jpaProps.put(Environment.USER, "root");
        jpaProps.put(Environment.PASS, "root");
        jpaProps.put(Environment.HBM2DDL_AUTO, "validate");
        jpaProps.put(Environment.SHOW_SQL, true);
        jpaProps.put(Environment.FORMAT_SQL, true);
        jpaProps.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");

        return jpaProps;
    }

    @Bean
    public HibernateTransactionManager platformTransactionManager() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setDataSource(getDataSource());
        hibernateTransactionManager.setSessionFactory(getSessionFactory());

        return hibernateTransactionManager;
    }
}
