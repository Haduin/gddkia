package pl.gddkia.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController(
    @Autowired private val tokenManager: TokenManager,
    @Autowired private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/login")
    fun createToken(@RequestBody authUserApi: AuthUserApi): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authUserApi.username,
                authUserApi.password
            )
        )
        return tokenManager.generateToken(authUserApi.username.orEmpty())
    }


}