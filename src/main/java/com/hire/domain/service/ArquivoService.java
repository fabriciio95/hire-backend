package com.hire.domain.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hire.domain.exception.ArquivoException;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.UsuarioRepository;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ArquivoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Value("${hire.files.image}")
	private String dirImages;
	
	

	public String converterImagemParaMiniatura(String nomeFoto, int height, int width, String formato) {
		String miniatura = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(String.format("%s//%s", dirImages, nomeFoto))));
			BufferedImage resizedImage = Scalr.resize(bufferedImage, 20);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, formato, baos);
			miniatura = String.format("data:image/%s;base64,%s", formato, DatatypeConverter.printBase64Binary(baos.toByteArray()));
		} catch(Exception e) { 
			throw new ArquivoException(e.getMessage());
		}
		return miniatura;
	}
	
	public void copy(InputStream in, String fileName, String outputDir) throws IOException {
		Files.copy(in, Paths.get(outputDir, fileName), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public String getImage(Path path, String formato) {
		byte[] file;
		String fileBase64;
		try {
			file = Files.readAllBytes(path);
		    fileBase64 = new String(Base64.encodeBase64(file), "UTF-8");
		} catch (IOException e) {
			throw new ArquivoException(e.getMessage());
		}
		return String.format("data:image/%s;base64,%s", formato, fileBase64);
	}
	
	public void salvarImagem(Usuario usuario) { 
		usuario.definirNomeFoto();
		ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(usuario.getFotoBase64()));
		try {
			copy(bais, usuario.getNomeFoto(), dirImages);
			Thumbnails.of(new File(String.format("%s//%s", dirImages, usuario.getNomeFoto())))
			.size(240, 240)
			.toFile(new File(String.format("%s//%s", dirImages, usuario.montarNomeFotoMiniatura())));
		} catch (IOException e) {
			throw new ArquivoException(e.getMessage());
		}
		usuarioRepository.save(usuario);
	}
}
