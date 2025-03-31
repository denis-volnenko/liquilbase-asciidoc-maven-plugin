package ru.volnenko.maven.plugin.databasedoc;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.CreateTableDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.CreateTypeDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.DocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.generator.impl.EntityRelationDiagramDocumentGenerator;
import ru.volnenko.maven.plugin.databasedoc.model.Root;
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
    @Parameter(property = "entityRelationDiagramInclude")
    public boolean entityRelationDiagramInclude = true;

    @Getter
    @Setter
    @Parameter(property = "outputPath")
    public String outputPath = "./doc";

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
    private final StringBuilder erd = new StringBuilder();

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
    public void execute() throws MojoExecutionException, MojoFailureException {
        documentGenerator
                .serviceName(serviceName)
                .entityRelationDiagramEnabled(entityRelationDiagramEnabled)
                .entityRelationDiagramInclude(entityRelationDiagramInclude)
                .headerSecondEnabled(headerSecondEnabled)
                .headerFirstEnabled(headerFirstEnabled)
                .tableOfContentsEnabled(tableOfContentsEnabled)
                .append(stringBuilder);

        @NonNull final List<Root> roots = rootParser.files(files).parse();
        entityRelationDiagramDocumentGenerator.roots(roots).append(erd);
        createTableDocumentGenerator.roots(roots).append(stringBuilder);
        createTypeDocumentGenerator.roots(roots).append(stringBuilder);

        save();
    }

    @SneakyThrows
    public void save() {
        if (outputPath == null || outputPath.isEmpty()) return;
        File path = new File(outputPath);
        path.mkdirs();

        if (outputFile == null || outputFile.isEmpty()) return;
        {
            File file = new File(path.getAbsolutePath() + "/" + outputFile);
            FileUtils.fileWrite(file, stringBuilder.toString());
        }
        {
            File file = new File(path.getAbsolutePath() + "/" + "erd.puml");
            FileUtils.fileWrite(file, erd.toString());
        }
        if (entityRelationDiagramEnabled) {
            final SourceStringReader reader = new SourceStringReader(erd.toString());
            final FileOutputStream output = new FileOutputStream(new File(path.getAbsolutePath() + "/" + "erd.svg"));
            reader.generateImage(output, new FileFormatOption(FileFormat.SVG, false));
        }
    }

}
