package pl.gddkia.security

import jakarta.persistence.*

@Entity
@Table(name = "USERS")
data class EntityAuthUser(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
)
