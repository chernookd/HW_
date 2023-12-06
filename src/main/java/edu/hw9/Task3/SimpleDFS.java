package edu.hw9.Task3;



import java.util.ArrayList;
import java.util.Arrays;


public class SimpleDFS {

    private static final ArrayList<Cell> PATH = new ArrayList<>();
    private Cell start;
    private Cell end;

    public static boolean solve(Maze maze, Cell start, Cell end) {
        if (!maze.isValid(start) && !maze.isValid(end)) {
            return false;
        }
        setFalseVisited(maze);
        if (!dfs(maze, start, end, PATH)) {
            return false;
        }
        setPath(PATH);
        end.setState(Cell.State.END);
        start.setState(Cell.State.START);
        return true;
    }

    private static void setFalseVisited(Maze maze) {
        Arrays.stream(maze.getCellArray())
            .forEach(cells -> Arrays.stream(cells)
                .forEach(cell -> cell.setVisited(false)));
    }

    private static void setPath(ArrayList<Cell> path) {
        if (!path.isEmpty()) {
            path.remove(0);
            path.forEach(cell -> cell.setState(Cell.State.PATH));
        }
    }

    @SuppressWarnings({"CyclomaticComplexity", "ReturnCount"})
    private static boolean dfs(Maze maze, Cell current, Cell end, ArrayList<Cell> path) {
        //  System.out.println(current.getRow() + " " + current.getColumn() + "\n");
        if (current.getRow() == end.getRow() && current.getColumn() == end.getColumn()) {
            return true;
        }
        if (maze.isValid(current) && !current.getVisited()) {
            current.setVisited(true);
            path.add(current);
            if (current.getRow() + 1 < maze.getHeight()
                && maze.getCellArray()[current.getRow() + 1][current.getColumn()].getState() != Cell.State.WALL
                && dfs(maze, maze.getCellArray()[current.getRow() + 1][current.getColumn()], end, path)) {
                return true;
            }
            if (current.getRow() - 1 >= 0
                && maze.getCellArray()[current.getRow() - 1][current.getColumn()].getState() != Cell.State.WALL
                && dfs(maze, maze.getCellArray()[current.getRow() - 1][current.getColumn()], end, path)) {
                return true;
            }
            if (current.getColumn() + 1 < maze.getWidth()
                && maze.getCellArray()[current.getRow()][current.getColumn() + 1].getState() != Cell.State.WALL
                && dfs(maze, maze.getCellArray()[current.getRow()][current.getColumn() + 1], end, path)) {
                return true;
            }
            if (current.getColumn() - 1 >= 0
                && maze.getCellArray()[current.getRow()][current.getColumn() - 1].getState() != Cell.State.WALL
                && dfs(maze, maze.getCellArray()[current.getRow()][current.getColumn() - 1], end, path)) {
                return true;
            } else if (!path.isEmpty()) {
                path.get(path.size() - 1).setVisited(false);
                path.remove(path.size() - 1);
            }
        }
        return false;
    }
}
