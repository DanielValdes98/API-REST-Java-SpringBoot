package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        //System.out.println("Este es el inicio del filter");
        var authHeader = request.getHeader("Authorization");
        //System.out.println(token);

        /*
        if(token ==  null || token == ""){
            throw new RuntimeException("El token enviado no es valido");
        }
        */

        if(authHeader != null){

            //System.out.println("Validamos que token no sea null");
            var token = authHeader.replace("Bearer ", "");
            //System.out.println(token);
            //System.out.println(tokenService.getSubject(token)); // Este usuario tiene sesion?
            var nombreUsuario = tokenService.getSubject(token); // Extrae el username

            // Con esto verificamos que el login es valido, que el usuario existe
            if (nombreUsuario != null){
                // Tolen valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); // Forzamos el inicio de session
                SecurityContextHolder.getContext().setAuthentication(authentication); // Seteamos manualmente la autenticacion
            }
        }
        filterChain.doFilter(request, response); // Ejecuta la magia del filtro y manda el request y el response que llega
    }
}
