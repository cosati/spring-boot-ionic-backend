package br.com.cosati.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cosati.cursomc.domain.ItemPedido;
import br.com.cosati.cursomc.domain.PagamentoComBoleto;
import br.com.cosati.cursomc.domain.Pedido;
import br.com.cosati.cursomc.domain.enums.EstadoPagamento;
import br.com.cosati.cursomc.repositories.ItemPedidoRepository;
import br.com.cosati.cursomc.repositories.PagamentoRepository;
import br.com.cosati.cursomc.repositories.PedidoRepository;
import br.com.cosati.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido x : obj.getItens()) {
			x.setDesconto(0.0);
			x.setPreco(produtoService.find(x.getProduto().getId()).getPreco());
			x.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		return obj;
	}
	
}
