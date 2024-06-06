package pl.gddkia.security

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EntityAuthUserRepository : JpaRepository<EntityAuthUser, Long> {
    fun findMyUserByUsername(username: String?): Optional<EntityAuthUser>
}
