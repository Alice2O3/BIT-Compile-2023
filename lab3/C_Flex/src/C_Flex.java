import cflex.preprocess.*;
import cflex.scanner.Scanner_C;
import cflex.scanner.Scanner_workflow;

public class C_Flex {
    public static void main(String[] args) {
        System.out.println("Program Start!");
        if(args.length < 1){
            usage();
            return;
        }

        String filename = args[0];

        PreProcess_workflow preprocess_c = new PreProcess_workflow(new PreProcess_C(), ".c");
        Scanner_workflow scanner_c = new Scanner_workflow(new Scanner_C(), ".c");

        preprocess_c.execute(filename);
        scanner_c.execute(preprocess_c.getSave_path());
    }

    public static void usage(){
        System.out.println("USAGE: C_Flex FILE_NAME.xx");
    }
}
