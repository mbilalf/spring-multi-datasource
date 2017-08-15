package bilal.multids.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@PropertySource({ "classpath:persistence-reporting-db.properties" })
@EnableJpaRepositories(
        basePackages = "bilal.multids.events.repositories",
        entityManagerFactoryRef = "eventsEntityManager",
        transactionManagerRef = "eventsTransactionManager"
)
public class EventsConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean eventsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(eventsDataSource());
        em.setPackagesToScan( new String[] { "bilal.multids.events.model" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabase(Database.POSTGRESQL);

        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        //properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        //properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean("eventsDataSource")
    public DataSource eventsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("events.driverClassName"));
        dataSource.setUrl(env.getProperty("events.jdbc.url"));
        dataSource.setUsername(env.getProperty("events.jdbc.user"));
        dataSource.setPassword(env.getProperty("events.jdbc.pass"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager eventsTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(eventsEntityManager().getObject());
        return transactionManager;
    }

    @Bean(name = "jdbcEvents")
    public JdbcTemplate jdbcTemplate(DataSource eventsDataSource) {
        return new JdbcTemplate(eventsDataSource());
    }
}

