package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

	//InputStream recuperar(String nomeArquivo);
	
	FotoRecuperada recuperar(String nomeArquivo);
	
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
		private String contentType;
		
					public String getContentType() {
						return contentType;
					}
					public void setContentType(String contentType) {
						this.contentType = contentType;
					}
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
	
	class FotoRecuperada {
		
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}
		
		public boolean temInputStream() {
			return inputStream != null;
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		
		
	}
	
}