package ru.volnenko.maven.plugin.databasedoc.data;

import com.tngtech.java.junit.dataprovider.DataProvider;
import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.util.MapperUtil;

import java.util.stream.Stream;

public class UniqueKeyUtilDataProvider extends AbstractDataProvider {

    @DataProvider
    public static Object[][] ukMethodConstraint() {
        @NonNull final TestData testData = MapperUtil.parseJsonFromResource(
                "testdata/uniquekeyutil/dataUkMethodConstraint.json", TestData.class
        );
        return Stream.concat(
                        testData.validTestCases.stream(),
                        testData.invalidTestCases.stream()
                )
                .map(testCase -> new Object[]{testCase.constraintUk, testCase.expectedUk})
                .toArray(Object[][]::new);
    }

    @DataProvider
    public static Object[][] enabledMethodColumn() {
        @NonNull final TestData testData = MapperUtil.parseJsonFromResource(
                "testdata/uniquekeyutil/dataEnabledMethodColumn.json", TestData.class
        );
        return Stream.concat(
                        testData.validTestCases.stream(),
                        testData.invalidTestCases.stream()
                )
                .map(testCase -> new Object[]{testCase.column, testCase.expectedBoolean})
                .toArray(Object[][]::new);
    }

    @DataProvider
    public static Object[][] ukMethodCreateTable() {
        @NonNull final TestData testData = MapperUtil.parseJsonFromResource(
                "testdata/uniquekeyutil/dataUkMethodCreateTable.json", TestData.class
        );
        return testData.testCases.stream()
                .map(testCase -> new Object[]{testCase.createTable, testCase.expectedUks})
                .toArray(Object[][]::new);
    }

    @DataProvider
    public static Object[][] uksMethodRoots() {
        @NonNull final TestData testData = MapperUtil.parseJsonFromResource(
                "testdata/uniquekeyutil/dataUksMethodRoots.json", TestData.class
        );
        return testData.testCases.stream()
                .map(testCase -> new Object[]{testCase.roots, testCase.expectedUks})
                .toArray(Object[][]::new);
    }


}
