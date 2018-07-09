
import java.util.ArrayList;
import java.util.Collections;

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






        NearestNeighbour(8);
        printPathCost(paTH);
        twoOptPathResult(paTH);
//        NearestInsertion(10);
//        printPathCost(paTH);


    }

    public static void printPathCost(ArrayList<data> route){
        double cost=0;
        System.out.print("PATH IS: ");
        for (int i = 0; i < route.size() ; i++) {
            if(i<route.size()-1) System.out.print(route.get(i).idN+"-->");
            else System.out.print(route.get(i).idN);

            if(i<=route.size()-2) {
                cost+=adjMat[route.get(i).idN][route.get(i+1).idN];
            }
        }
        System.out.println("\nCOST: "+cost);

    }

    public static double getPathCost(ArrayList<data> route){
        double cres=0;
        for (int i = 0; i < route.size() ; i++) {

            if(i<=route.size()-2) {
                cres+=adjMat[route.get(i).idN][route.get(i+1).idN];
            }
        }
        return cres;

    }

    public static void twoOptPathResult(ArrayList<data> route){
        double oldC=getPathCost(route);
        for (int i = 2; i < route.size()-2; i++) {
            System.out.println("i: "+i);
            for (int j = i+1; j < route.size()-1 ; j++) {
                ArrayList<data> newRoute=twoOptSwap(route,i,j);
                double newC=getPathCost(newRoute);
                if(newC<oldC){
                    route=newRoute;
                }
            }

        }

        printPathCost(route);


    }


    public static ArrayList<data> twoOptSwap(ArrayList<data> route, int i, int k){

        ArrayList<data> firstPortion=new ArrayList<data>(route.subList(0,i-1));
//        for (int j = 0; j <firstPortion.size() ; j++) {
//            System.out.print(firstPortion.get(j).idN+"-->");
//        }
//        System.out.println();
        ArrayList<data> secndPart=new ArrayList<data>(route.subList(i-1,k));
//        for (int j = 0; j < secndPart.size() ; j++) {
//            System.out.print(secndPart.get(j).idN+" ");
//        }
//        System.out.println();
        Collections.reverse(secndPart);
//        for (int j = 0; j < secndPart.size() ; j++) {
//            System.out.print(secndPart.get(j).idN+" ");
//        }
//        System.out.println();
        ArrayList<data> thirdPart=new ArrayList<data>(route.subList(k,route.size()));
//        for (int j = 0; j < thirdPart.size() ; j++) {
//            System.out.print(thirdPart.get(j).idN+" ");
//        }
//        System.out.println();

        ArrayList<data> res=new ArrayList<data>();

        res.addAll(firstPortion);
        res.addAll(secndPart);
        res.addAll(thirdPart);

        return res;


    }




    public static void NearestInsertion(int i){
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
            //System.out.println("min Node--> "+minNode);

            int destM=0;
            double min= Double.MAX_VALUE;
            //System.out.println("path size=="+paTH.size());
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

    public static void NearestNeighbour(int i){
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
