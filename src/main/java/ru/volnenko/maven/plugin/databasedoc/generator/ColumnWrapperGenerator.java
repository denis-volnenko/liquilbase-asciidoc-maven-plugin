package ru.volnenko.maven.plugin.databasedoc.generator;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.model.ColumnWrapper;

import java.util.Collections;
import java.util.List;

public final class ColumnWrapperGenerator extends AbstractGenerator {

    @NonNull
    private ColumnGenerator columnGenerator = new ColumnGenerator();

    @NonNull
    private Integer index = 1;

    @NonNull
    private List<ColumnWrapper> columnWrappers = Collections.emptyList();

    @NonNull
    public ColumnWrapperGenerator stringBuilder(@NonNull final StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
        return this;
    }

    @NonNull
    public ColumnWrapperGenerator index(@NonNull final Integer index) {
        this.index = index;
        return this;
    }

    @NonNull
    public ColumnWrapperGenerator columnWrappers(@NonNull final List<ColumnWrapper> columnWrappers) {
        this.columnWrappers = columnWrappers;
        return this;
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull StringBuilder stringBuilder) {
        stringBuilder.append("==== Описание полей\n");
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,20,20,20,5,5,5,5,5,10\"]\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("|*Физ. название*\n");
        stringBuilder.append("|*Тип*\n");
        stringBuilder.append("|*Лог. название*\n");
        stringBuilder.append("^|*PK*\n");
        stringBuilder.append("^|*UK*\n");
        stringBuilder.append("^|*FK*\n");
        stringBuilder.append("^|*AI*\n");
        stringBuilder.append("^|*NN*\n");
        stringBuilder.append("|*DEFAULT*\n");
        stringBuilder.append("\n");
        int index = 1;
        for (final ColumnWrapper columnWrapper : columnWrappers) {
            columnGenerator
                    .index(index)
                    .column(columnWrapper.getColumn())
                    .append(stringBuilder);
            index++;
        }
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        return stringBuilder;
    }

}
