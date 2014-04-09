import java.lang.*;
import java.util.*;
import java.awt.Color;


public class SeamCarver {
    private boolean test = false;
    
    private Picture inputPic;
    private Picture myPicture;
//    private final int width, height;
    private int inputW = 0;
    private int W = 0;
    private int inputH = 0;
    private int H = 0;
    private double[][] energyMatrix;
    private Color[][] colorArray;
    
    private int [] horizontal;
    private int [] vertical;
//    private EdgeWeightedDigraph G;
//    private EdgeWeighted2dArray verticalArray;
    
   public SeamCarver(Picture picture) {
       this.inputPic = picture;
       this.myPicture = new Picture(this.inputPic);
       this.inputW = inputPic.width();
       this.W = inputPic.width();
       this.inputH = inputPic.height();
       this.H = inputPic.height();
       
       if(test) StdOut.println("this picture has pixels in col: " + this.W + " and in row: " + this.H);
       
       this.colorArray = new Color[this.inputW][this.inputH];
       for ( int col = 0; col < this.inputW; col++) {
           for ( int row = 0; row < this.inputH; row++) {
               this.colorArray[col][row] = this.inputPic.get(col, row);
           }
       }

       // total col W, row H
       this.energyMatrix = new double[this.H][this.W];
       
       for (int x=0; x < this.H; x++) {
           for ( int y=0; y < this.W; y++) {
               this.energyMatrix[x][y] = this.energy(y, x);
           }
       }
       
//       myPicture.width = picture.width;
//       myPicture.width = picture.width();
//       myPicture.height = picture.height();
//       
//       this.W = picture.width;
//       this.W = picture.width();
       
   }
   
   // create / re-create picture
   public Picture picture() {

       // use color array or energy matrix to create ?       
       Picture newPicture = new Picture(this.W, this.H);
       
       //copy pixels from input picture
       for ( int col = 0; col < this.W; col++) {
           for ( int row = 0; row < this.H; row++) {
               newPicture.set(col, row, this.colorArray[col][row]); 
           }
       }
       
       return newPicture;

   }

   public     int width() {                        // width  of current picture
       return this.W;
   }
   
   public     int height() {                       // height of current picture
       return this.H;
   }
   
   // energy of pixel at column x and row y in current picture
   public  double energy(int x, int y){           
       
       if (x < 0 || x > this.W - 1 || y < 0 || y > this.H - 1) throw new java.lang.IndexOutOfBoundsException();
       
       if (x == 0 || x == this.W - 1 || y == 0 || y == this.H - 1) {
           return 195075.0;
       }
       else {
//         if(test) { StdOut.println("x= " + x + " , y= "+y);}  
           
         int r_x_b = this.myPicture.get(x + 1, y).getRed();
         int g_x_b = this.myPicture.get(x + 1, y).getGreen();
         int b_x_b = this.myPicture.get(x + 1, y).getBlue();
           
         int r_x_a = this.myPicture.get(x - 1, y).getRed();
         int g_x_a = this.myPicture.get(x - 1, y).getGreen();
         int b_x_a = this.myPicture.get(x - 1, y).getBlue();
         
         int r_y_a = this.myPicture.get(x, y - 1).getRed();
         int g_y_a = this.myPicture.get(x, y - 1).getGreen();
         int b_y_a = this.myPicture.get(x, y - 1).getBlue();
         
         int r_y_b = this.myPicture.get(x, y + 1).getRed();
         int g_y_b = this.myPicture.get(x, y + 1).getGreen();
         int b_y_b = this.myPicture.get(x, y + 1).getBlue();

         double rxSqr = Math.pow(Math.abs(r_x_b - r_x_a), 2);
         double gxSqr = Math.pow(Math.abs(g_x_b - g_x_a), 2);
         double bxSqr = Math.pow(Math.abs(b_x_b - b_x_a), 2);
         
         double rySqr = Math.pow(Math.abs(r_y_b - r_y_a), 2);
         double gySqr = Math.pow(Math.abs(g_y_b - g_y_a), 2);
         double bySqr = Math.pow(Math.abs(b_y_b - b_y_a), 2);
           
         return (rxSqr + gxSqr + bxSqr + rySqr + gySqr + bySqr ); 
       }
       
   }
   
   public   int[] findHorizontalSeam() {            // sequence of indices for horizontal seam in current picture
//       transpose the image, call findVerticalSeam(), and transpose it back.

       double[][] traverseMatrix = new double[this.W][this.H];
       
       for (int x=0; x < this.H; x++) {
           for ( int y=0; y < this.W; y++) {
               traverseMatrix[y][x] =  this.energyMatrix[x][y];
           }
       }
       
       EdgeWeighted2dArray horizonArray = new EdgeWeighted2dArray(this.H, this.W, traverseMatrix);
       this.horizontal = new int[this.W];
       int[] array = horizonArray.getSeam();
       for (int col = 0; col < this.W; col++){
           this.horizontal[col] = array[col];
       }
       return this.horizontal;
   }  
   
   private int node(int col, int row) {
       return col + row * this.W + 1;
   }
   
   public   int[] findVerticalSeam() {             // sequence of indices for vertical   seam in current picture

       this.vertical = new int[this.H];
       
       
       EdgeWeighted2dArray verticalArray = new EdgeWeighted2dArray(this.W, this.H, this.energyMatrix);

       int[] array = verticalArray.getSeam();
       for (int row = 0; row < this.H; row++){

           this.vertical[row] = array[row];
           
//           System.out.printf("row: %d col to remove: %d \n", row, this.vertical[row]);
           
//           if (test) {
//               StdOut.println(array[row]);
//               StdOut.println(this.vertical[row]);
//               StdOut.println(this.verticalArray.index[row]);
//           }
       }

       return this.vertical;
   }   
   
   /*Throw a java.lang.IllegalArgumentException if removeVerticalSeam() or removeHorizontalSeam() is called 
    * with an array of the wrong length or if the array is not a valid seam (i.e., either an entry is outside 
    * its prescribed range or two adjacent entries differ by more than 1).
    * 
    * Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() or removeHorizontalSeam() is 
    * called when either the width or height is less than or equal to 1.
    * */
   public void removeHorizontalSeam(int[] a) {   // remove horizontal seam from current picture
       if( this.W <= 1 || this.H <= 1 || a.length != this.W ) throw new java.lang.IllegalArgumentException();
       
       for ( int col = 0; col < this.W; col++) {
           if (a[col] < 0 || a[col] > this.H - 1) throw new java.lang.IllegalArgumentException();
           
           if (col < this.W -1 ) {
               if (Math.abs(a[col] - a[col + 1]) > 1) throw new java.lang.IllegalArgumentException();
           }
           
           // overwrite removed pixel in each col ?
           for (int row2update = a[col] + 1; row2update < this.H; row2update++) {
               this.colorArray[col][row2update - 1] =  this.colorArray[col][row2update];
          
           }
       }
       
       this.H = this.H -1;
   } 
   
   public void removeVerticalSeam(int[] a) {     // remove vertical   seam from current picture
       if( this.W <= 1 || this.H <= 1 || a.length != this.H ) throw new java.lang.IllegalArgumentException();
       
       for ( int row = 0; row < this.H; row++) {
           if (a[row] < 0 || a[row] > this.W - 1) throw new java.lang.IllegalArgumentException();
           
           if (row < this.H -1 ) {
               if (Math.abs(a[row]-a[row+1]) > 1) throw new java.lang.IllegalArgumentException();
           }
           
//           StdOut.println("row: " + row + " a[row] " + a[row]);
           
           // overwrite removed pixel in each row ?
           for (int col_to_update = a[row] + 1; col_to_update < this.W; col_to_update++) {
//               this.colorArray[row][col_to_update - 1] =  this.colorArray[row][col_to_update];
               this.colorArray[col_to_update - 1][row] =  this.colorArray[col_to_update][row];
           }
       }
       
       //DO NOT remove pixel at ( col=a[row], row), in case undo, but remember this pixel, and when picture() creating new resizing pic, do not copy this pixel
//       System.arraycopy(); 
       this.W = this.W -1;
   }
   
   
/*
   public static void main(String[] args)
   {
       Picture inputImg = new Picture(args[0]);
       System.out.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
       //inputImg.show();        
       SeamCarver sc = new SeamCarver(inputImg);
       
       System.out.printf("Displaying horizontal seam calculated.\n");
       double totalSeamEnergy = 0;

       int[] horizontalSeam = sc.findHorizontalSeam();
       for (int j = 0; j < sc.height(); j++)
       {
           for (int i = 0; i < sc.width(); i++)            
           {
               char lMarker = ' ';
               char rMarker = ' ';
               if (j == horizontalSeam[i])
               {
                   lMarker = '[';
                   rMarker = ']';
                   totalSeamEnergy += sc.energy(i, j);
               }

               System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
           }
           System.out.println();
       }                
       
       System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);

       System.out.printf("Displaying vertical seam calculated.\n");
       totalSeamEnergy = 0;

       int[] verticalSeam = sc.findVerticalSeam();
       for (int j = 0; j < sc.height(); j++)
       {
           for (int i = 0; i < sc.width(); i++)            
           {
               char lMarker = ' ';
               char rMarker = ' ';
               if (i == verticalSeam[j])
               {
                   lMarker = '[';
                   rMarker = ']';
                   totalSeamEnergy += sc.energy(i, j);
               }

               System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
           }

           System.out.println();
       }                
       
       System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);

   }*/
   
}