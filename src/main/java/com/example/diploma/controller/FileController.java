package com.example.diploma.controller;


import com.example.diploma.dto.FileDto;
import com.example.diploma.dto.FileResponseDto;
import com.example.diploma.entity.File;
import com.example.diploma.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {

    private FileService service;

    @PostMapping()
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename, MultipartFile file) {
        service.uploadFile(authToken, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename) {
        service.deleteFile(authToken, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> downloadFile(@RequestHeader("auth-token") String authToken,
                                               @RequestParam("filename") String filename) {
        File file = service.downloadFile(authToken, filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getContent());
    }

    @PutMapping()
    public ResponseEntity<?> editFileName(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename,
                                          @RequestBody FileDto file) {
        service.editFileName(authToken, filename, file.getFilename());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    List<FileResponseDto> getAllFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") Integer limit) {
        return service.getAllFiles(authToken);
    }
}
