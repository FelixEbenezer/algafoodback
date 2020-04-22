package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

InputStream recuperar(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if (nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}


	class NovaFoto {
		
		private String nomeAquivo;
		private InputStream inputStream;
		
		public String getNomeAquivo() {
			return nomeAquivo;
		}
		public void setNomeAquivo(String nomeAquivo) {
			this.nomeAquivo = nomeAquivo;
		}
		public InputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		
		
		
	}
	
}