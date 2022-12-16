package com.example.diploma.repository;

import com.example.diploma.entity.File;
import com.example.diploma.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
    public interface FileRepository extends JpaRepository<File, String> {

    File findByUserAndFilename(User user, String filename);
    void removeByUserAndFilename(User user, String filename);
    List<File> findAllByUser(User user, Sort sort);


    @Modifying
    @Query("update File f set f.filename = :newName where f.filename = :filename and f.user = :user")
    void editFilenameByUser(@Param("user") User user, @Param("filename") String filename, @Param("newName") String newName);
}