package sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Sumit
 */
public class UniqueInColumnBehavior implements Behavior {

    @Override
    public void evaluate(SudokuGrid grid) {
        for(int x=0; x<9;x++){// denotes each row
            Set<Integer> fixed = new HashSet<Integer>();
            //fill fixed numbers in set
            populateFixed(grid, x, fixed);
            //remove them from possible solution for others
            updateFlags(grid, x, fixed);
            
            //find unique in column by flags
            findUniqueInColumnByFlag(grid, x);
            
            //check for any final solution obtained
            checkForFinal(grid, x);
        }
    }

    private void findUniqueInColumnByFlag(SudokuGrid grid, int x) {
        for(int y=0;y<9;y++){
            SudokuCell pivot = grid.grid[x][y];
            if(pivot.value!=0) continue;
            boolean[] refArray = pivot.flags;
            
            for(int m=0;m<refArray.length;m++){ //iterate over flags for all true digit, if it is only true location in entire row.
                boolean unique = true;
                if(refArray[m]){
                    for(int l=0;l<9;l++){
                        SudokuCell cell = grid.grid[x][l];
                        if(cell.value!=0)continue;
                        if(cell==pivot) continue;
                        if(cell.flags[m]) {
                            unique = false;
                            break;
                        }
                    }
                }
                if(unique && refArray[m]) {
                    System.out.println("Unique in column found for "+x+":"+y+"="+ (m+1) );
                    pivot.value = m+1;
                    Arrays.fill(refArray, false);
                    refArray[m]=true;
                }
            }
        }
    
    }

    private void populateFixed(SudokuGrid grid, int x, Set<Integer> fixed) {
        //get all numbers in that row
        for(int y=0; y<9; y++){
            int value = grid.grid[x][y].value;
            if(value!=0) fixed.add(value);
        }
    }

    private void checkForFinal(SudokuGrid grid, int x) {
        for(int y=0; y<9; y++){
            SudokuCell cell = grid.grid[x][y];
            if(cell.value==0)cell.finalizeValue();
        }
    }

    private void updateFlags(SudokuGrid grid, int x, Set<Integer> fixed) {
        for(int y=0;y<9;y++){
            SudokuCell cell = grid.grid[x][y];
            if(cell.value==0){
                for(Integer a : fixed){
                    cell.flags[a-1] = false;
                }
            }
        }
    }

}
