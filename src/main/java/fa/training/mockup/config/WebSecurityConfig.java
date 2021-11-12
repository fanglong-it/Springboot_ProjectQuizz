//package fa.training.mockup.config;
//
//
//
//
//import com.example.usermanager.service.CustomerUserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Autowired
////    private DataSource dataSource;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new CustomerUserDetailService();
//    }
//
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//
//        return authProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//
////                .antMatchers("/list_users").authenticated()
////                .antMatchers("/admin/**").hasAuthority("ADMIN")
////                .antMatchers("/manager/**").hasAuthority("MANAGER")
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                	.usernameParameter("email")
//                    .defaultSuccessUrl("/list_users")
//                    .failureUrl("/login_page?incorrectAccount")
//                    .permitAll()
//                .and()
//                .logout().permitAll()
//                .and().exceptionHandling().accessDeniedPage("/access-denied");
//    }
//
//
//}
