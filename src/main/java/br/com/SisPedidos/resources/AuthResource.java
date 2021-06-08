package br.com.SisPedidos.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.SisPedidos.dto.EmailDTO;
import br.com.SisPedidos.security.JWTUtil;
import br.com.SisPedidos.security.UserSS;
import br.com.SisPedidos.services.AuthService;
import br.com.SisPedidos.services.UserService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) throws ObjectNotFoundException {
		try {
			service.sendNewPassword(objDto.getEmail());
			return ResponseEntity.noContent().build();			
		}catch(ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
	}

}
