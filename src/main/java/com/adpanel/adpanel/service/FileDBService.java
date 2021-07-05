package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.FileDB;

import java.util.List;

public interface FileDBService {
    void store(FileDB fileDB);
    FileDB getFile(Long id);
    FileDB getLastFile();
    List<FileDB> getAllFiles();
}
