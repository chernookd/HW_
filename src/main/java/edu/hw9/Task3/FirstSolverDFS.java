package edu.hw9.Task3;


import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class FirstSolverDFS extends RecursiveTask<ArrayList<Cell>> {

    private ArrayList<Cell> path = new ArrayList<>();
    private Cell current;
    private Cell end;
    private Maze maze;

    public ArrayList<Cell> getPath() {
        return path;
    }

    public FirstSolverDFS(Maze maze, Cell current, Cell end, ArrayList<Cell> path) {
        this.maze = maze;
        this.current = current;
        this.end = end;
        this.path = path;
    }

    @Override
    protected ArrayList<Cell> compute() {
        if (current.getRow() == end.getRow() && current.getColumn() == end.getColumn()) {
            return new ArrayList<>(path);
        }

        if (maze.isValid(current) && !current.getVisited()) {
            current.setVisited(true);
            path.add(current);

            ArrayList<FirstSolverDFS> tasks = new ArrayList<>();

            int[] indent = {1, -1, 0, 0 };

            for (int i = 0; i < indent.length; i++) {
                int nextRow = current.getRow() + indent[i];
                int nextCol = current.getColumn() + indent[indent.length - 1 - i];

                if (isValidPosition(nextRow, nextCol)) {
                    Cell nextCell = maze.getCellArray()[nextRow][nextCol];
                    tasks.add(new FirstSolverDFS(maze, nextCell, end, new ArrayList<>(path)));
                }
            }

            for (FirstSolverDFS task : tasks) {
                task.fork();
            }

            for (FirstSolverDFS task : tasks) {
                ArrayList<Cell> result = task.join();
                if (result != null) {
                    return result;
                }
            }

            current.setVisited(false);
            path.remove(path.size() - 1);
        }

        return null;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < maze.getHeight()
            && col >= 0 && col < maze.getWidth()
            && maze.getCellArray()[row][col].getState() != Cell.State.WALL;
    }
}
