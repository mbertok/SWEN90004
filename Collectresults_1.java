/**
 * Created by marci on 17/05/2017.
 */
import java.io.*;

public class Collectresults_1 {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader br = null;
        boolean first=true;
        FileReader fr = null;
        String dir="Change_recieving_Netlogo";
        int counter=10;
        int lcount=0;
        String[] results=new String[50];
        double[] CC=new double [500];
        double[] CD=new double [500];
        double[] DC=new double [500];
        double[] DD=new double [500];
        int cc,cd,dc,dd=0;
        for(int i=1;i<=counter;i++){
            String sCurrentLine;
            String[] line=new String[16];
            br = new BufferedReader(new FileReader(dir+"/"+i+".csv"));
            lcount=0;
            while ((sCurrentLine = br.readLine()) != null) {
                if(lcount>=20){
                    //System.out.println(sCurrentLine.replace("\"",""));
                    line=sCurrentLine.replace("\"","").split(",");
                    //System.out.println(line[0].replace(" \" ",""));
                    if(!(line[0].equals("0")) &&Integer.valueOf(line[0])<501 ){
                        if(line[0].equals("500")){
                            System.out.println(line[0]);
                            System.out.println(line[1]);
                        }
                        CC[Integer.valueOf(line[0])-1]=CC[Integer.valueOf(line[0])-1]+Integer.valueOf(line[1]);
                        CD[Integer.valueOf(line[0])-1]=CD[Integer.valueOf(line[0])-1]+Integer.valueOf(line[5]);
                        DC[Integer.valueOf(line[0])-1]=DC[Integer.valueOf(line[0])-1]+Integer.valueOf(line[9]);
                        DD[Integer.valueOf(line[0])-1]=DD[Integer.valueOf(line[0])-1]+Integer.valueOf(line[13]);
                    }
                    lcount++;
                }
                else {
                    lcount++;
                }
            }
            br.close();

        }
        for(int j=0;j<50;j++){
            //if(j==0 || j==500)
            CC[j]=CC[j]/counter;
            CD[j]=CD[j]/counter;
            DC[j]=DC[j]/counter;
            DD[j]=DD[j]/counter;
        }
        System.out.println(CC[0]);
        FileWriter f=new FileWriter(dir+"/av-r.csv");
        f.write("Tick,Number,Type\n");
        for(int k=1;k<=50;k++){
            f.write(k+","+CC[k-1]+",CC \n");
            //System.out.println(CC[k-1]);
            f.write(k+","+CD[k-1]+",CD \n");
            f.write(k+","+DC[k-1]+",DC \n");
            f.write(k+","+DD[k-1]+",DD \n");
            //f.write(k+","+CC[k-1]+","+CD[k-1]+","+DC[k-1]+","+DD[k-1]+"\n");
        }
        f.close();


    }
}
