package com.example.diploma.entity;

import com.example.diploma.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Builder
@Table (name = "Files")
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    private String fileName;

    private LocalDateTime localDateTime;

    private Long size;

    private String type;

    private byte [] content;

    @ManyToOne
    private User user;
}