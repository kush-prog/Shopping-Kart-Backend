package com.kush.shoppingkart.Service;

import java.util.List;

import com.kush.shoppingkart.dtos.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import com.kush.shoppingkart.model.Image;

public interface ImageService {
	
	Image getImageById(Long Id);
	void deleteImageById(Long Id); 
	List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
	void updateImage(MultipartFile file, Long imageId);
	
}
