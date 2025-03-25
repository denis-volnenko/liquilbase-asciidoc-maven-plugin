package ru.volnenko.maven.plugin.databasedoc.builder;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.api.IDatabaseChangeLogBuilder;
import ru.volnenko.maven.plugin.databasedoc.model.Root;

public final class DatabaseChangeLogBuilder implements IDatabaseChangeLogBuilder {

    @NonNull
    private final RootBuilder rootBuilder;

    public DatabaseChangeLogBuilder(@NonNull final RootBuilder rootBuilder) {
        this.rootBuilder = rootBuilder;
    }

    @NonNull
    @Override
    public Root root() {
        return rootBuilder.root();
    }

    @NonNull
    @Override
    public ChangeSetBuilder changeSet() {
        return new ChangeSetBuilder(this);
    }

}
