package com.facundoprecentado.service;

import com.facundoprecentado.domain.Guest;
import com.facundoprecentado.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendContactMail(Guest guest) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("tarjetamascotin@gmail.com");
        mail.setFrom(guest.getEmail());
        mail.setSubject(guest.getEmail() + " - Contacto desde Tarjeta Mascotin.com");
        mail.setText("Nombre; " + guest.getName() + "\n"
                + "Correo: " + guest.getEmail() + "\n"
                + "Mensaje: " + guest.getMessage());
        javaMailSender.send(mail);
    }

    public void sendNewUserMail(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("tarjetamascotin@gmail.com");
        mail.setFrom(user.getUsername());
        mail.setSubject(user.getUsername() + " - Nuevo usuario desde Tarjeta Mascotin.com");
        mail.setText("Correo: " + user.getUsername() + "\n"
                + "Contraseña: " + user.getPassword());
        javaMailSender.send(mail);
    }

    public void sendUserPassword(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("tarjetamascotin@gmail.com");
        mail.setFrom(user.getUsername());
        mail.setSubject(user.getUsername() + " - Recuperación de contraseña en Tarjeta Mascotin.com");
        mail.setText("Tu contraseña es: " + user.getPassword());
        javaMailSender.send(mail);
    }

}
