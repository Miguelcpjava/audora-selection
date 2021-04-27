package br.com.audoraselection.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_carrinhocompras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoCompras extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -707920200417483381L;
	private BigDecimal subtotal;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "carrinho")
	private List<ItemCarrinho> linesItems = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	@Column(columnDefinition = "Boolean default false")
	private boolean finalizado;
	
}
