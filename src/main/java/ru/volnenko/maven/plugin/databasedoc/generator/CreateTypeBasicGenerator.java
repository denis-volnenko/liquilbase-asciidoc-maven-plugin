package ru.volnenko.maven.plugin.databasedoc.generator;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.api.ICreateTypeBasicGenerator;
import ru.volnenko.maven.plugin.databasedoc.model.CreateType;
import ru.volnenko.maven.plugin.databasedoc.util.StringUtil;

public final class CreateTypeBasicGenerator extends AbstractGenerator implements ICreateTypeBasicGenerator {

    @NonNull
    private CreateType createType = new CreateType();

    @NonNull
    private String serviceName = "";

    @NonNull
    private String dataBaseInfo = "";

    @NonNull
    public CreateTypeBasicGenerator createType(@NonNull final CreateType createType) {
        this.createType = createType;
        return this;
    }

    @NonNull
    public CreateTypeBasicGenerator serviceName(@NonNull final String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @NonNull
    public CreateTypeBasicGenerator dataBaseInfo(@NonNull final String dataBaseInfo) {
        this.dataBaseInfo = dataBaseInfo;
        return this;
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull StringBuilder stringBuilder) {
        stringBuilder.append("=== Перечисление \"" + StringUtil.format(createType.getTypeName()) + "\"\n");
        stringBuilder.append("==== Общие сведения\n");
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"20,80\"]\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Физ. название*:\n");
        stringBuilder.append("|" + StringUtil.format(createType.getTypeName()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Лог. название*:\n");
        stringBuilder.append("|" + StringUtil.format(createType.getRemarks()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Сервис*:\n");
        stringBuilder.append("|" + StringUtil.format(serviceName) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*База данных*:\n");
        stringBuilder.append("|" + StringUtil.format(createType.getCatalogName()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Доп. сведения*:\n");
        stringBuilder.append("|" + StringUtil.format(dataBaseInfo) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Схема*:\n");
        stringBuilder.append("|" + StringUtil.format(createType.getSchemaName()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        return stringBuilder;
    }

}
