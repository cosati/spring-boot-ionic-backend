package br.com.cosati.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cosati.cursomc.domain.Cliente;
import br.com.cosati.cursomc.repositories.ClienteRepository;
import br.com.cosati.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		char temp;
		switch(opt) {
			case 0: //gera digito
				temp = (char) (rand.nextInt(10) + 48);
				break;
			case 1: //gera letra maiúscula
				temp = (char) (rand.nextInt(26) + 65);
				break;
			default: //gera letra minúscula
				temp = (char) (rand.nextInt(26) + 97);
				break;
		}
		return temp;
	}
	
}
