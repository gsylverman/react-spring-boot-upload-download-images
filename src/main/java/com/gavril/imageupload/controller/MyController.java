package com.gavril.imageupload.controller;

import com.gavril.imageupload.model.FileProps;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
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
//        System.out.println("http://" + baseURL + "/");

        String[] pathNames;
        File f = new File("src/main/resources/images/");
        pathNames = f.list();

        AtomicInteger i = new AtomicInteger(1);
        Arrays.stream(pathNames).forEach(item -> {
            filesProps.add(
                    new FileProps(i.getAndIncrement(),
                            item.substring(0, item.lastIndexOf('.')),
                            item, "http://" + baseURL + "/upload/api/image/" + item));
        });
        return filesProps;
    }

    @GetMapping(path = "/api/image/{imgName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public UrlResource getImage(@PathVariable("imgName") String imgName) throws IOException {
        Path pathToFile = Path.of("src/main/resources/images/" + imgName);
        UrlResource resource = null;
        try {
            resource = new UrlResource(pathToFile.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }

    @PostMapping(path = "/api/deleteFiles")
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

    @PostMapping(path = "/api/deleteFile/{name}")
    public void deleteImage(@PathVariable("name") String name) {
        File image = new File("src/main/resources/images/" + name);
        image.delete();
    }
}
