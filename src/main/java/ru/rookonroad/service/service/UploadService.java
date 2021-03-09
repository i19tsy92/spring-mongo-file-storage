package ru.rookonroad.service.service;

import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rookonroad.service.model.FileModel;
import ru.rookonroad.service.repository.FileModelRepo;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class UploadService {

    private final FileModelRepo fileModelRepo;

    private final ReactiveGridFsTemplate fsTemplate;

    public UploadService(FileModelRepo imageRepo, ReactiveGridFsTemplate fsTemplate) {
        this.fileModelRepo = imageRepo;
        this.fsTemplate = fsTemplate;
    }

    public Flux<Void> getImageById(String id, ServerWebExchange exchange) {
        return fsTemplate.findOne(query(where("_id").is(id)))
                .log()
                .flatMap(fsTemplate::getResource)
                .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
    }

    public Mono<FileModel> saveImage(Mono<FilePart> file) {
        FileModel fileModel = new FileModel();
        return file.flatMap(part -> {
            fileModel.setName(part.filename());
            return this.fsTemplate.store(part.content(), part.filename());
        }).map((id) -> {
            fsTemplate.getResource(id.toHexString())
                    .flatMap(ReactiveGridFsResource::getGridFSFile)
                    .doOnNext(fs -> {
                        fileModel.setSize(fs.getChunkSize());
                    }).subscribe();
            fileModel.setHash(id.toHexString());
            fileModelRepo.save(fileModel).subscribe();
            return fileModel;
        });
    }
}
