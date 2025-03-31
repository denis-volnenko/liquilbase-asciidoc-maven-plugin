package ru.volnenko.maven.plugin.databasedoc.generator;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.model.CreateType;

public final class CreateTypeGenerator extends AbstractGenerator {

    @NonNull
    private final CreateTypeBasicGenerator createTypeBasicGenerator = new CreateTypeBasicGenerator();

    @NonNull
    private final ValueWrapperGenerator valueWrapperGenerator = new ValueWrapperGenerator();

    @NonNull
    private CreateType createType = new CreateType();

    @NonNull
    private String serviceName = "";

    @NonNull
    private String dataBaseInfo = "";

    @NonNull
    public CreateTypeGenerator createType(@NonNull final CreateType createType) {
        this.createType = createType;
        return this;
    }

    @NonNull
    public CreateTypeGenerator serviceName(@NonNull final String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @NonNull
    public CreateTypeGenerator dataBaseInfo(@NonNull final String dataBaseInfo) {
        this.dataBaseInfo = dataBaseInfo;
        return this;
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull StringBuilder stringBuilder) {
        createTypeBasicGenerator
                .dataBaseInfo(dataBaseInfo)
                .serviceName(serviceName)
                .createType(createType)
                .append(stringBuilder);
        valueWrapperGenerator
                .valueWrappers(createType.getValues())
                .append(stringBuilder);
        return stringBuilder;
    }

}
