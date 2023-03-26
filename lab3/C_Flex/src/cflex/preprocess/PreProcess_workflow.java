package cflex.preprocess;

import cflex.filesystem.FileIO;

import java.util.List;

public class PreProcess_workflow {
    private final IPreProcess preprocess;
    private final String suffix;
    private String save_path;
    public PreProcess_workflow(IPreProcess preprocess_, String suffix_){
        preprocess = preprocess_;
        suffix = suffix_;
        save_path = "";
    }

    public void execute(String filename){
        System.out.printf("Start PreProcess...%n");
        if(!filename.endsWith(suffix)){
            System.err.printf("Incorrect input file: %s%n", filename);
            return;
        }

        String code = FileIO.readFile(filename);
        if(code == null){
            System.err.println("File read error!");
            return;
        }
        //System.out.println(code);

        save_path = filename.replaceAll(String.format("\\%s$", suffix), "") + String.format(".pp%s", suffix);

        List<String> lines = preprocess.process(code);

        System.out.println(lines);
        for(String line : lines){
            System.out.println(line);
        }

        System.out.printf("Writing to %s...%n", save_path);
        FileIO.writeFile(lines, save_path);
    }

    public String getSave_path(){
        return save_path;
    }
}
