package edu.hw9.Task3;

public class PrintMaze {

    private  PrintMaze() {

    }

    public static void render(Maze maze) {
       print(maze.getCellArray());
    }

    private static final int WALL_SYMBOL_CODE = 219;

    @SuppressWarnings("RegexpSinglelineJava")
    public static void print(Cell[][] matrix) {
        for (Cell[] cells : matrix) {
            for (Cell cell : cells) {
                if (cell.getState() == Cell.State.WALL) {
                    char symbol = WALL_SYMBOL_CODE;
                    System.out.print(" " + symbol + " ");
                }
                if (cell.getState() == Cell.State.PASSAGE) {
                    System.out.print(" . ");
                }
                if (cell.getState() == Cell.State.PATH) {
                    System.out.print(" * ");
                }
                if (cell.getState() == Cell.State.START) {
                    System.out.print(" S ");
                }
                if (cell.getState() == Cell.State.END) {
                    System.out.print(" E ");
                }
            }
            System.out.print("\n");
        }
    }
}
