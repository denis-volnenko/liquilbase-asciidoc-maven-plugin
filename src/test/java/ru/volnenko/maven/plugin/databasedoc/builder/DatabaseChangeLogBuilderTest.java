package ru.volnenko.maven.plugin.databasedoc.builder;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.NonNull;
import org.junit.Assert;
import org.junit.Test;
import ru.volnenko.maven.plugin.databasedoc.builder.impl.DatabaseChangeLogBuilder;
import ru.volnenko.maven.plugin.databasedoc.builder.impl.ForeignKeyBuilder;

public class DatabaseChangeLogBuilderTest extends AbstractBuilderTest {

    @NonNull
    private final DatabaseChangeLogBuilder databaseChangeLogBuilder = changeLogBuilder();

    @Test
    @DisplayName("Контракт DatabaseChangeLogBuilder")
    @Description("Проверка контракта класса DatabaseChangeLogBuilder на null-значения")
    public void test() {
        Assert.assertNotNull(databaseChangeLogBuilder.root());
        Assert.assertNotNull(databaseChangeLogBuilder.changeSet());
        Assert.assertNotNull(databaseChangeLogBuilder);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNPE() {
        new DatabaseChangeLogBuilder(null);
    }

}
