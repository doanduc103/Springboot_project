package com.example.demo.security;

import com.example.demo.security.oauth.CustomOAuth2UserService;
import com.example.demo.security.oauth.CustomOauth2User;
import com.example.demo.security.oauth.Oauth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService() {
        return new JwTUserDetailService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService());
        authProvider.setPasswordEncoder(PasswordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Bean
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jwtUserDetailService).passwordEncoder(PasswordEncoder());
//    }

    // Tạo Bean AuthenticationManager quản lý xác thực
    @Bean
    public AuthenticationManager authenticationManageBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/trang-chu/**").hasAuthority("ADMIN")
                .antMatchers("/tai-khoan/**").hasAuthority("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler).failureUrl("/login?error=true")
                .and()
                .oauth2Login().loginPage("/login")
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and().successHandler(oauth2LoginSuccessHandler)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
//                .deleteCookies("JWT_TOKEN")
                .logoutSuccessUrl("/index").permitAll()
                .and()
                .rememberMe()
//                .key("uniqueAndSecret").tokenValiditySeconds(24 * 60 * 60)
                .tokenRepository(persistentTokenRepository()).
                userDetailsService(userDetailService())
                .and()
                .exceptionHandling().accessDeniedPage("/403");
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Không sử dụng session lưu lại trạng thái của principal


    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/images/**","/dist/**","/plugins/**");
    }

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
}
