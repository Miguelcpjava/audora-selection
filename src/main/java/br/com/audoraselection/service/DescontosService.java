package br.com.audoraselection.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.audoraselection.model.Descontos;
import br.com.audoraselection.repository.DescontosRepository;

@Service
public class DescontosService {

	@Autowired
	private DescontosRepository descontosResporitory;
	
	
	public Descontos criar(Descontos descontos) {
		return descontosResporitory.save(descontos);
	}
	
	public List<Descontos> buscarDescontosAtivos(){
		return descontosResporitory.findByAtivo(true);
	}
	
	public List<Descontos> buscarDescontosDeProdutos(Long produtoID){
		List<Descontos> descontos = new ArrayList<Descontos>();
		for(Descontos desconto : buscarDescontosAtivos()) {
			if(desconto.getAplicabilidade().equals("P") && desconto.getAplica_id() == produtoID) {
				descontos.add(desconto);
			}
		}
		return descontos;
	}
	
	public List<Descontos> buscarDescontosDeCategoria(Long categoriaID){
		List<Descontos> descontos = new ArrayList<Descontos>();
		for(Descontos desconto : buscarDescontosAtivos()) {
			if(desconto.getAplicabilidade().equals("C") && desconto.getAplica_id() == categoriaID) {
				descontos.add(desconto);
			}
		}
		return descontos;
	}
}
