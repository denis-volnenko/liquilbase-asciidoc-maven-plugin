package ru.volnenko.maven.plugin.databasedoc;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import ru.volnenko.maven.plugin.databasedoc.enumerated.ErdType;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.CreateTableDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.CreateTypeDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.DocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.EntityRelationDiagramDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.model.impl.Root;
import ru.volnenko.maven.plugin.databasedoc.parser.RootParser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public final class Generator extends AbstractMojo {

    @Getter
    @Setter
    @Parameter(property = "serviceName")
    public String serviceName = "Сервис";

    @Getter
    @Setter
    @Parameter(property = "dataBaseInfo")
    public String dataBaseInfo = "";

    @Getter
    @Setter
    @Parameter(property = "headerFirstEnabled")
    public boolean headerFirstEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "headerSecondEnabled")
    public boolean headerSecondEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "tableOfContentsEnabled")
    public boolean tableOfContentsEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "tableOfContentsEnabled")
    public boolean entityRelationDiagramEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "entityRelationDiagramPhysicEnabled")
    public boolean entityRelationDiagramPhysicEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "entityRelationDiagramLogicEnabled")
    public boolean entityRelationDiagramLogicEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "entityRelationDiagramInclude")
    public boolean entityRelationDiagramInclude = true;

    @Getter
    @Setter
    @Parameter(property = "entityRelationDiagramInclude")
    public boolean entityRelationDiagramMultiDatabase = false;

    @Getter
    @Setter
    @Parameter(property = "outputPath")
    public String outputPath = "./doc";

    @Getter
    @Setter
    @Parameter(property = "outputJsonFile")
    public String outputJsonFile = "scheme.json";

    @Getter
    @Setter
    @Parameter(property = "outputJsonFileEnabled")
    public Boolean outputJsonFileEnabled = false;

    @Getter
    @Setter
    @Parameter(property = "outputYamlFile")
    public String outputYamlFile = "scheme.yaml";

    @Getter
    @Setter
    @Parameter(property = "outputYamlFileEnabled")
    public Boolean outputYamlFileEnabled = false;

    @Getter
    @Setter
    @Parameter(property = "outputFile")
    public String outputFile = "index.adoc";

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Getter
    @Setter
    @Parameter(property = "files")
    private List<String> files = new ArrayList<>();

    @NonNull
    private final StringBuilder stringBuilder = new StringBuilder();

    @NonNull
    private final DocumentGenerator documentGenerator = new DocumentGenerator();

    @NonNull
    private EntityRelationDiagramDocumentGenerator entityRelationDiagramDocumentGenerator = new EntityRelationDiagramDocumentGenerator();

    @NonNull
    private CreateTypeDocumentGenerator createTypeDocumentGenerator = new CreateTypeDocumentGenerator();

    @NonNull
    private CreateTableDocumentGenerator createTableDocumentGenerator = new CreateTableDocumentGenerator();

    @NonNull
    private final RootParser rootParser = new RootParser();

    @SneakyThrows
    public void execute() {
        documentGenerator
                .serviceName(serviceName)
                .entityRelationDiagramEnabled(entityRelationDiagramEnabled)
                .entityRelationDiagramInclude(entityRelationDiagramInclude)
                .headerSecondEnabled(headerSecondEnabled)
                .headerFirstEnabled(headerFirstEnabled)
                .tableOfContentsEnabled(tableOfContentsEnabled)
                .append(stringBuilder);

        @NonNull final List<Root> roots = rootParser.files(files).parse();

        createTableDocumentGenerator.serviceName(serviceName).dataBaseInfo(dataBaseInfo).roots(roots).append(stringBuilder);
        createTypeDocumentGenerator.serviceName(serviceName).dataBaseInfo(dataBaseInfo).roots(roots).append(stringBuilder);

        save(roots);
    }

    @SneakyThrows
    public void save(@NonNull final List<Root> roots) {
        if (outputPath == null || outputPath.isEmpty()) return;
        if (outputFile == null || outputFile.isEmpty()) return;
        @NonNull final File path = new File(outputPath);
        initOutputPath(path)
                .saveDatabaseYAML(path)
                .saveDatabaseJSON(path)
                .saveEntityRelationDiagramLogic(roots, path)
                .saveEntityRelationDiagramPhysic(roots, path)
                .saveEntityRelationDiagramADOC(path);
    }

    @NonNull
    private Generator saveEntityRelationDiagramPhysic(@NonNull final List<Root> roots, @NonNull final File path) {
        if (!entityRelationDiagramEnabled) return this;
        if (!entityRelationDiagramPhysicEnabled) return this;
        return saveEntityRelationDiagram(roots, path, ErdType.PHYSIC, "erd_physic");
    }

    @NonNull
    private Generator saveEntityRelationDiagramLogic(@NonNull final List<Root> roots, @NonNull final File path) {
        if (!entityRelationDiagramEnabled) return this;
        if (!entityRelationDiagramLogicEnabled) return this;
        return saveEntityRelationDiagram(roots, path, ErdType.LOGIC, "erd_logic");
    }

    @NonNull
    private Generator saveEntityRelationDiagram(
            @NonNull final List<Root> roots,
            @NonNull final File path,
            @NonNull final ErdType erdType,
            @NonNull final String filename
    ) {
        @NonNull final StringBuilder erdInternal = entityRelationDiagramDocumentGenerator
                .erdType(erdType).multiDatabase(entityRelationDiagramMultiDatabase).internal().roots(roots).append(new StringBuilder());
        @NonNull final StringBuilder erdExternal = entityRelationDiagramDocumentGenerator
                .erdType(erdType).multiDatabase(entityRelationDiagramMultiDatabase).external().roots(roots).append(new StringBuilder());
        return this
                .saveEntityRelationDiagramPUML(path, erdInternal, filename)
                .saveEntityRelationDiagramSVG(path, erdInternal, filename)
                .saveEntityRelationDiagramPUML(path, erdExternal, filename);
    }

    @NonNull
    private Generator initOutputPath(@NonNull final File path) {
        path.mkdirs();
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveEntityRelationDiagramADOC(@NonNull final File path) {
        if (outputFile.isEmpty()) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputFile);
        FileUtils.fileWrite(file, stringBuilder.toString());
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveDatabaseYAML(@NonNull final File path) {
        if (!outputYamlFileEnabled) return this;
        if (outputYamlFile.isEmpty()) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputYamlFile);
        FileUtils.fileWrite(file, rootParser.yaml());
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveDatabaseJSON(@NonNull final File path) {
        if (!outputJsonFileEnabled) return this;
        if (outputJsonFile.isEmpty()) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputJsonFile);
        FileUtils.fileWrite(file, rootParser.json());
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveEntityRelationDiagramPUML(
            @NonNull final File path,
            @NonNull final StringBuilder stringBuilder,
            @NonNull final String filename
    ) {
        if (!entityRelationDiagramEnabled) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + filename + ".puml");
        FileUtils.fileWrite(file, stringBuilder.toString());
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveEntityRelationDiagramSVG(
            @NonNull final File path,
            @NonNull final StringBuilder stringBuilder,
            @NonNull final String filename
    ) {
        if (!entityRelationDiagramEnabled) return this;
        @NonNull final SourceStringReader reader = new SourceStringReader(stringBuilder.toString());
        @NonNull final FileOutputStream output = new FileOutputStream(new File(path.getAbsolutePath() + "/" + filename + ".svg"));
        reader.generateImage(output, new FileFormatOption(FileFormat.SVG, false));
        return this;
    }

}
