package com.example.springboottodolist.service.impl;

import com.example.springboottodolist.exception.FileNotFoundException;
import com.example.springboottodolist.exception.FileStorageException;
import com.example.springboottodolist.property.FileStorageProperties;
import com.example.springboottodolist.service.FileStorageService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileFileStorageServiceImpl implements FileStorageService {

  private static final Logger logger = LoggerFactory.getLogger(FileFileStorageServiceImpl.class);

  private final Path fileStorageLocation;

  @Autowired
  public FileFileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation =
        Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    try {
      if (Files.isDirectory(this.fileStorageLocation)) {
        logger.info("Directory {} has exist", this.fileStorageLocation);
      } else {
        Files.createDirectory(this.fileStorageLocation);
        logger.info("Create directory {} success", this.fileStorageLocation);
      }
    } catch (IOException e) {
      throw new FileStorageException(
          "Could not create the directory where the uploaded files will be stored.", e);
    }
  }

  @Override
  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      // Check if the file's name contains invalid characters
      if (fileName.contains("..")) {
        throw new FileStorageException(
            "Sorry! Filename contains invalid path sequence " + fileName);
      }
      // Copy file to the target location (Replacing existing file with the same name)
      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (IOException e) {
      throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
    }
  }

  @Override
  public Resource loadFileAsResource(String fileName) {
    try {
      Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new FileNotFoundException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new FileNotFoundException("File not found " + fileName, ex);
    }
  }
}
