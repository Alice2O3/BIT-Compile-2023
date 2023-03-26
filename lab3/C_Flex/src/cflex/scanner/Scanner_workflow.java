package cflex.scanner;

import cflex.filesystem.FileIO;

import java.util.List;

public class Scanner_workflow {
    private final IScanner scanner;
    private final String suffix;
    private String savepath;
    public Scanner_workflow(IScanner scanner_, String suffix_){
        scanner = scanner_;
        suffix = suffix_;
        savepath = "";
    }

    public void execute(String filename){
        System.out.printf("Start Scan...%n");
        String pp_suffix = String.format(".pp%s", suffix);
        if(!filename.endsWith(pp_suffix)){
            System.err.printf("Incorrect input file: %s%n", filename);
            return;
        }

        String code = FileIO.readFile(filename);
        if(code == null){
            System.err.println("File read error!");
            return;
        }
        System.out.println(code);

        savepath = filename.replaceAll(String.format("\\%s$", pp_suffix), "") + ".tokens";

        List<String> tokens = scanner.scan(code);
        System.out.println(tokens);
        for(String line : tokens){
            System.out.println(line);
        }

        System.out.printf("Writing to %s...%n", savepath);
        FileIO.writeFile(tokens, savepath);
    }
}
