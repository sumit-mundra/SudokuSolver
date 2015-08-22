package sudoku;

import java.io.File;
import java.util.Scanner;

public class ApplicationMain {
    public static void main(String args[]){
        int result = 0;
        Scanner sc = null;
        try{
            File file = new File("C:\\Users\\Sudeep\\Desktop\\p096_sudoku.txt");
            sc = new Scanner(file);
            int count = 0;
            int[] inputArray = new int[81];
            int k = 0;
            while(sc.hasNextLine()){
                
                if(count%10==0){
                    String s = sc.nextLine();
                    System.out.println(s);
                    count++;
                    continue;
                }
                String row = sc.nextLine();
                for (int i = 0; i<9;i++){
                    inputArray[k++] = Integer.parseInt(""+row.charAt(i));
                }
                if(count%10==9){
                    SudokuGrid grid = new SudokuGrid(inputArray);
                    System.out.println("About to solve this grid:");
                    grid.print();
                    System.out.println();
                    Thread.sleep(3000L);
                    grid.solve();
                    
                    result = result + grid.fetchUniqueNumber();
                    inputArray = new int[81];
                    k=0;
                }
                count++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            if(sc!=null )sc.close();
        }
        
        System.out.println("Result is " + result);

    }
}
