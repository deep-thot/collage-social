package se.collagekoren;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableOAuth2Client
@SpringBootApplication
public class CollageSocialApplication extends WebSecurityConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(CollageSocialApplication.class, args);
    }

    @Autowired
    OAuth2ClientContext clientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).antMatcher("/**")
                .authorizeRequests()
            .antMatchers("/", "/start", "/login**", "/**.js")
                .permitAll()
            .anyRequest()
                .authenticated().and().logout().logoutSuccessUrl("/").permitAll()
            .and().csrf().csrfTokenRepository(csrfTokenRepository())
            .and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

    private Filter ssoFilter(){
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), clientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId()));
        return googleFilter;
    }

    @Bean
    @ConfigurationProperties("google.client")
    OAuth2ProtectedResourceDetails google(){
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    ResourceServerProperties googleResource(){
        return new ResourceServerProperties();
    }

    @Bean
    FilterRegistrationBean filterRegistrationBean(OAuth2ClientContextFilter filter){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(filter);
        bean.setOrder(-100);
        return bean;
    }

    private Filter csrfHeaderFilter(){
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken)httpServletRequest.getAttribute(CsrfToken.class.getName());
                if(csrf != null){
                    Cookie cookie = WebUtils.getCookie(httpServletRequest, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if(cookie == null || token != null && !token.equals(cookie.getValue())){
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        httpServletResponse.addCookie(cookie);
                    }

                }
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository(){
        HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
        tokenRepository.setHeaderName("X-XSRF-TOKEN");
        return tokenRepository;
    }
}