package com.example.springboottodolist.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboottodolist.service.impl.FileFileStorageServiceImpl;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FileFileStorageServiceImpl fileStorageService;

  @Test
  public void shouldLoadFile() throws Exception {
    String fileName = "test-file.txt";
    Path fileLocation = Paths.get("/uploads").toAbsolutePath().normalize().resolve(fileName);
    UrlResource urlResource = new UrlResource(fileLocation.toUri());

    when(fileStorageService.loadFileAsResource(fileName)).thenReturn(urlResource);

    mockMvc
        .perform(get(String.format("/downloadFile/%s", fileName)))
        .andExpect(status().isOk())
        .andExpect(
            header().string("Content-Disposition", "attachment; filename=\"test-file.txt\""));
  }

  @Test
  public void shouldUploadFile() throws Exception {
    String originalFilename = "testFileName.txt";

    MockMultipartFile multipartFile =
        new MockMultipartFile(
            "file",
            originalFilename,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            "Spring Framework".getBytes());

    when(fileStorageService.storeFile(multipartFile)).thenReturn(originalFilename);

    mockMvc
        .perform(multipart("/uploadFile").file(multipartFile))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.fileName", is(originalFilename)));
  }
}
