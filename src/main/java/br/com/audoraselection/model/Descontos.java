package br.com.audoraselection.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável pela administração dos descontos que serão aplicados conforme
 * escolhido no campo aplicabilidade, onde este campo determina a forma de aplicação,
 * por exemplo: Se aplicabilidade é P, então o campo aplica_id rece o valor da id de um produto
 * caso seja C, o campo aplica_id recebe o valor da chave de uma categoria, no caso for geral, o
 * campo sempre recebe o valor default que é zero.
 * @author Miguel
 *
 */
@Entity
@Table(name = "tbl_desconto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Descontos extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1971743114345440658L;
	private String cupom;
	private String tipo; //[P]percentual ou [V] Valor
	private String aplicabilidade; //[G] Geral, ou seja, valor total do carrinho ou [C] Categoria ou [P] Produto
	private Long aplica_id; 
	private String formaPagamento;//[C]Cartao ou [V] A vista
	private String tipoPagamentoComCartao; //[C]Crédito ou [D]Débito 
	private int valorDesconto;
	private boolean cumulativo;
	private boolean ativo;
	
}
