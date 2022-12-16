package com.example.diploma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class FileResponseDto {

    private String filename;
    private Long size;
}
