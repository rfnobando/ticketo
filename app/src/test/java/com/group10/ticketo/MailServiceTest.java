package com.group10.ticketo;

import com.group10.ticketo.services.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// para el test no levantar la bd (que no esta configurada) se usa el @EnableAutoConfiguration
/*@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})*/
public class MailServiceTest {

    @Autowired
    private IMailService mailService;

    @Test
    public void testEnviarCorreo() {
        mailService.sendMail(
                "ticketo.unla@gmail.com",
                "alanleonelmaciel@gmail.com",
                "Prueba desde Spring Boot",
                "Este es un correo de prueba enviado exitosamente."
        );
    }
}
