package ru.rookonroad.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.rookonroad.service.model.FileModel;

@Repository
public interface FileModelRepo extends ReactiveMongoRepository<FileModel, String> {
}
