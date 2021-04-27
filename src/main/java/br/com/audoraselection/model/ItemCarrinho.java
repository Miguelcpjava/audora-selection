package br.com.audoraselection.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_itemcarrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinho extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9204575667345052646L;
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;
	private int quantidade;
	@Column(name = "preco", nullable = false, precision = 10,scale = 2 )
	private BigDecimal preco;
	@ManyToOne
	@JoinColumn(name = "carrinho_id", nullable = false)
	private CarrinhoCompras carrinho;
	
	
	
}
