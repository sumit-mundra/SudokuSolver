package sudoku;

import java.util.Arrays;

/**
 * Entity class for Sudoku Cells.
 * Each cell identity is obtained by ({@link #x},{@link #y}) pair.
 * Every cell has a possible solution set with digits 1 to 9. {@link this.flags} hold the boolean array for the
 * same.
 * 0 means not possible. 1 means possible.
 * {@link #value} denotes actual digit which would be part of the solution. It could be a given or
 * calculated value.
 *
 * @author Sumit
 */
public class SudokuCell {
    final int MAX_SIZE = 9;
    int value;
    boolean[] flags = new boolean[MAX_SIZE];
    private int x;
    private int y;

    SudokuCell() {
        this(0, 0, 0);
    }

    SudokuCell(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        Arrays.fill(flags, true);
        if (value != 0) {
            Arrays.fill(flags, false);
            flags[value - 1] = true;
        }
    }

    SudokuCell(int x, int y) {
        this(x, y, 0);
    }


    public void finalizeValue() {
        int trueCount = 0;
        int value = -1;
        for (int j = 0; j < 9; j++) {
            if (flags[j]) {
                trueCount++;
                value = j + 1;
            }
        }
        if (trueCount == 1) {
            System.out.println("Finalized " + this.x + "," + this.y + "with value " + value);
            this.value = value;
        }
    }

}
