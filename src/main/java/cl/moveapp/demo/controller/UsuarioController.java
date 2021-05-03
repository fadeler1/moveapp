package cl.moveapp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import cl.moveapp.demo.repository.PhoneRepository;
import cl.moveapp.demo.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import cl.moveapp.demo.dto.TokenRequest;
import cl.moveapp.demo.dto.UsuarioRequest;
import cl.moveapp.demo.dto.usuarioResponse;
import cl.moveapp.demo.exception.ResourceNotFoundException;
import cl.moveapp.demo.model.Usuario;

@RestController
@RequestMapping("/moveapp/")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PhoneRepository phoneRepository;

	@PostMapping("/agregar")
	public  ResponseEntity<?> agregarUsuario(@Valid @RequestBody UsuarioRequest request){
		Usuario usuario = new Usuario();
		usuario.setEmail(request.getEmail());
		usuario.setPassword(request.getPassword());
		usuario.setPhones(request.getPhones());
		usuario.setActive(true);
		Date objDate = new Date(); 
	    usuario.setCreated(objDate); 

	    Usuario user = usuarioRepository.save(usuario);
	    
	    usuarioResponse response  = new usuarioResponse();
	    
	    response.setId(user.getId());
	    response.setCreated(user.getCreated());
	    response.setActive(user.isActive());
	    
	    return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/actualizar")
	public  ResponseEntity<Usuario> actualizarUsuario(@RequestBody UsuarioRequest request) {
		System.out.println(request.toString());
		Usuario usuario = new Usuario();
		usuario.setEmail(request.getEmail());
		usuario.setPassword(request.getPassword());
		usuario.setPhones(request.getPhones());
		
		Date objDate = new Date(); 
	    usuario.setCreated(objDate); 
        
	    Usuario user = usuarioRepository.save(usuario);
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/usuarios")
	public  List<Usuario> getAllUsuarios(){
		return usuarioRepository.findAll();
	}
	
	/*@GetMapping("/eliminar/{id}")
	public   ResponseEntity<?> deleteUsuario(@PathVariable(value = "id") Integer usuarioId) throws ResourceNotFoundException{
		usuarioRepository.deleteById(usuarioId);
		return ResponseEntity.ok().build();
		
	}*/
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") Integer usuarioId)
			throws ResourceNotFoundException{
		Usuario usuairo = usuarioRepository.findById(usuarioId)
				.orElseThrow (() -> new ResourceNotFoundException("Usuario no encontrado con id : "+usuarioId));

		return ResponseEntity.ok().body(usuairo);

	}
	
	@PostMapping("token")
	public TokenRequest login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		TokenRequest token  = new TokenRequest();
		token.setToken(getJWTToken(username));
		token.setUsername(username);
		token.setPassword(pwd);

		return token;
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return token;
	}
	
}
