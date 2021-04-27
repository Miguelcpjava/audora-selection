package br.com.audoraselection.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_produto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Produto extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8424983779606498491L;
	private String nome;
	@Column(precision=10,scale=2)
	private BigDecimal preco;
	@OneToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private CategoriaProduto categoria;
	
}
