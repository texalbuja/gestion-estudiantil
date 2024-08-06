package com.gestionestudiantil.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class CorreoService {
	@Resource(mappedName = "java:jboss/mail/Default")
	private Session mailSession;

	@Asynchronous
	public void enviarCorreo(String para, String asunto, String contenido) {
		try {
			MimeMessage m = new MimeMessage(mailSession);
			Address from = new InternetAddress("info@gestionestudiantil.com");
			Address[] to = { new InternetAddress(para) };
			m.setFrom(from);
			m.setRecipients(javax.mail.Message.RecipientType.TO, to);
			m.setSubject(asunto);
			m.setSentDate(new Date());
			m.setContent(contenido, "text/plain");
			javax.mail.Transport.send(m);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void enviarCorreoMasivo(String[] para, String asunto,
			String contenido) {
		try {
			MimeMessage m = new MimeMessage(mailSession);
			Address from = new InternetAddress("info@gestionestudiantil.com");
			Address[] to = { new InternetAddress(para[0]) };
			m.setFrom(from);
			m.setRecipients(javax.mail.Message.RecipientType.TO, to);
			m.setSubject(asunto);
			m.setSentDate(new Date());
			m.setContent(contenido, "text/plain");
			javax.mail.Transport.send(m);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}