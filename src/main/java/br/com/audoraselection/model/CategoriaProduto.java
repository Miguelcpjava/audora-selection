package br.com.audoraselection.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_categoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaProduto extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4643574071651010106L;
	private String nome;

}
