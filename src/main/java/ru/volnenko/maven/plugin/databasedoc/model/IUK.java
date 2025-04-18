package ru.volnenko.maven.plugin.databasedoc.model;

import lombok.NonNull;

public interface IUK {

    @NonNull
    String getTableName();

    void setTableName(@NonNull String tableName);

    @NonNull
    String getFieldName();

    void setFieldName(@NonNull String fieldName);

}
