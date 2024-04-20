package pl.gddkia.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import pl.gddkia.estimate.EstimateServiceImpl
import java.io.Serializable
import java.security.Key
import java.util.*
import kotlin.reflect.KFunction1


@Component
class TokenManager : Serializable {
    private val LOGGER: Logger = LogManager.getLogger(
        TokenManager::class.java
    )
    private val TOKEN_VALIDITY: Long = 1000 * 60 * 5

    @Value("\${security.secret}")
    private val SECRET: String = ""

    fun validateToken(token: String, userDetails: UserDetails?): Boolean {
        val username = extractUsername(token)
        return isTokenActive(token)
    }

    fun generateToken(username: String): String {
        val claims: MutableMap<String, Any> = HashMap()
        return createToken(claims, username)
    }

    fun extractUsername(token: String): String? {
        return extractClaim(token, Claims::getSubject)
    }

    private fun <T> extractClaim(token: String, kFunction1: KFunction1<Claims, T>): T {
        val claims = extractAllClaims(token)
        return kFunction1.invoke(claims)
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            LOGGER.info("Token wygasł dla ${e.claims}")
            throw ExpiredJwtException(e.header,e.claims,e.message)
        }

    }

    private fun isTokenActive(token: String): Boolean {
        return extractExpiration(token).after(Date())
    }

    private fun createToken(claims: Map<String, Any>, username: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + TOKEN_VALIDITY))
            .signWith(getSignKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSignKey(): Key {
        val keyBytes = Base64.getDecoder().decode(SECRET)
        return Keys.hmacShaKeyFor(keyBytes)
    }

}