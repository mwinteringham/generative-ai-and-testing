import java.io.IOException;
import java.util.List;

public class Parser {

//    'yml',
//    'md',
//    'json',
//    'js',
//    'xml',
//    'properties',
//    'txt',
//    'html',
//    'scss',
//    'snap',
//    'sql',
//    'yaml',
//    'cmd',
//    'sh',
//    'conf'

    public static void main(String[] args) throws IOException {
        JavaParser parser = new JavaParser("/Users/mark/Desktop/restful-booker-platform-trunk/");

        List<JsonlEntry> javaCodeSnippets = parser.getCodeSnippets();

        JsonlExporter.exportToJsonL(javaCodeSnippets, "/Users/mark/Desktop/parser-test/output.jsonl");
    }

}
