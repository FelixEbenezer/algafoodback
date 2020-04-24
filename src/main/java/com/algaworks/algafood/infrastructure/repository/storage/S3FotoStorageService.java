package com.algaworks.algafood.infrastructure.repository.storage;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.exception.StorageException;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

// @Service
public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		FotoRecuperada fotoRecuperada = new FotoRecuperada();
		fotoRecuperada.setUrl(url.toString());
		
		return fotoRecuperada;
	}
	

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeAquivo());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			
			var deleteObjectRequest = new DeleteObjectRequest(
	                storageProperties.getS3().getBucket(), caminhoArquivo);

	        amazonS3.deleteObject(deleteObjectRequest);
	        
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

}