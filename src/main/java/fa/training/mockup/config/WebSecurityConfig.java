package fa.training.mockup.config;





import fa.training.mockup.service.CustomerUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomerUserDetailService();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());


        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/course/").permitAll()
                .antMatchers("/quiz/editQuiz/**","/quiz/viewQuiz/**","/quiz/viewAnswer/**","/quiz/createAnswer/**","/course/createPage").hasAnyAuthority("ADMIN")
                .antMatchers("/quiz/joinPage/**","/quiz/attempt","/grade/view","/quiz/check").hasAnyAuthority("USER")


                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/user/login")
                	.usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .failureUrl("/user/login?incorrectAccount")
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/user/logout")
                    .permitAll()
                .and().exceptionHandling().accessDeniedPage("/user/access-denied");
    }


}
