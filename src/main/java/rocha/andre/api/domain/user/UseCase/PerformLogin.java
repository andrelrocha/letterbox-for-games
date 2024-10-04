package rocha.andre.api.domain.user.UseCase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.auditLog.LoginStatus;
import rocha.andre.api.domain.auditLog.useCase.RegisterAuditLog;
import rocha.andre.api.domain.user.DTO.UserLoginDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.security.AuthTokensDTO;
import rocha.andre.api.infra.security.TokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PerformLogin {

    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RegisterAuditLog registerAuditLog;
    @Autowired
    private UserRepository userRepository;

    @Transactional(noRollbackFor = BadCredentialsException.class)
    public AuthTokensDTO performLogin(UserLoginDTO data, HttpServletRequest request) {
        if (data.login().isEmpty() || data.password().isEmpty()) {
            throw new IllegalArgumentException("Login e senha não podem ser vazios.");
        }

        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        try {
            Authentication authentication = manager.authenticate(authenticationToken);

            User userAuthenticated = (User) authentication.getPrincipal();

            resetFailedAttempts(userAuthenticated);

            String accessToken = tokenService.generateAccessToken(userAuthenticated);
            String refreshToken = tokenService.generateRefreshToken(userAuthenticated);

            registerAuditLog.logLogin(
                    data.login(),
                    request,
                    LoginStatus.SUCCESS,
                    request.getHeader("User-Agent"),
                    UUID.fromString("a05fa936-8640-4474-b5cd-963a36ff5fe6")
            );

            return new AuthTokensDTO(accessToken, refreshToken);

        } catch (BadCredentialsException e) {
            // Autenticação falhou, vamos tratar o incremento de falhas
            handleFailedLogin(data.login(), request);
            throw new BadCredentialsException("Login ou senha errados.");
        }
    }

    private void resetFailedAttempts(User user) {
        user.setAccessFailedCount(0);
        user.setLockoutEnabled(false);
        user.setLockoutEnd(null);
        userRepository.save(user);
    }

    private void handleFailedLogin(String login, HttpServletRequest request) {
        System.out.println("Iniciando handleFailedLogin para o login: " + login);

        // Buscar o usuário diretamente pelo login
        User user = userRepository.findByLoginToHandle(login);

        // Verificar se o usuário foi encontrado
        if (user == null) {
            System.out.println("Usuário não encontrado para o login: " + login);
            throw new ValidationException("Usuário não encontrado para o login: " + login);
        }

        System.out.println("Usuário encontrado: " + user.getLogin());

        // Incrementar tentativas falhas
        int failedAttempts = user.getAccessFailedCount() + 1;
        System.out.println("Tentativas falhas atuais: " + failedAttempts);

        // Verificar se o limite de tentativas foi atingido
        if (failedAttempts >= MAX_ATTEMPTS) {
            user.setLockoutEnabled(true);
            user.setLockoutEnd(LocalDateTime.now().plusMinutes(15));
            System.out.println("Usuário bloqueado por 15 minutos.");
        } else {
            user.setAccessFailedCount(failedAttempts);
            System.out.println("Incrementando contador de tentativas falhas para: " + failedAttempts);
        }

        // Salvar as alterações no banco de dados
        var user1 = userRepository.saveAndFlush(user);
        System.out.println(user1);
        System.out.println(user1.getId());
        System.out.println("novo contador: "+user1.getAccessFailedCount());
        System.out.println("Usuário atualizado no banco de dados.");

        registerAuditLog.logLogin(
                login,
                request,
                LoginStatus.FAILURE,
                request.getHeader("User-Agent"),
                UUID.fromString("a05fa936-8640-4474-b5cd-963a36ff5fe6")
        );
    }

}
