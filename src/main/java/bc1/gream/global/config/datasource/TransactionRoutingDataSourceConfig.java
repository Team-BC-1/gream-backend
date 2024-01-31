package bc1.gream.global.config.datasource;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionRoutingDataSourceConfig {

//    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
//    @Bean
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @ConfigurationProperties(prefix = "spring.datasource.hikari.secondary")
//    @Bean
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @DependsOn({"primaryDataSource", "secondaryDataSource"})
//    @Bean
//    public DataSource routingDataSource(
//        @Qualifier("primaryDataSource") DataSource primary,
//        @Qualifier("secondaryDataSource") DataSource secondary) throws SQLException {
//        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();
//
//        Map<Object, Object> dataSourceMap = Map.of(
//            DataSourceType.READ_WRITE, primary,
//            DataSourceType.READ_ONLY, secondary
//        );
//
//        routingDataSource.setTargetDataSources(dataSourceMap);
//        routingDataSource.setDefaultTargetDataSource(primary);
//
//        return routingDataSource;
//    }
//
//    @DependsOn({"routingDataSource"})
//    @Primary
//    @Bean
//    public DataSource dataSource(DataSource routingDataSource) {
//        return new LazyConnectionDataSourceProxy(routingDataSource);
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//        return jpaTransactionManager;
//    }

}