package pl.gddkia.security

import lombok.RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class JwtUserDetailsService(
    private val entityAuthUserRepository: EntityAuthUserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): MyUser? {
        val user = entityAuthUserRepository.findMyUserByUsername(username)
            .orElseThrow()
        return MyUser(user.username, user.password, listOf(SimpleGrantedAuthority("USER")))
    }


}


