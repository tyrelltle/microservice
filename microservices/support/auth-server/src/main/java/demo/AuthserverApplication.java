package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
public class AuthserverApplication {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private Http401UnauthorizedEntryPoint authenticationEntryPoint;


		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
					.exceptionHandling()
					.authenticationEntryPoint(authenticationEntryPoint)
					.and()
					.logout()
					.logoutUrl("/api/logout")
 					.and()
					//.csrf()
					//.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
					//.disable()
					//.headers()
					//.frameOptions().disable()
					//.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authorizeRequests()
					.antMatchers("/api/authenticate").permitAll()
					.antMatchers("/api/register").permitAll()
					.antMatchers("/api/logs/**").hasAnyAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/api/**").authenticated()
					.antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/protected/**").authenticated();

		}
	}



	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
				.withClient("acme")
				.secret("acmesecret")
				.authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
				.scopes("webshop");
		}
	}
}
