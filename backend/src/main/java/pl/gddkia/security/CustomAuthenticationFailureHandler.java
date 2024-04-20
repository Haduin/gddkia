package pl.gddkia.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            // Tutaj możesz dostosować zachowanie w przypadku niepoprawnego hasła.
            // Na przykład możesz zwrócić niestandardowy kod błędu lub informację JSON.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Niepoprawne hasło.");
        } else {
            // Obsługa innych rodzajów wyjątków uwierzytelniania
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("Błąd uwierzytelnienia.");
        }
    }

}