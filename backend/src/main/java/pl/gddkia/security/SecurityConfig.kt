package pl.gddkia.security

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig(private val converter: JwtAuthConverter) {

    @Value("\${security.front_url}")
    val frontUri: String = ""

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors {
                it.configurationSource {
                    val corsConfiguration = CorsConfiguration()
                    corsConfiguration.allowedOrigins = listOf(frontUri)
                    corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
                    corsConfiguration.allowCredentials = true
                    corsConfiguration.applyPermitDefaultValues()
                    corsConfiguration
                }
            }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/***").permitAll()
                it.requestMatchers("/actuator/***").permitAll()
                it.requestMatchers("/actuator").permitAll()
                it.requestMatchers("/**").authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .oauth2ResourceServer { o ->
                o.jwt {
                    it.jwtAuthenticationConverter(converter)
                }
            }
            .build()
    }
}