package pl.gddkia.security

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val jwtUserDetailsService: JwtUserDetailsService
) {

    @Value("\${security.front_url}")
    val frontUri: String = ""

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors { obj ->
                obj.configurationSource {
                    val corsConfiguration = CorsConfiguration()
                    corsConfiguration.allowedOrigins = listOf(frontUri)
                    corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
                    corsConfiguration.allowCredentials = true
                    corsConfiguration.applyPermitDefaultValues()
                    corsConfiguration
                }
            }
            .csrf { obj -> obj.disable() }
            .authorizeHttpRequests { req ->
                req.requestMatchers("/login").permitAll()
                req.requestMatchers("/**").authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authenticationProvider(authenticationProvider())
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * Enables to catch spring security events using {@code AuthenticationEvents}
     */
    @Bean
    fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher?): AuthenticationEventPublisher {
        return DefaultAuthenticationEventPublisher(applicationEventPublisher)
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(jwtUserDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}