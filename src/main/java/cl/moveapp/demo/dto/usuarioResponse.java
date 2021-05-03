package cl.moveapp.demo.dto;
import java.util.Date;
import java.util.List;
import cl.moveapp.demo.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class usuarioResponse {

	private int id;
	private Date created;
	//private Date modified;
	//private Date last_login;
	//private String token;
	private boolean isActive;
}
