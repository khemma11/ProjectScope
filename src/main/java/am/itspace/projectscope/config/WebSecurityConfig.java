package am.itspace.projectscope.config;

import am.itspace.projectscope.security.JwtAuthFilter;
import am.itspace.projectscope.security.service.UserSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

        private final UserSecurityService userSecurityService;
        private final JwtAuthFilter jwtFilter;

        public WebSecurityConfigurer(UserSecurityService userService, JwtAuthFilter jwtFilter) {
            this.userSecurityService = userService;
            this.jwtFilter = jwtFilter;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/project/TeamLeader", "/project/search").hasAnyAuthority("TEAM_LEADER")
                    .antMatchers(HttpMethod.GET, "/project/Member").hasAnyAuthority("TEAM_MEMBER")
                    .antMatchers(HttpMethod.POST, "/user/register", "/user/login").hasAnyAuthority("TEAM_MEMBER", "TEAM_LEADER")
                    .antMatchers(HttpMethod.DELETE, "/project/{id}").hasAnyAuthority("TEAM_LEADER")
                    .antMatchers("/user/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.headers().cacheControl();
        }


        @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}

//MVC
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserDetailService userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .formLogin()
//                .loginPage("/loginPage")
//                .loginProcessingUrl("/perform_login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/successLogin")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/teamLider").hasAnyAuthority("TEAM_LEADER")
//                .antMatchers("/users").hasAnyAuthority("TEAM_LEADER")
//                .antMatchers("/projects").hasAnyAuthority("TEAM_LEADER")
//                .antMatchers("/newProjectPage").hasAnyAuthority("TEAM_LEADER")
//                .antMatchers("/userProjects").hasAnyAuthority("TEAM_MEMBER")
//                .antMatchers("/logs").hasAnyAuthority("TEAM_MEMBER")
//                .antMatchers("/addLogPage").hasAnyAuthority("TEAM_MEMBER")
//                .antMatchers("/").permitAll();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder);
//    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
