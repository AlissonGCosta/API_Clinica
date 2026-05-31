package org.costa.API_Clinica.auth;

import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.exception.ResourceNotFoundException;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.medicos.repository.MedicosRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MedicosRepository medicosRepository;

    @Override
    public UserDetails loadUserByUsername(String crm) throws UsernameNotFoundException {
        MedicoEntity medico = medicosRepository.findByCrm(crm)
                .orElseThrow(() -> new ResourceNotFoundException("Medico nao encontrado"));

        return new User(
                medico.getCrm(),
                medico.getSenha(),
                List.of(new SimpleGrantedAuthority(medico.getRole().name()))
        );
    }
}
