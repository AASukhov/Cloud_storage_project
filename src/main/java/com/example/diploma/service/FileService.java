package com.example.diploma.service;

import com.example.diploma.dto.FileResponseDto;
import com.example.diploma.entity.File;
import com.example.diploma.entity.User;
import com.example.diploma.exception.FileException;
import com.example.diploma.exception.UnauthorizedUserException;
import com.example.diploma.repository.FileRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.security.JwtCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class FileService {

    FileRepository fileRepository;

    UserRepository userRepository;

    JwtCreator creator;

    public FileService (FileRepository fileRepository, UserRepository userRepository, JwtCreator creator) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.creator = creator;
    }

    public void uploadFile(String authToken, String filename, MultipartFile file) {
        final User user = getUser(authToken);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        try {
            File file1 = new File(filename, LocalDateTime.now(), file.getSize(), file.getContentType(), file.getBytes(),user);
            fileRepository.save(file1);
            log.info("User {} upload file {}", user.getLogin(), filename);
        } catch (IOException e) {
            log.error("Upload file error");
            throw new FileException("The problem with uploading");
        }
    }

    public void deleteFile(String authToken, String filename) {
        final User user = getUser(authToken);
        if (user == null) {
            log.error("Delete file error");
            throw new UnauthorizedUserException("Unauthorized user");
        }
        log.info("User {} delete file {}", user.getLogin(), filename);
        fileRepository.removeByUserAndFilename(user, filename);
    }

    public File downloadFile(String authToken, String filename) {
        final User user = getUser(authToken);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        final File file = fileRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            log.error("Download file error");
            throw new FileException("Thw problem with the downloading");
        }
        return file;
    }

    public void editFileName(String authToken, String filename, String newFileName) {
        final User user = getUser(authToken);
        if (user == null) {
            log.error("Edit file error");
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (newFileName != null) {
            fileRepository.editFilenameByUser(user, filename, newFileName);
            log.info("User {} edit file {}", user.getLogin(), filename);
        } else {
            throw new FileException("Problem to correct the name of file");
        }
    }
    public List<FileResponseDto> getAllFiles(String authToken, Integer limit) {
        final User user = getUser(authToken);
        if (user == null) {
            log.error("Get all files error");
            throw new UnauthorizedUserException("Unauthorized error");
        }
        log.info("User {} get all files", user.getLogin());
        return fileRepository.findAllByUser(user, Sort.by("filename")).stream()
                .map(f -> new FileResponseDto(f.getFilename(), f.getSize()))
                .collect(Collectors.toList());
    }
    private User getUser(String authToken) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        final String login = creator.getLoginFromToken(authToken);
        log.info(login);
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UnauthorizedUserException("Unauthorized user"));
    }

    public String isUserExisting(String login) {
        if (userRepository.existsByLogin(login)) {
            return "User is existing";
        } else return "Unknown user";
    }
}
