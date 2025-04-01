package es.upm.dit.isst.isstgrupo07flores.service;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.ClienteRepository;
import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FloricultorRepository floricultorRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Optional<Cliente> cliente = clienteRepository.findByCorreoElectronico(correoElectronico);
        if (cliente.isPresent()) {
            return User.builder()
                    .username(cliente.get().getCorreoElectronico())
                    .password(cliente.get().getContrasena())
                    .roles("CLIENTE")
                    .build();
        }
        Optional<Floricultor> floricultor = floricultorRepository.findByCorreoElectronico(correoElectronico);
        if (floricultor.isPresent()) {
            return User.builder()
                    .username(floricultor.get().getCorreoElectronico())
                    .password(floricultor.get().getContrasena())
                    .roles("FLORICULTOR")
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + correoElectronico);
        }
    }
}
