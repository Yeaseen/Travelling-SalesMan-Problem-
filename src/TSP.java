import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;

class data{
   int x, y;
   boolean visited;

   public data(int xx, int yy){
       x=xx;
       y=yy;
       visited=false;
   }
}

public class TSP {

    static double[][] adjMat=null;
    static ArrayList<data> nodeCO= new ArrayList<data>();
    static String path=null;

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
        System.out.println();
        path="5-->";
        //nodeCO.get(0).visited=true;
        getTSPPath(5);
        path+="5";
        System.out.println();
        System.out.println(path);



        //System.out.println(getMinimumOfColumn(3));

    }

    public static double getEucDIs(int p,int q, int r, int s){
        double m= Math.abs(p - r);
        double n= Math.abs(q - s);

        return Math.sqrt((m*m) +(n*n));
    }

    public static boolean checkNonVisited(){
        for (int i = 0; i < nodeCO.size(); i++) {
            if(!nodeCO.get(i).visited) return true;
        }
        return false;
    }

    public static void getTSPPath(int sNode){
        nodeCO.get(sNode).visited=true;
        while (checkNonVisited()){
            int explored=getMinimumOfColumn(sNode);
            path+=explored+"-->";
            getTSPPath(explored);
        }
    }

    public static int getMinimumOfColumn (int col )
    {
            double min = Integer.MAX_VALUE;
            int res = 0;
            for ( int j = 0; j < adjMat [ col ].length; j++ ) {
                if (adjMat[j][col] < min && (col != j) && !nodeCO.get(j).visited) {
                    min = adjMat[j][col];
                    res=j;
                }
            }
        System.out.println( "Minimum of column " + col + " = " + min );
            return res;


    }
}
