package com.algaworks.algafood.api.exceptionHandler;

public enum ProblemaType {

DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrada", "Recurso não encontrado"),
ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro do URI inválido"),
ERRO_DO_SISTEMA("/erro-sistema", "Erro do sistema"),
ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
MSG_COMPLEXO("/Msg-complexo", "Msg incompreensivel");

private String title;
	private String uri;
	
	ProblemaType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	

}
