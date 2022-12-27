package com.example.diploma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@Setter
public class FileResponseDto {

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("size")
    private Long size;
}
