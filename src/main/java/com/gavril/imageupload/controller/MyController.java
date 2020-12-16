package com.gavril.imageupload.controller;

import com.gavril.imageupload.model.FileProps;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/upload")
public class MyController {

    @PostMapping(
            path = "/api",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadImage(@RequestParam("file") MultipartFile file) {
        Path filepath = Paths.get("src/main/resources/images/", file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(path = "/api/multiple")
    public String uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        files.stream().forEach(file -> {
            String name = file.getOriginalFilename();
            Path filepath = FileSystems.getDefault().getPath("src/main/resources/images/", file.getOriginalFilename());
            try (OutputStream outputStream = Files.newOutputStream(filepath)) {
                outputStream.write(file.getBytes());
                System.out.println(name + " saved to disk");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "Uploaded: " + files.size() + " files";
    }

    @GetMapping(path = "/api/images")
    public List<FileProps> getFilesList(HttpServletRequest request) {
        List<FileProps> filesProps = new ArrayList<>();
        String baseURL = request.getHeader("host");
        System.out.println("http://" + baseURL + "/");

        String[] pathNames;
        File f = new File("src/main/resources/images/");
        pathNames = f.list();

        AtomicInteger i = new AtomicInteger(1);
        Arrays.stream(pathNames).forEach(item -> {
            filesProps.add(
                    new FileProps(i.getAndIncrement(),
                            item.substring(0, item.lastIndexOf('.')),
                            "http://" + baseURL + "/upload/api/image/" + item));
        });
        return filesProps;
    }

    @GetMapping(path = "/api/image/{imgName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("imgName") String imgName) throws IOException {

        var imgFile = new ClassPathResource("images/" + imgName);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    @PostMapping(path = "/deleteFiles")
    public void deleteImages() {
        String[] pathNames;
        File f = new File("src/main/resources/images/");
        pathNames = f.list();
        Arrays.stream(pathNames).forEach(fileName -> {
            File file = new File("src/main/resources/images/" + fileName);
            file.delete();
            System.out.print(fileName + "deleted");
        });
    }
}
