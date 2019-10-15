package sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sumit
 */
public class SudokuGrid {
    boolean solvedGrid = false;
    SudokuCell[][] grid = new SudokuCell[9][9];
    List<Behavior> list = new ArrayList<Behavior>();

    SudokuGrid(int[] input) {
        initialize(input);
        list.add(new UniqueInRowBehavior());
        list.add(new UniqueInColumnBehavior());
        list.add(new UniqueInSubgridBehavior());
    }


    void initialize(int[] input) {
        if (input.length != 81) {
            System.out.println("The input array is incomplete");
        } else {
            int index = 0;
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    grid[x][y] = new SudokuCell(x, y, input[index++]);
                }
            }
        }
    }

    void solve() {
        //check if it is solved
        while (!isSolved()) {
            for (Behavior b : list) {
                b.evaluate(this);
                this.print();
                checkSolved();
            }
            /*try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
        this.print();
    }

    boolean checkSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = this.grid[i][j];
                if (cell.value != 0) {
                    continue;
                } else
                    return false;
            }
        }
        setSolved(true);
        return true;
    }

    void print() {
        System.out.println("-----------------");
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                SudokuCell cell = this.grid[x][y];
                System.out.print(cell.value);
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    public boolean isSolved() {
        return solvedGrid;
    }

    public void setSolved(boolean flag) {
        solvedGrid = flag;
    }

    int fetchUniqueNumber() {
        String result = this.grid[0][0].value + "" + this.grid[1][0].value + this.grid[2][0].value;
        return Integer.parseInt(result);
    }
}
