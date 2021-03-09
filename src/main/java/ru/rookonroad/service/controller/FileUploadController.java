package ru.rookonroad.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rookonroad.service.model.FileModel;
import ru.rookonroad.service.service.FileModelService;
import ru.rookonroad.service.service.UploadService;

import java.util.Map;

@RestController
public class FileUploadController {

    private final UploadService fileService;

    private final FileModelService modelService;

    public FileUploadController(UploadService service, FileModelService modelService) {
        this.fileService = service;
        this.modelService = modelService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<Void> downloadImage(@PathVariable("id") String imageId, ServerWebExchange exchange) {
        return fileService.getImageById(imageId, exchange);
    }

    @PostMapping
    public Mono<ResponseEntity> upload(@RequestPart Mono<FilePart> file) {
        return Mono.just(ResponseEntity.ok(fileService.saveImage(file)));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<FileModel> getAllFiles(Map<String, String> filter) {
        return modelService.getAllFiles(filter);
    }
}
