import java.util.ArrayList;

class data{
   int x, y;
   
   public data(int xx, int yy){
       x=xx;
       y=yy;
   }
}

public class TSP {

    static double[][] adjMat=null;
    static ArrayList<data> nodeCO= new ArrayList<data>();

    public static void main(String[] args) {

        int n= Integer.parseInt(args[0]);
        System.out.println("Number of Nodes: " + n);
        for (int i = 0; i < n ; i++) {
            nodeCO.add(new data(Integer.parseInt(args[i*2+1]),Integer.parseInt(args[i*2+2])));
            System.out.println(nodeCO.get(i).x+"  "+nodeCO.get(i).y);
        }
        adjMat=new double[n][n];

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if(j>i){
                    double dis=getEucDIs(nodeCO.get(i).x,nodeCO.get(i).y,
                            nodeCO.get(j).x,nodeCO.get(j).y);
                    adjMat[i][j]=dis;
                    adjMat[j][i]=dis;
                }
                else if(i==j) adjMat[i][j]=0;

            }

        }

        for (int i = 0; i < n  ; i++) {
            for (int j = 0; j < n ; j++) {
                System.out.print(String.format("%.2f",adjMat[i][j])+"  ");
            }
            System.out.println();
        }


    }

    public static double getEucDIs(int p,int q, int r, int s){
        double m= Math.abs(p - r);
        double n= Math.abs(q - s);

        return Math.sqrt((m*m) +(n*n));
    }
}
