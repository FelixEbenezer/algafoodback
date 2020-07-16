package com.algaworks.algafood.api.exceptionHandler;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	public static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	
	@Autowired
	private MessageSource messageSource;
	
	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
	= "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
			+ "o problema persistir, entre em contato com o administrador do sistema.";

//406 not acceptable
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

// accesso_negado
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(AccessDeniedException ex, WebRequest request) {

	    HttpStatus status = HttpStatus.FORBIDDEN;
	    ProblemaType problemType = ProblemaType.ACESSO_NEGADO;
	    String detail = ex.getMessage();

	    Problema problem = createProblemBuilder(status, problemType, detail);
	            problem.setUserMessage(detail);
	            problem.setUserMessage("Você não possui permissão para executar essa operação.");

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
// bindException
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		// TODO Auto-generated method stub
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
		
	}
	
//MethodArgumentNotValidException
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

	    return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {
		ProblemaType problemType = ProblemaType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	       
	    // BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problema.Field> problemFields = bindingResult.getFieldErrors().stream()
	    		.map(fieldError -> {
	    		
	    		String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
    			
	    		return (new Problema.Field(fieldError.getField(), (message)));
	    		})
	    		.collect(Collectors.toList());
	    
	    Problema problem = createProblemBuilder(status, problemType, detail);
	        problem.setUserMessage(detail);
	        problem.setFields(problemFields);
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
// tYPEmISMATCH 	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemaType problemType = ProblemaType.PARAMETRO_INVALIDO;

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
// RECURSO NAO ENCONTRADO
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemaType problemType = ProblemaType.RECURSO_NAO_ENCONTRADA;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
				ex.getRequestURL());
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
//outras exceptions
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemaType problemType = ProblemaType.ERRO_DO_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		
		// String userMessage = detail; 

		// Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
		// fazendo logging) para mostrar a stacktrace no console
		// Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
		// para você durante, especialmente na fase de desenvolvimento
	//	ex.printStackTrace();
		logger.error(ex.getMessage(), ex);
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
/*	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemaType problemType = ProblemaType.MSG_COMPLEXO;
		String detail = "Por favor verifique o conteudo da tua msg...algo invalido";
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}*/
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		else if(rootCause instanceof PropertyBindingException) {
			return HandlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ProblemaType problemType = ProblemaType.MSG_COMPLEXO;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> HandlePropertyBindingException(PropertyBindingException ex, 
			HttpHeaders headers,HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemaType problemType = ProblemaType.MSG_COMPLEXO;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemaType problemType = ProblemaType.MSG_COMPLEXO;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	


	
	

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemaType problemType = ProblemaType.RECURSO_NAO_ENCONTRADA;
		String detail = ex.getMessage();
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(detail);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
		
		/*HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema pro = new Problema();
		pro.setStatus(status.value());
		pro.setType("https://algafood.com.br/entidade-nao-encontrada");
		pro.setTitle("Entidade Não Encontrada");
		pro.setDetail(ex.getMessage());
		
		return handleExceptionInternal(ex, pro, new HttpHeaders(), 
				HttpStatus.NOT_FOUND, request);*/
		
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(
			EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemaType problemType = ProblemaType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(detail);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
		
			}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemaType problemType = ProblemaType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problema problem = createProblemBuilder(status, problemType, detail);
		problem.setUserMessage(detail);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}	
	
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			Problema p = new Problema();
			p.setTitle(status.getReasonPhrase());
			p.setStatus(status.value());
			p.setTimestamp(LocalDateTime.now());
			p.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
			
			body = p;
		} else if (body instanceof String) {
			Problema p = new Problema();
			p.setTitle((String) body);
			p.setStatus(status.value());
			p.setTimestamp(LocalDateTime.now());
			p.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
			
			body = p;
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problema createProblemBuilder(HttpStatus status,
			ProblemaType problemType, String detail) {
		
		
		Problema pro = new Problema();
		pro.setStatus(status.value());
		pro.setType(problemType.getUri());
		pro.setTitle(problemType.getTitle());
		pro.setDetail(detail);
		pro.setUserMessage(detail);
		pro.setTimestamp(LocalDateTime.now());
		
		return pro;
	}
	
}


/*
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), 
				HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(
			EntidadeEmUsoException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), 
				HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), 
				HttpStatus.BAD_REQUEST, request);
	}	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			Problema p = new Problema();
			p.setDataHora(LocalDateTime.now());
			p.setMsg(status.getReasonPhrase());
			
			body = p;
		} else if (body instanceof String) {
			Problema p = new Problema();
			p.setDataHora(LocalDateTime.now());
			p.setMsg((String) body);
			body = p;
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
}
*/