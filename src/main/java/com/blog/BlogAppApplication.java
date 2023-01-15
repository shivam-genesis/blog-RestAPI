package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableTransactionManagement // For Transactional property
@EnableWebMvc // For Swagger UI
@EnableCaching
public class BlogAppApplication /*implements CommandLineRunner*/{
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	@Autowired
//	private UserRepo userRepository;
//	
//	public void run(String... args) throws Exception{
//		User u2 = new User();
//		u2.setUserId(1);
//		u2.setName("Shivam");
//		u2.setEmail("shivam@gmail.com");
//		u2.setPassword("abc123");
//		u2.setEncodedPassword(this.bCryptPasswordEncoder.encode("abc123"));
//		u2.setAbout("Specialist Programmer at Infosys!!");
//		u2.setRole("ROLE_ADMIN");
//		this.userRepository.save(u2);
//	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}