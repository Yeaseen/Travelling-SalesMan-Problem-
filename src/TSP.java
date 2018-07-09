
import java.util.ArrayList;

class data{
   int x, y,idN;
   boolean visited;

   public data(int xx, int yy,int id){
       x=xx;
       y=yy;
       idN=id;
       visited=false;
   }
   public data(int id){
       idN=id;
       System.out.println("idN==========="+idN);
   }
}



public class TSP {

    static double[][] adjMat=null;
    static ArrayList<data> nodeCO= new ArrayList<data>();
    static ArrayList<data> paTH= new ArrayList<data>();
    static double cost=0;


    public static void main(String[] args) {

        int n= Integer.parseInt(args[0]);
        System.out.println("Number of Nodes: " + n);
        for (int i = 0; i < n ; i++) {
            nodeCO.add(new data(Integer.parseInt(args[i*2+1]),Integer.parseInt(args[i*2+2]),i));
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






        //func1(2);
        func2(10);
        System.out.print("PATH IS: ");
        for (int i = 0; i < paTH.size() ; i++) {
            if(i<paTH.size()-1)System.out.print(paTH.get(i).idN+"-->");
            else System.out.print(paTH.get(i).idN);

            if(i<=paTH.size()-2) {
                cost+=adjMat[paTH.get(i).idN][paTH.get(i+1).idN];
            }
        }
        System.out.println("\nCOST: "+cost);

    }


    public static void func2(int i){
        nodeCO.get(i).visited=true;
        paTH.add(0,nodeCO.get(i));

        int dest1= getMinimumOfColumn(i);
        nodeCO.get(dest1).visited=true;
        paTH.add(1,nodeCO.get(dest1));
        paTH.add(2,nodeCO.get(i));

        while (checkNonVisited())
        {
            int minNode=0;
            for (int j = 0; j < paTH.size()-1 ; j++) {
                //System.out.println("idN"+paTH.get(j).idN);
                minNode=getMinimumOfColumn(paTH.get(j).idN);
            }
            System.out.println("min Node--> "+minNode);

            int destM=0;
            double min= Double.MAX_VALUE;
            System.out.println("path size=="+paTH.size());
            for (int j = 0; j < paTH.size()-1 ; j++) {
                //System.out.println("j:  "+j);
                //System.out.println(adjMat[paTH.get(j).idN][minNode]+ " "+adjMat[minNode][paTH.get(j+1).idN]+ " "+adjMat[paTH.get(j).idN]
                    //    [paTH.get(j+1).idN]);
                double x=adjMat[paTH.get(j).idN][minNode]+adjMat[minNode][paTH.get(j+1).idN]-adjMat[paTH.get(j).idN]
                        [paTH.get(j+1).idN];
                //System.out.println("x: "+x);
                if(x<min){
                    min=x;
                    destM=j;
                }

            }
            nodeCO.get(minNode).visited=true;
            paTH.add(destM+1,nodeCO.get(minNode));
            //System.out.println("put in "+destM);

        }







    }

    public static void func1(int i){
        paTH.add(nodeCO.get(i));
        getTSPPath(i);
        paTH.add(nodeCO.get(i));


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
            paTH.add(nodeCO.get(explored));

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
        //System.out.println( "Minimum of column " + col + " = " + min );
            return res;


    }
}
