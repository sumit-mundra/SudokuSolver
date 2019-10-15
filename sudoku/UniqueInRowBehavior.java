package sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sumit
 */
public class UniqueInRowBehavior implements Behavior {

    @Override
    public void evaluate(SudokuGrid grid) {
        for (int y = 0; y < 9; y++) {
            Set<Integer> fixed = new HashSet<Integer>();
            populateFixed(grid, y, fixed);
            updateFlagsWithFixed(grid, y, fixed);
            findUniqueInRowByFlag(grid, y);
            checkForFinal(grid, y);
        }
    }

    private void populateFixed(SudokuGrid grid, int y, Set<Integer> fixed) {
        for (int x = 0; x < 9; x++) {
            int value = grid.grid[x][y].value;
            if (value != 0) fixed.add(value);
        }
    }

    private void checkForFinal(SudokuGrid grid, int y) {
        for (int x = 0; x < 9; x++) {
            SudokuCell cell = grid.grid[x][y];
            if (cell.value == 0) cell.finalizeValue();
        }
    }

    private void updateFlagsWithFixed(SudokuGrid grid, int y, Set<Integer> fixed) {
        for (int x = 0; x < 9; x++) {
            SudokuCell cell = grid.grid[x][y];
            if (cell.value == 0) {
                for (Integer a : fixed) {
                    cell.flags[a - 1] = false;
                }
            }
        }
    }

    private void findUniqueInRowByFlag(SudokuGrid grid, int y) {
        for (int x = 0; x < 9; x++) {
            SudokuCell pivot = grid.grid[x][y];
            if (pivot.value != 0) continue;
            //System.out.println("At cell: "+x+","+y);
            boolean[] refArray = pivot.flags;

            for (int m = 0; m < refArray.length; m++) { //iterate over flags for all true digit, if it is only true
                // location in entire row.
                boolean unique = true;
                if (!refArray[m]) continue;
                if (refArray[m]) {
                    //System.out.println("Checking for digit : "+ (m+1));
                    for (int l = 0; l < 9; l++) {
                        SudokuCell cell = grid.grid[l][y];
                        if (cell.value != 0) continue;
                        if (cell == pivot) {
                            continue;
                        }
                        if (cell.flags[m]) {
                            unique = false;
                            break;
                        }
                    }
                }
                if (unique && refArray[m]) {
                    System.out.println("Unique in row found for " + x + ":" + y + "=" + (m + 1));
                    pivot.value = m + 1;
                    Arrays.fill(refArray, false);
                    refArray[m] = true;
                }
            }
        }
    }
}
