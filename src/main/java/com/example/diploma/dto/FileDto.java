package com.example.diploma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Getter
@Setter
public class FileDto {
    private String filename;
    private Long size;
}
