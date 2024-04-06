package pl.gddkia.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
@RequiredArgsConstructor
class JwtFilter @Autowired constructor(
    private val tokenManager: TokenManager,
    private val userDetailsService: JwtUserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenHeader: String? = request.getHeader("Authorization")
        var token: String = ""
        var username: String? = null
        if (tokenHeader?.startsWith("Bearer ") == true) {
            token = tokenHeader.substring(7)
            username = tokenManager.extractUsername(token)
        }

        if (SecurityContextHolder.getContext().authentication == null && !username.isNullOrEmpty()) {
            val userDetails: MyUser? = userDetailsService.loadUserByUsername(username)
            if (tokenManager.validateToken(token, userDetails)) {
                val authenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails?.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }

        filterChain.doFilter(request, response)

    }
}