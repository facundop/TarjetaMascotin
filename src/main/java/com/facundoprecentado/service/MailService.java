package com.facundoprecentado.service;

import com.facundoprecentado.domain.Guest;
import com.facundoprecentado.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public void sendNewSocioMail(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("facundop@gmail.com");
        mail.setBcc("tarjetamascotin@gmail.com");
        mail.setFrom(user.getUsername());
        mail.setSubject(user.getUsername() + ", Bienvenido a Tarjeta  Mascotin!");
        mail.setText("Bienvenid@, muchas gracias por registrarse en Tarjeta Mascotin!" + "\n"
                + "De ahora en adelante tu mascota y tú disfrutaran de nuevas experiencias juntos y al mismo veras un ahorro mensual para tu beneficio económico." + "\n"  + "\n"
                + "Usuario: " + user.getUsername()  + "\n"  + "\n"
                + "Si aun no abonaste tu plan puede hacerlo AQUÍ:" + "\n"
                + "La activación de la tarjeta mascotin es instantánea en cuanto se efectivizo el pago del plan Anual y todos tus datos se encuentren completos." + "\n"  + "\n"
                + "Si ya abonaste tu plan y tu tarjeta no se encuentra ACTIVA, por favor infórmanos a info@tarjetamascotin.com" + "\n"  + "\n"
                + "Como utilizar su Tarjeta Mascotin?" + "\n"
                + "Ingrese al siguiente link: " + "\n" + "\n"
                + "Contacto Mascotin" + "\n"
                + "info@tarjetamascotin.com" + "\n" + "\n"
                + "Estamos a su disposición, el staff de Tarjeta Mascotin. ");

        javaMailSender.send(mail);
    }

    public void sendNewAsociadoMail(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("facundop@gmail.com");
        mail.setBcc("tarjetamascotin@gmail.com");
        mail.setFrom(user.getUsername());
        mail.setSubject(user.getUsername() + ", Bienvenido a Tarjeta  Mascotin!");
        mail.setText("Muchas gracias por registrarse en Tarjeta Mascotin, un mundo de beneficios para tu mascota." + "\n"
                + "De ahora en adelante su negocio  disfrutara de nuevas experiencias y beneficios para sus actuales y nuevos clientes y  al mismo vera un incremento mensual en sus tickets de venta para su beneficio económico." + "\n"  + "\n"
                + "Usuario: " + user.getUsername()  + "\n"  + "\n"
                + "Si aun no abonaste tu plan puede hacerlo AQUÍ:" + "\n"
                + "En minutos nos comunicaremos telefónicamente con usted para completar el Alta de Registro de Asociado." + "\n"
                + "Nuestro horario de atención telefónico es de 10 a 20hrs, mediante emails las 24 hrs." + "\n"  + "\n"
                + "Como utilizar su Tarjeta Mascotin?" + "\n"
                + "Ingrese al siguiente link: " + "\n" + "\n"
                + "Contacto Mascotin" + "\n"
                + "info@tarjetamascotin.com" + "\n" + "\n"
                + "Estamos a su disposición, el staff de Tarjeta Mascotin. ");

        javaMailSender.send(mail);
    }

    public void sendUserPassword(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("tarjetamascotin@gmail.com");
        mail.setFrom(user.getUsername());
        mail.setSubject(user.getUsername() + " - Recuperación de contraseña en Tarjeta Mascotin.com");
        mail.setText("Estimad@ Mascotin, solicitaste una recuperación de contraseña." + "\n"
                + "Tu nueva contraseña es: " + "password" + "\n" + "\n"
                + "Por favor no compartas esta información."
                + "Estamos a su disposición, el staff de Tarjeta Mascotin. ");

        javaMailSender.send(mail);
    }

}
