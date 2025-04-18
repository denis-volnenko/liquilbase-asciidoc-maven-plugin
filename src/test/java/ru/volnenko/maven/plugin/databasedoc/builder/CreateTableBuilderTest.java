package ru.volnenko.maven.plugin.databasedoc.builder;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import lombok.NonNull;
import org.junit.Assert;
import org.junit.Test;
import ru.volnenko.maven.plugin.databasedoc.builder.impl.CreateTableBuilder;
import ru.volnenko.maven.plugin.databasedoc.model.impl.CreateTable;

@Feature("Builder")
public class CreateTableBuilderTest extends AbstractBuilderTest {

    @NonNull
    private final CreateTableBuilder tableBuilder = createTableBuilder();

    @NonNull
    private final CreateTable table = getFirstTable(tableBuilder);

    @Test
    @DisplayName("Контракт CreateTableBuilder")
    @Description("Проверка контракта класса CreateTableBuilder на null-значения и" +
                 "корректности установки значений")
    public void test() {
        Assert.assertNotNull(tableBuilder.root());
        Assert.assertNotNull(tableBuilder.change());
        Assert.assertNotNull(tableBuilder.column());
        Assert.assertNotNull(tableBuilder.createTable());

        Assert.assertNotNull(tableBuilder.tableName(TABLE_NAME));
        Assert.assertNotNull(tableBuilder.catalogName(CATALOG_NAME));
        Assert.assertNotNull(tableBuilder.tablespace(TABLESPACE));
        Assert.assertNotNull(tableBuilder.remarks(REMARKS));

        Assert.assertEquals(EXPECTED_TABLE_NAME, table.getTableName());
        Assert.assertEquals(EXPECTED_CATALOG_NAME, table.getCatalogName());
        Assert.assertEquals(EXPECTED_TABLESPACE, table.getTablespace());
        Assert.assertEquals(EXPECTED_REMARKS, table.getRemarks());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Контракт CreateTableBuilder")
    @Description("Проверка контракта класса CreateTableBuilder на NullPointerException")
    public void testTableNameNPE() {
        tableBuilder.tableName(null);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Контракт CreateTableBuilder")
    @Description("Проверка контракта класса CreateTableBuilder на NullPointerException")
    public void testCatalogNameNPE() {
        tableBuilder.catalogName(null);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Контракт CreateTableBuilder")
    @Description("Проверка контракта класса CreateTableBuilder на NullPointerException")
    public void testTablespaceNPE() {
        tableBuilder.tablespace(null);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Контракт CreateTableBuilder")
    @Description("Проверка контракта класса CreateTableBuilder на NullPointerException")
    public void testRemarksNPE() {
        tableBuilder.remarks(null);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Конструктор CreateTableBuilder")
    @Description("Проверка конструктора класса CreateTableBuilder на NullPointerException")
    public void testConstructorNPE() {
        new CreateTableBuilder(null);
    }

}
