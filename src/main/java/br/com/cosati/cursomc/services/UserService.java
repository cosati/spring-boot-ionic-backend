package br.com.cosati.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.cosati.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			// retorna usuário logado
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
