package com.algaworks.algafood.infrastructure.repository.email;

//import org.apache.log4j.Logger;
//import org.apache.logging.log4j.Logger;

import java.util.logging.Logger;

public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
    	
    	// Create a Logger 
    	//Logger log = Logger.getLogger("");
    	Logger log = Logger.getLogger(FakeEnvioEmailService.class.getName());
        
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}"+mensagem.getDestinatarios()+corpo);
     //   log.info(mensagem.getDestinatarios());
      //  log.info(corpo);
        
    }
}     