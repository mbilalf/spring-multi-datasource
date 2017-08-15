package bilal.multids.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({ "classpath:persistence-reporting-db.properties" })
@EnableJpaRepositories(
        basePackages = "bilal.multids.core.repositories",
        entityManagerFactoryRef = "coreEntityManager",
        transactionManagerRef = "coreTransactionManager"
)
public class CoreConfig {
    @Autowired
    private Environment env;

    @Bean("coreEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean coreEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(coreDataSource());
        em.setPackagesToScan(new String[] { "bilal.multids.core.model" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(true);

        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        //properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        //properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean("coreDataSource")
    public DataSource coreDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("core.driverClassName"));
        dataSource.setUrl(env.getProperty("core.jdbc.url"));
        dataSource.setUsername(env.getProperty("core.jdbc.user"));
        dataSource.setPassword(env.getProperty("core.jdbc.pass"));

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager coreTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(coreEntityManager().getObject());

        return transactionManager;
    }

    @Bean(name = "jdbcCore")
    public JdbcTemplate jdbcTemplate(DataSource coreDataSource) {
        System.out.println("\n\n\n.. returning jdbcCore");
        return new JdbcTemplate(coreDataSource());
    }
}