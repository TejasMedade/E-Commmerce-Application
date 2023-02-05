/**
 * 
 */
package com.masai.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.payloads.ImageResponse;

/**
 * @author tejas
 *
 */
public interface FileServices {

	InputStream serveImage(String path, String imageName) throws FileNotFoundException;

	ImageResponse addImage(String path, MultipartFile multipartFile) throws IOException, FileTypeNotValidException;

	boolean delete(String filename) throws IOException;

}
