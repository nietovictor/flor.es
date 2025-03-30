package es.upm.dit.isst.isstgrupo07flores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Isstgrupo07floresApplication {

	public static void main(String[] args) {
		SpringApplication.run(Isstgrupo07floresApplication.class, args);

		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // La contraseña original que deseas encriptar
        String password = "pass";

        // Encriptar la contraseña
        String encodedPassword = encoder.encode(password);

        // Mostrar la contraseña encriptada
        System.out.println("Contraseña encriptada: " + encodedPassword);
	}



}
