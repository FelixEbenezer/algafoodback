package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.repository.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.repository.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
            	return new SmtpEnvioEmailService();
            default:
                return null;
        }
    }
}    

/*
if(emailProperties.getDestinatario().isEmpty())
{return new SmtpEnvioEmailService();}
else {
	return new DestinatarioSmtpEnvioEmailService();
}*/