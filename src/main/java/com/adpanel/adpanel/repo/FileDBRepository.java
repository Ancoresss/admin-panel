package com.adpanel.adpanel.repo;

import com.adpanel.adpanel.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
    FileDB findTopByOrderByIdDesc();
}
