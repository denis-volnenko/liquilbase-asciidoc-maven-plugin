package ru.volnenko.maven.plugin.databasedoc.api;

import lombok.NonNull;

public interface IGenerator<M> {

    @NonNull
    String generate(@NonNull M model, @NonNull Integer index);

    void append(@NonNull M model, @NonNull Integer index, @NonNull StringBuilder builder);

}
