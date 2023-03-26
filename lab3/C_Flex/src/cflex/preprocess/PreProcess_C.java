package cflex.preprocess;

import java.util.ArrayList;
import java.util.List;

public class PreProcess_C implements IPreProcess{
    public List<String> process(String code) {
        String code_processed = code.replaceAll("/\\*[\\s\\S]*\\*/", "");

        String[] lines = code_processed.split("\n");
        List<String> ret = new ArrayList<>();
        for(String line : lines){
            String line_processed = process_line(line);
            if(!line_processed.isEmpty()){
                ret.add(line_processed + " ");
            }
        }
        return ret;
    }

    private String process_line(String line){
        return line.replaceAll("\\t","    ")
                .replaceAll("//.*|#.*", "")
                .replaceAll(" +", " ")
                .trim();
    }
}
