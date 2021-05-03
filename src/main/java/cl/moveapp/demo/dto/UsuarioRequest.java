package cl.moveapp.demo.dto;


import java.util.List;

import javax.validation.constraints.Pattern;

import cl.moveapp.demo.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email invalido")
	private String email;
	
	private String password;
	private List<Phone> phones;
	
}
