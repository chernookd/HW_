package edu.hw9.Task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class MultithreadingDFS {

    private MultithreadingDFS() {

    }

    public static boolean solve(Maze maze, Cell start, Cell end) {
        if (!maze.isValid(start) && !maze.isValid(end)) {
            return false;
        }
        setFalseVisited(maze);
        ForkJoinPool pool = new ForkJoinPool(1);
        FirstSolverDFS dfsTask = new FirstSolverDFS(maze, start, end, new ArrayList<>());
        ArrayList<Cell> path  = pool.invoke(dfsTask);
        setPath(path);
        end.setState(Cell.State.END);
        start.setState(Cell.State.START);

        return path != null;
    }

    private static void setFalseVisited(Maze maze) {
        Arrays.stream(maze.getCellArray())
            .forEach(cells -> Arrays.stream(cells)
                .forEach(cell -> cell.setVisited(false)));
    }

    private static void setPath(ArrayList<Cell> path) {
        if (path != null && !path.isEmpty()) {
            path.remove(0);
            path.forEach(cell -> cell.setState(Cell.State.PATH));
        }
    }
}
