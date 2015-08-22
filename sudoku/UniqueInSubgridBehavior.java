package sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements the unique in sub grid behavior for a number
 * @author Sumit
 *
 */
public class UniqueInSubgridBehavior implements Behavior {

    /*
     * Iterate over all subgrids, choosing one,
     * store all numbers of this subgrid in memory
     * update flags of members : removing true for fixed values
     */
    @Override
    public void evaluate(SudokuGrid grid) {
        for(int l=0;l<3;l++){//subgrid row index
            for(int m=0;m<3;m++){ // subgrid column index
                Set<Integer> fixed = new HashSet<Integer>();
                populateFixed(grid, l, m, fixed);
                updateFlags(grid, l, m, fixed);
                //System.out.println("Checking for subgrid:"+ System.currentTimeMillis());
                findUniqueInSubgrid(grid,l,m);
                //System.out.println("Cheecked in subgrid: "+System.currentTimeMillis());
                checkForFinal(grid, l, m, fixed);
            }
        }
        
    }

    private void findUniqueInSubgrid(SudokuGrid grid, int row, int col) {
        for(int i=3*row; i<3*row+3; i++){
            for(int j=3*col; j<3*col+3; j++){
                
                
                SudokuCell pivot = grid.grid[i][j];
                if(pivot.value!=0) continue;
                boolean[] refArray = pivot.flags;
                StringBuilder sb = new StringBuilder();
                for(int m=0;m<refArray.length;m++){
                    sb.append(refArray[m]==true?1:0);
                }
                System.out.println("Checking "+i+","+j + "::"+sb.toString());
                for(int m=0;m<refArray.length;m++){ //iterate over flags for all true digit, if it is only true location in entire row.
                    boolean unique = true;
                    if(refArray[m]){
                        OUTERLOOP:
                        for(int p=3*row; p<3*row+3; p++){
                            for(int q=3*col; q<3*col+3; q++){
                                //System.out.println("Comparing with "+p+","+q);
                                SudokuCell cell = grid.grid[p][q];
                                if(cell.value!=0) continue;
                                if(cell==pivot) continue;
                                if(cell.flags[m]) {
                                    unique = false;
                                    break OUTERLOOP;
                                }
                            }
                        }//end OUTERLOOP
                    }
                    if(unique && refArray[m]) {
                        //System.out.println("Unique in subgrid found for:"+i+","+j+":"+ (m+1));
                        pivot.value = m+1;
                        Arrays.fill(refArray, false);
                        refArray[m]=true;
                    }
                }
            }
        }
    }
    private void populateFixed(SudokuGrid grid, int row , int col, Set<Integer> fixed){
        for(int i=3*row; i<3*row+3; i++){
            for(int j=3*col; j<3*col+3; j++){
                int value = grid.grid[i][j].value;
                if(value!=0) fixed.add(value);
            }
        }
    }

    private void updateFlags(SudokuGrid grid, int row , int col, Set<Integer> fixed){
        for(int i=3*row; i<3*row+3; i++){
            for(int j=3*col; j<3*col+3; j++){
                SudokuCell cell = grid.grid[i][j];
                if(cell.value==0){
                    for(int a : fixed){
                        cell.flags[a-1] = false;
                    }
                }
                
            }
        } //end for
    }

    public void checkForFinal(SudokuGrid grid, int row , int col, Set<Integer> fixed){
        //updateFinal
        for(int i=3*row; i<3*row+3; i++){
            for(int j=3*col; j<3*col+3; j++){
                SudokuCell cell = grid.grid[i][j];
                if(cell.value==0) cell.finalizeValue();
            }
        }
    }

}
