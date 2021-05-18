/* *****************************************************************************
 *  Name:    Luchitha Pasqual
 *  UowID:   w1790040
 *  iitID:   20191236
 *
 *  Description:  Dinic's Algorithm to find the maximum flow of a flow network
 *                with a parser to input text files.
 **************************************************************************** */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    int NOF_Nodes;
    ArrayList<String> inputs = new ArrayList<>();
    ArrayList<Integer> from = new ArrayList<>();
    ArrayList<Integer> to = new ArrayList<>();
    ArrayList<Integer> capacity = new ArrayList<>();


    public int getNOF_Nodes() {
        return NOF_Nodes;
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public ArrayList<Integer> getFrom() {
        return from;
    }

    public ArrayList<Integer> getTo() {
        return to;
    }

    public ArrayList<Integer> getCapacity() {
        return capacity;
    }

    public void run(String filePath){
        File myObj = new File(filePath);
        Scanner myReader;
        {
            try {
                myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    inputs.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
                System.out.println("file not found enter the file path and try again");
            }
        }

        NOF_Nodes = Integer.parseInt(inputs.get(0).trim());

        for(int i=1; i<=inputs.size()-1; i++){
            String[] test = inputs.get(i).split(" ");
            from.add(Integer.parseInt(test[0]));
            to.add(Integer.parseInt(test[1]));
            capacity.add(Integer.parseInt(test[2]));

        }

        System.out.println("-----------------");
        System.out.println("Number of Nodes: "+NOF_Nodes);
        System.out.println("-----------------");

    }
}

