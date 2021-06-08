package br.com.SisPedidos.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.SisPedidos.domain.Cliente;
import br.com.SisPedidos.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
