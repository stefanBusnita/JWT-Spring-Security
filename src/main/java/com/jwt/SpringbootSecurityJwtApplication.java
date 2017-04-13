package com.jwt;

import com.jwt.model.sysadmin.Rights;
import com.jwt.model.sysadmin.User;
import com.jwt.model.sysadmin.UserPrivateData;
import com.jwt.services.utils.sysadmin.RightsService;
import com.jwt.services.utils.sysadmin.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashSet;
import java.util.Set;

/**
 * Bootstrap application and start with main method.
 * 
 * @author stefan.busnita
 *
 */
@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class SpringbootSecurityJwtApplication {


	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SpringbootSecurityJwtApplication.class);




	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJwtApplication.class, args);
	}


	/**
	 * Workaround for initializing database by listening to ApplicationContextInitialized event.
	 * Inject the CustomRepository into the commandRunner bean
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner doInitialConfig(UserService repository, RightsService rightsRepository) {
		return (String... args) -> {
			//save some users for tests

			UserPrivateData user1PrivateData = new UserPrivateData();
			user1PrivateData.setEmail("user1@gamil.com");

			User user1 = new User();
			user1.setUserPrivateData(user1PrivateData);
			user1.setUsername("stefan");
			user1.setPassword("123456"); //DEMO PURPOSE USE HASH FOR PASSWORD STORING IN DB check Spring password encoder

			Rights userRight = new Rights();
			userRight.setRightName("USER");

			Rights visitorRights = new Rights();
			visitorRights.setRightName("GUEST");

			Set<Rights> user1Rights = new HashSet(){
				{
					add(userRight);
					add(visitorRights);
				}
			};

			user1.setUserRights(user1Rights);

			repository.save(user1);


			User retrieved = new User();

			retrieved = repository.findByUsername("stefan");

			System.out.println("--------------USER WAS FOUND AND RETRIEVED-----------------");
			System.out.println(retrieved);

		};
	}

}
