/**
 * Created by marci on 17/05/2017.
 */
import java.io.*;

public class Collectresults {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader br = null;
        boolean first=true;
        FileReader fr = null;
        int counter=50;
        String[] results=new String[50];
        double[] CC=new double [500];
        double[] CD=new double [500];
        double[] DC=new double [500];
        double[] DD=new double [500];
        String[] r=new String[5];
        int cc,cd,dc,dd=0;
        for(int i=1;i<=counter;i++){
            first=true;
            String sCurrentLine;

            br = new BufferedReader(new FileReader(i+".csv"));

            while ((sCurrentLine = br.readLine()) != null) {
                if(!first) {
                    r = sCurrentLine.split(",");
                    CC[Integer.valueOf(r[0])-1]=CC[Integer.valueOf(r[0])-1]+Integer.valueOf(r[1]);
                    CD[Integer.valueOf(r[0])-1]=CD[Integer.valueOf(r[0])-1]+Integer.valueOf(r[2]);
                    DC[Integer.valueOf(r[0])-1]=DC[Integer.valueOf(r[0])-1]+Integer.valueOf(r[3]);
                    DD[Integer.valueOf(r[0])-1]=DD[Integer.valueOf(r[0])-1]+Integer.valueOf(r[4]);
                    System.out.println("Tick:" + r[0]);
                }
                else{
                    first=false;
                }
            }
            br.close();

        }
        for(int j=0;j<500;j++){
            System.out.println(CC[j]);
            CC[j]=CC[j]/counter;
            CD[j]=CD[j]/counter;
            DC[j]=DC[j]/counter;
            DD[j]=DD[j]/counter;
        }
        System.out.println(CC[0]);
        FileWriter f=new FileWriter("av-r.csv");
        f.write("Tick,Number,Type\n");
        for(int k=1;k<=500;k++){
            f.write(k+","+CC[k-1]+",CC \n");
            f.write(k+","+CD[k-1]+",CD \n");
            f.write(k+","+DC[k-1]+",DC \n");
            f.write(k+","+DD[k-1]+",DD \n");
            //f.write(k+","+CC[k-1]+","+CD[k-1]+","+DC[k-1]+","+DD[k-1]+"\n");
        }
        f.close();


    }
}
