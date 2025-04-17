package ru.volnenko.maven.plugin.databasedoc.data;

import com.tngtech.java.junit.dataprovider.DataProvider;
import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.util.MapperUtil;

public class ConstraintUtilDataProvider extends AbstractDataProvider {

    @DataProvider
    public static Object[][] nullableColumn() {
        @NonNull final TestData testData = MapperUtil.parseJsonFromResource(
                "testdata/constraint-util/dataNullableColumn.json",
                TestData.class
        );
        return testData.testCases.stream()
                .map(testCase -> new Object[]{testCase.column, testCase.expectedBoolean})
                .toArray(Object[][]::new);
    }

    @DataProvider
    public static Object[][] notNullColumn() {
        @NonNull final TestData testData = MapperUtil.parseJsonFromResource(
                "testdata/constraint-util/dataNotNullColumn.json",
                TestData.class
        );
        return testData.testCases.stream()
                .map(testCase -> new Object[]{testCase.column, testCase.expectedBoolean})
                .toArray(Object[][]::new);
    }
    
}
