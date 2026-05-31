package org.costa.API_Clinica.auth;

import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.auth.dto.LoginRequest;
import org.costa.API_Clinica.auth.response.LoginResponse;
import org.costa.API_Clinica.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.crm(),
                        loginRequest.senha()
                )
        );

        String token = jwtService.gerarToken(authentication.getName());
        return new LoginResponse(token);
    }
}
