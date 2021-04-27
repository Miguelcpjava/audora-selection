package br.com.audoraselection.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends AbstractEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226670278471407486L;
	@Email
	private String username;
	private String password;
	@Column(columnDefinition = "boolean default false")
	private boolean administrador;
	private boolean enabled;
	
	
	
}
