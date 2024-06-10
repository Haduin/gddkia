package pl.gddkia.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import pl.gddkia.exceptions.MainResponse

@RestController
@CrossOrigin
class SecurityController(
    @Autowired private val tokenManager: TokenManager,
    @Autowired private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/login")
    fun createToken(@RequestBody authUserApi: AuthUserApi): ResponseEntity<MainResponse.UserLogged> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authUserApi.username,
                authUserApi.password
            )
        )
        return ResponseEntity.ok()
            .body(
                MainResponse.UserLogged(
                    tokenManager.generateToken(authUserApi.username.orEmpty())
                )
            )

    }


}