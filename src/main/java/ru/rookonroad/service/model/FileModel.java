package ru.rookonroad.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.rookonroad.service.utils.MimeType;

@Getter
@Setter
@Document("fileModel")
@NoArgsConstructor
public class FileModel {

    @Id
    private String hash;

    private String name;

    private Integer size;

    private MimeType type;

}
