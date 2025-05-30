package ru.volnenko.maven.plugin.databasedoc.util;

import lombok.NonNull;
import ru.volnenko.maven.plugin.databasedoc.model.impl.*;

import java.util.*;

public final class PrimaryKeyUtil {

    @NonNull
    public static Set<PK> pks(final Collection<Root> roots) {
        if (roots == null || roots.isEmpty()) return Collections.emptySet();
        @NonNull final Set<PK> result = new LinkedHashSet<>();
        for (Root root: roots) result.addAll(pks(root));
        return result;
    }

    @NonNull
    public static Set<PK> pks(final Root root) {
        if (root == null) return Collections.emptySet();
        if (root.getDatabaseChangeLog() == null) return Collections.emptySet();
        if (root.getDatabaseChangeLog().isEmpty()) return Collections.emptySet();
        @NonNull final Set<PK> result = new LinkedHashSet<>();
        for (DatabaseChangeLog changeLog: root.getDatabaseChangeLog()) {
            if (changeLog == null) continue;
            final ChangeSet changeSet = changeLog.getChangeSet();
            if (changeSet == null) continue;
            final List<Change> changes = changeSet.getChanges();
            if (changes == null || changes.isEmpty()) continue;
            for (Change change: changes) {
                if (change == null) continue;
                final CreateTable createTable = change.getCreateTable();
                if (createTable == null) continue;
                if (createTable.getColumns() == null) continue;
                for (final ColumnWrapper columnWrapper: createTable.getColumns()) {
                    final Column column = columnWrapper.getColumn();
                    if (column == null) continue;
                    final String name = ColumnUtil.getName(column);
                    if (name == null || name.isEmpty()) continue;
                    if (column.getConstraints() == null) continue;
                    if (column.getConstraints().getPrimaryKey() == null) continue;
                    if (!column.getConstraints().getPrimaryKey()) continue;
                    result.add(new PK(createTable.getTableName(), name));
                }
            }
        }
        return result;
    }

}
