package pl.gddkia.security

import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pl.gddkia.exceptions.InvalidCredentials

@Service
@RequiredArgsConstructor
class JwtUserDetailsService(
    private val entityAuthUserRepository: EntityAuthUserRepository
) : UserDetailsService {
    var LOGGER  = LoggerFactory.getLogger(JwtUserDetailsService::class.java)

    override fun loadUserByUsername(username: String?): MyUser {
        val user = entityAuthUserRepository.findMyUserByUsername(username)
            .orElseThrow {
                LOGGER.info("Nie znaleziono użytkownika ${username}")
                InvalidCredentials("Niepoprawne dane")
            }
        return MyUser(user.username, user.password, listOf(SimpleGrantedAuthority("USER")))
    }


}


