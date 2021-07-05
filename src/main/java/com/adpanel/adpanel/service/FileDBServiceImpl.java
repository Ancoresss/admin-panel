package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.FileDB;
import com.adpanel.adpanel.repo.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileDBServiceImpl implements FileDBService {
    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public void store(FileDB fileDB) {
        fileDBRepository.save(fileDB);
    }

    @Override
    public FileDB getFile(Long id) {
        return fileDBRepository.getById(id);
    }

    @Override
    public FileDB getLastFile() {
        return fileDBRepository.findTopByOrderByIdDesc();
    }

    @Override
    public List<FileDB> getAllFiles() {
        return fileDBRepository.findAll();
    }
}
