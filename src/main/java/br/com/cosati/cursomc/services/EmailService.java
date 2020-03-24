package br.com.cosati.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.cosati.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
