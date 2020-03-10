package br.com.cosati.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.cosati.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

//		Serve apenas como exemplo. Deve ser trocado por web service para buscar vencimento do boleto
		public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(instanteDoPedido);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			pgto.setDataVencimento(cal.getTime());
		}
	
}
