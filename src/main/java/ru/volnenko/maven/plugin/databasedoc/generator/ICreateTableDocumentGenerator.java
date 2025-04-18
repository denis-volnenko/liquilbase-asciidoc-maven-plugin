package ru.volnenko.maven.plugin.databasedoc.generator;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.CreateTableDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.model.impl.Root;

import java.util.List;

public interface ICreateTableDocumentGenerator {

    @NonNull
    CreateTableDocumentGenerator serviceName(@NonNull String serviceName);

    @NonNull
    CreateTableDocumentGenerator dataBaseInfo(@NonNull String dataBaseInfo);

    @NonNull
    CreateTableDocumentGenerator roots(@NonNull List<Root> roots);

}
