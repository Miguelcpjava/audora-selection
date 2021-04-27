package br.com.audoraselection.util;

public enum TipoPagamento {

	CARTAO("C"),
	AVISTA("V");
	
	private String formaDePagamento;

	TipoPagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	  @Override
	    public String toString() {
	        return formaDePagamento;
	    }

	
}
