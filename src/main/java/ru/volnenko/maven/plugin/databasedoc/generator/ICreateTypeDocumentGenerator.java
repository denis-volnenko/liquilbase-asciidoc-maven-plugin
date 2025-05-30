package ru.volnenko.maven.plugin.databasedoc.generator;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.model.impl.Root;

import java.util.List;

public interface ICreateTypeDocumentGenerator extends IGenerator {

    @NonNull
    ICreateTypeDocumentGenerator serviceName(@NonNull String serviceName);

    @NonNull
    ICreateTypeDocumentGenerator dataBaseInfo(@NonNull String dataBaseInfo);

    @NonNull
    ICreateTypeDocumentGenerator roots(@NonNull List<Root> roots);

}
