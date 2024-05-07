package pl.gddkia.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component
import pl.gddkia.exceptions.InvalidCredentials

@Component
class AuthenticationEvents {
    var logger: Logger = LoggerFactory.getLogger(AuthenticationEvents::class.java)

    @EventListener
    fun onSuccess(success: AuthenticationSuccessEvent?) {
        logger.info("Zalogowano sie ${(success?.source as UsernamePasswordAuthenticationToken).principal}")
    }

    @EventListener
    fun onFailure2(failures: AuthenticationFailureBadCredentialsEvent) {
        logger.info("Nie udało sie zalogować użytkownikowi: ${(failures.source as UsernamePasswordAuthenticationToken).principal}")
        throw InvalidCredentials("Niepoprawne dane")
    }

}