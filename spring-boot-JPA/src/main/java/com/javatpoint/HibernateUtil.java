package com.javatpoint;


public class HibernateUtil {
//	  private static StandardServiceRegistry registry;
//	  private static SessionFactory sessionFactory;
//
//	  public static SessionFactory getSessionFactory() {
//	    if (sessionFactory == null) {
//	      try {
//	        StandardServiceRegistryBuilder registryBuilder = 
//	            new StandardServiceRegistryBuilder();
//
//	        Map<String, String> settings = new HashMap<>();
//	        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//	        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/");
//	        settings.put("hibernate.connection.username", "root");
//	        settings.put("hibernate.connection.password", "marwan");
//	        settings.put("hibernate.hbm2ddl.auto", "none");
//	        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//	        settings.put("hibernate.flushMode", "FLUSH_AUTO");
//	        settings.put("spring.datasource.initialization-mode", "always");
//	        registryBuilder.applySettings(settings);
//
//	        registry = registryBuilder.build();
//
//	        MetadataSources sources = new MetadataSources(registry)
//	            .addAnnotatedClass(UserRecord.class);
//	        sources.addAnnotatedClass(Role.class);
//	        sources.addAnnotatedClass(Privilege.class);
//	        sources.addAnnotatedClass(ConfirmationToken.class);
//
//	        Metadata metadata = sources.getMetadataBuilder().build();
//
//	        sessionFactory = metadata.getSessionFactoryBuilder().build();
//	      } catch (Exception e) {
//	        System.out.println("SessionFactory creation failed");
//	        if (registry != null) {
//	          StandardServiceRegistryBuilder.destroy(registry);
//	        }
//	      }
//	    }
//	    return sessionFactory;
//	  }
//
//	  public static void shutdown() {
//	    if (registry != null) {
//	      StandardServiceRegistryBuilder.destroy(registry);
//	    }
//	  }
	}