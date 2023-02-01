/**
 * 
 */
package com.masai.servicesImplementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.payloads.ImageResponse;
import com.masai.services.FileServices;

/**
 * @author tejas
 *
 */

@Service
public class FileServicesImplementation implements FileServices {

	@Value("${project.image}")
	private String path;

	@Override
	public ImageResponse addImage(String path, MultipartFile multipartFile)
			throws IOException, FileTypeNotValidException {

		String contentType = multipartFile.getContentType();

		if (contentType == null) {
			throw new IllegalArgumentException("Products Images List Should Not be Empty");
		}

		if ((contentType.contains("jpeg") || contentType.contains("jpg") || contentType.contains("png"))) {

			// FileName
			String name = multipartFile.getOriginalFilename();

			// Generating Random File Name for The Image
			String randomId = UUID.randomUUID().toString();
			String filename = randomId.concat(name.substring(name.lastIndexOf(".")));

			// FullPath
			String filePath = path + File.separator + filename;

			// Create Folder if Not Created
			File folder = new File(path);

			if (!folder.exists()) {
				folder.mkdir();
			}

			// File Copy
			Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

			return new ImageResponse(LocalDateTime.now(), filename, filePath, "Image Updated Successfully !", true);

		} else {
			throw new FileTypeNotValidException("Image", contentType);
		}
	}

	@Override
	public InputStream serveImage(String path, String imageName) throws FileNotFoundException {

		String fullPath = path + File.separator + imageName;

		InputStream is = new FileInputStream(fullPath);

		return is;

	}

	@Override
	public boolean delete(String filename) throws IOException {

			Path file = Paths.get(path + File.separator + filename);

			return Files.deleteIfExists(file);

		
	}

}
