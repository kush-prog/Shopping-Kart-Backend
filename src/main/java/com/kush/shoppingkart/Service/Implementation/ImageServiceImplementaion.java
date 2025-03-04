package com.kush.shoppingkart.Service.Implementation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import com.kush.shoppingkart.exceptions.ResourceNotFoundException;
import org.hibernate.annotations.processing.SQL;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kush.shoppingkart.Service.ImageService;
import com.kush.shoppingkart.Service.ProductService;
import com.kush.shoppingkart.dtos.ImageDto;
import com.kush.shoppingkart.model.Image;
import com.kush.shoppingkart.model.Product;
import com.kush.shoppingkart.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImplementaion implements ImageService{
	
	private ImageRepository imageRepository;
	private ProductService productService;

	@Override
	public Image getImageById(Long Id) {
		
		return imageRepository.findById(Id).
				 orElseThrow(() -> new ResourceNotFoundException("No image found with id: "+ Id));
	
	}

	@Override
	public void deleteImageById(Long Id) {

		imageRepository.findById(Id).ifPresentOrElse(imageRepository::delete, () ->{
			throw new ResourceNotFoundException("No image fount with id: "+ Id);
		});
		
	}

	@Override
	public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);
		List<ImageDto> savedImageDto = new ArrayList<>();
		for(MultipartFile file : files) {
			
			try {
				
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);

				String buildDownloadUrl = "/api/images/image/download";
				String downloadUrl = buildDownloadUrl +image.getId();
				image.setDownloadUrl(downloadUrl);
				imageRepository.save(image);
				Image savedImage = imageRepository.save(image);

				savedImage.setDownloadUrl(buildDownloadUrl +image.getId());
				imageRepository.save(savedImage);

				ImageDto imagedto = new ImageDto();
				imagedto.setImageId(savedImage.getId());
				imagedto.setImageName(savedImage.getFileName());
				imagedto.setDownloadUrl(savedImage.getDownloadUrl());
				savedImageDto.add(imagedto);
				
			}catch(IOException | SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

}
