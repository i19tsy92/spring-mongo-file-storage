package ru.rookonroad.service.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.rookonroad.service.model.FileModel;
import ru.rookonroad.service.repository.FileModelRepo;

import java.util.Map;

@Service
public class FileModelService {

    private final FileModelRepo fileModelRepo;

    public FileModelService(FileModelRepo repo) {
        this.fileModelRepo = repo;
    }

    public Flux<FileModel> getAllFiles(Map<String, String> filter) {
        return fileModelRepo.findAll();
    }
}
