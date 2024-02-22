import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JavaParser {

    private final List<JsonlEntry> codeSnippets = new ArrayList<>();
    private String importList = "";
    private String filename = "";

    public JavaParser(String rootPath) throws IOException {
        // Specify the directory containing your Java source files
        Path sourceDirectory = Path.of(rootPath);

        // Parse each Java file in the source directory
        Files.walk(sourceDirectory)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {
                    System.out.println("Parsing " + path.getFileName() + "...");
                    CompilationUnit compilationUnit = null;
                    filename = path.getFileName().toString().replace(".java", "");

                    try {
                        compilationUnit = StaticJavaParser.parse(new FileInputStream(path.toString()));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    // Visit and print method names
                    compilationUnit.accept(new PackageVisitor(), null);
                    compilationUnit.accept(new ImportVisitor(), null);

                    addEntry("What imports are being added to " + filename, importList);

                    compilationUnit.accept(new ClassVisitor(), null);
                });
    }

    public List<JsonlEntry> getCodeSnippets() {
        return codeSnippets;
    }

    private class PackageVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(CompilationUnit compilationUnit, Void arg) {
            if(compilationUnit.getPackageDeclaration().isPresent()){
                addEntry("What package does " + filename + " belong to", "package " + compilationUnit.getPackageDeclaration().get().getName() + ";");
            }

            super.visit(compilationUnit, arg);
        }
    }

    private class ImportVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(ImportDeclaration importDeclaration, Void arg) {
            importList += "import " + importDeclaration.getNameAsString() + ";\n";

            super.visit(importDeclaration, arg);
        }
    }

    private class ClassVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(ClassOrInterfaceDeclaration classDeclaration, Void arg) {
            if(classDeclaration.getAnnotations().isEmpty()){
                addEntry("What is the class declaration for " + filename, "public class " + classDeclaration.getName() + " {\n\n}");
            } else {
                String annotationList = buildAnnotationList(classDeclaration.getAnnotations());

                addEntry("What is the class declaration for " + filename, annotationList + "public class " + classDeclaration.getName() + " {\n\n}");
            }


            classDeclaration.getFields().forEach(field -> {
                String methodAnnotations = buildAnnotationList(field.getAnnotations());

                if(!field.getModifiers().isEmpty()) {
                    if(field.getVariables().get(0).getInitializer().isPresent())
                        addEntry("What is the variable " + field.getVariables().get(0).getName() + " assigned to in " + filename + "?", methodAnnotations + field.getModifiers().get(0) + field.getElementType().asString() + " " + field.getVariables().get(0).getName() + " = " + field.getVariables().get(0).getInitializer().get() + ";");
                    else
                        addEntry("List variables declared in " + filename, methodAnnotations + field.getModifiers().get(0) + field.getElementType().asString() + " " + field.getVariables().get(0).getName() + ";");
                }
            });

            // Visit and print details of each method
            classDeclaration.getMethods().forEach(method -> method.accept(new MethodVisitor(), null));

            super.visit(classDeclaration, arg);
        }
    }

    private class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            String annotations = buildAnnotationList(md.getAnnotations());

            if(md.getBody().isPresent()) {
                addEntry("How does the method " + md.getName() + " work for " + filename + "?",annotations + md.getDeclarationAsString() + "\n" + md.getBody().get().toString());
            }

            super.visit(md, arg);
        }
    }

    private void addEntry(String instruction, String entry){
        codeSnippets.add(new JsonlEntry(instruction,entry));
    }

    private String buildAnnotationList(NodeList<AnnotationExpr> annotations){
        StringBuilder annotationList = new StringBuilder();

        for(AnnotationExpr annotation : annotations){
            annotationList.append("@").append(annotation.getName()).append("\n");
        }

        return annotationList.toString();
    }

}
