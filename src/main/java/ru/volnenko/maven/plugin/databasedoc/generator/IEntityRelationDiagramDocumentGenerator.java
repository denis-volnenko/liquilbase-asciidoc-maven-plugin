package ru.volnenko.maven.plugin.databasedoc.generator;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.enumerated.ErdType;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.EntityRelationDiagramDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.model.impl.Root;

import java.util.List;

public interface IEntityRelationDiagramDocumentGenerator extends IGenerator {

    @NonNull
    EntityRelationDiagramDocumentGenerator multiDatabase(boolean multiDatabase);

    @NonNull
    EntityRelationDiagramDocumentGenerator erdType(@NonNull ErdType erdType);

    @NonNull
    ErdType erdType();

    @NonNull
    EntityRelationDiagramDocumentGenerator physic();

    @NonNull
    EntityRelationDiagramDocumentGenerator logic();

    @NonNull
    IEntityRelationDiagramDocumentGenerator roots(@NonNull List<Root> roots);

    @NonNull
    IEntityRelationDiagramDocumentGenerator internal();

    @NonNull
    IEntityRelationDiagramDocumentGenerator external();

}
