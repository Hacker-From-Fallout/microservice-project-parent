package chernyshov.ignat.admin.config;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import chernyshov.ignat.admin.web.client.OAuthHttpHeadersProvider;
import jakarta.annotation.Priority;

@Configuration
public class SecurityBeans {
    
	@Bean
    public OAuthHttpHeadersProvider oAuthHttpHeadersProvider(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        return new OAuthHttpHeadersProvider(
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService)
        );
    }
    
    @Bean
    @Priority(0)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(request -> Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                        .map(header -> header.startsWith("Bearer ")).orElse(false))
                .oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(customizer -> customizer.anyRequest().hasAuthority("SCOPE_metrics_server"))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    @Bean
    @Priority(1)
    public SecurityFilterChain uiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(customizer -> customizer.anyRequest().hasRole("ADMIN"))
                .build();
    }
    
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
        OidcUserService oidcUserService = new OidcUserService();
        return userRequest -> {
            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
            List<GrantedAuthority> authorities =
                    Stream.concat(oidcUser.getAuthorities().stream(),
                            Optional.ofNullable(oidcUser.getClaimAsStringList("groups"))
                                    .orElseGet(List::of)
                                    .stream()
                                    .filter(role -> role.startsWith("ROLE_"))
                                    .map(SimpleGrantedAuthority::new)
                                    .map(GrantedAuthority.class::cast))
                            .toList();

            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
} 

