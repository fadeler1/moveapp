package cl.moveapp.demo.model;

import java.util.Date;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private int id;	
	
	private String email;
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@GeneratedValue
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
//	private String token;
	private boolean isActive;
	
	@OneToMany(targetEntity = Phone.class , cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id" , referencedColumnName = "id")
	private List<Phone> phones;
	
	
}
