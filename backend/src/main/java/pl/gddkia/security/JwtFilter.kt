package pl.gddkia.security

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
@RequiredArgsConstructor
class JwtFilter(
    private val tokenManager: TokenManager,
    private val userDetailsService: JwtUserDetailsService,
) : OncePerRequestFilter() {
    private val LOGGER: Logger = LogManager.getLogger(JwtFilter::class.java)
    private val BEARER_PREFIX = "Bearer "
    private val AUTHORIZATION_PREFIX = "Authorization"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val (token: String, username: String?) = readAndValidateAuthHeader(request)

        loadAndValidateAuthUserDetails(username, token, request)

        filterChain.doFilter(request, response)

    }

    private fun readAndValidateAuthHeader(request: HttpServletRequest): Pair<String, String?> {
        val tokenHeader = request.getHeader(AUTHORIZATION_PREFIX)
        var username: String? = null
        var token: String = ""
        tokenHeader
            ?.takeIf { it.startsWith(BEARER_PREFIX) }
            ?.run {
                token = tokenHeader.substring(BEARER_PREFIX.length)
                try {
                    username = tokenManager.extractUsername(token)
                } catch (e: ExpiredJwtException) {
                    LOGGER.info("Token wygasł dla ${e.claims}")
                }

            }
        return Pair(token, username)
    }

    private fun loadAndValidateAuthUserDetails(
        username: String?,
        token: String,
        request: HttpServletRequest
    ) {
        SecurityContextHolder.getContext().authentication ?: run {
            username
                ?.takeIf { it.isNotEmpty() }
                ?.let { userDetailsService.loadUserByUsername(it) }
                ?.takeIf { tokenManager.validateToken(token, it) }
                ?.also {
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(it, null, it.authorities)
                    authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                }
        }
    }
}