package edu.hw9.Task3Tets;


import edu.hw9.Task3.Cell;
import edu.hw9.Task3.Maze;
import edu.hw9.Task3.MultithreadingDFS;
import edu.hw9.Task3.PrintMaze;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDFSSolver {

    @Test
    public void findPathTest() {
        Cell[][] cellMatrix = {
            {new Cell(0,0, Cell.State.WALL), new Cell(0,1, Cell.State.WALL),
                new Cell(0,2, Cell.State.WALL), new Cell(0,3, Cell.State.WALL),
                new Cell(0,4, Cell.State.WALL)},
            {new Cell(1,0, Cell.State.WALL), new Cell(1,1, Cell.State.PASSAGE),
                new Cell(1,2, Cell.State.WALL), new Cell(1,3, Cell.State.PASSAGE),
            new Cell(1,4, Cell.State.WALL)},
            {new Cell(2,0, Cell.State.WALL), new Cell(2,1, Cell.State.PASSAGE),
                new Cell(2,2, Cell.State.WALL), new Cell(2,3, Cell.State.PASSAGE),
                new Cell(2,4, Cell.State.WALL)},
            {new Cell(3,0, Cell.State.WALL), new Cell(3,1, Cell.State.PASSAGE),
                new Cell(3,2, Cell.State.PASSAGE), new Cell(3,3, Cell.State.PASSAGE),
                new Cell(3,4, Cell.State.WALL)},
            {new Cell(4,0, Cell.State.WALL), new Cell(4,1, Cell.State.WALL),
                new Cell(4,2, Cell.State.WALL), new Cell(4,3, Cell.State.WALL),
                new Cell(4,4, Cell.State.WALL)},

        };
        ArrayList<Cell> correctPath = new ArrayList<>(List.of(cellMatrix[2][1], cellMatrix[3][1], cellMatrix[3][2],
            cellMatrix[3][3], cellMatrix[2][3]));
        Maze maze = new Maze(5, 5, cellMatrix);
        Cell start = maze.getCellArray()[1][1];
        Cell end = maze.getCellArray()[1][3];
        boolean result = MultithreadingDFS.solve(maze, start, end);


        assertTrue(result);
        assertTrue(maze.getListPathCell().containsAll(correctPath));
    }

    @Test
    public void findPathTestWithManyPassage() {
        Cell[][] cellMatrix = {
            {new Cell(0,0, Cell.State.WALL), new Cell(0,1, Cell.State.WALL),
                new Cell(0,2, Cell.State.WALL), new Cell(0,3, Cell.State.WALL),
                new Cell(0,4, Cell.State.WALL)},
            {new Cell(1,0, Cell.State.WALL), new Cell(1,1, Cell.State.PASSAGE),
                new Cell(1,2, Cell.State.PASSAGE), new Cell(1,3, Cell.State.PASSAGE),
                new Cell(1,4, Cell.State.WALL)},
            {new Cell(2,0, Cell.State.WALL), new Cell(2,1, Cell.State.PASSAGE),
                new Cell(2,2, Cell.State.WALL), new Cell(2,3, Cell.State.PASSAGE),
                new Cell(2,4, Cell.State.WALL)},
            {new Cell(3,0, Cell.State.WALL), new Cell(3,1, Cell.State.PASSAGE),
                new Cell(3,2, Cell.State.PASSAGE), new Cell(3,3, Cell.State.PASSAGE),
                new Cell(3,4, Cell.State.WALL)},
            {new Cell(4,0, Cell.State.WALL), new Cell(4,1, Cell.State.WALL),
                new Cell(4,2, Cell.State.WALL), new Cell(4,3, Cell.State.WALL),
                new Cell(4,4, Cell.State.WALL)},

        };
        ArrayList<Cell> correctPath = new ArrayList<>(List.of(cellMatrix[2][1], cellMatrix[3][1], cellMatrix[3][2],
            cellMatrix[3][3], cellMatrix[2][3]));
        ArrayList<Cell> correctPath2 = new ArrayList<>(List.of(cellMatrix[1][2]));
        Maze maze = new Maze(5, 5, cellMatrix);
        Cell start = maze.getCellArray()[1][1];
        Cell end = maze.getCellArray()[1][3];
        boolean result = MultithreadingDFS.solve(maze, start, end);
        assertTrue(result);
        assertTrue(maze.getListPathCell().containsAll(correctPath)
            || maze.getListPathCell().containsAll(correctPath2));
    }

    @Test
    public void NoPathTest() {
        Cell[][] cellMatrix = {
            {new Cell(0,0, Cell.State.WALL), new Cell(0,1, Cell.State.WALL),
                new Cell(0,2, Cell.State.WALL), new Cell(0,3, Cell.State.WALL),
                new Cell(0,4, Cell.State.WALL)},
            {new Cell(1,0, Cell.State.WALL), new Cell(1,1, Cell.State.PASSAGE),
                new Cell(1,2, Cell.State.WALL), new Cell(1,3, Cell.State.PASSAGE),
                new Cell(1,4, Cell.State.WALL)},
            {new Cell(2,0, Cell.State.WALL), new Cell(2,1, Cell.State.WALL),
                new Cell(2,2, Cell.State.WALL), new Cell(2,3, Cell.State.WALL),
                new Cell(2,4, Cell.State.WALL)},
            {new Cell(3,0, Cell.State.WALL), new Cell(3,1, Cell.State.PASSAGE),
                new Cell(3,2, Cell.State.WALL), new Cell(3,3, Cell.State.PASSAGE),
                new Cell(3,4, Cell.State.WALL)},
            {new Cell(4,0, Cell.State.WALL), new Cell(4,1, Cell.State.WALL),
                new Cell(4,2, Cell.State.WALL), new Cell(4,3, Cell.State.WALL),
                new Cell(4,4, Cell.State.WALL)},

        };
        Maze maze = new Maze(5, 5, cellMatrix);
        Cell start = maze.getCellArray()[1][1];
        Cell end = maze.getCellArray()[1][3];
        boolean result = MultithreadingDFS.solve(maze, start, end);
        PrintMaze.print(maze.getCellArray());


        assertFalse(result);
    }

    @Test
    public void invalidArgTest() {
        Cell[][] cellMatrix = {
            {new Cell(0,0, Cell.State.WALL), new Cell(0,1, Cell.State.WALL),
                new Cell(0,2, Cell.State.WALL), new Cell(0,3, Cell.State.WALL),
                new Cell(0,4, Cell.State.WALL)},
            {new Cell(1,0, Cell.State.WALL), new Cell(1,1, Cell.State.PASSAGE),
                new Cell(1,2, Cell.State.WALL), new Cell(1,3, Cell.State.WALL),
                new Cell(1,4, Cell.State.WALL)},
            {new Cell(2,0, Cell.State.WALL), new Cell(2,1, Cell.State.PASSAGE),
                new Cell(2,2, Cell.State.WALL), new Cell(2,3, Cell.State.PASSAGE),
                new Cell(2,4, Cell.State.WALL)},
            {new Cell(3,0, Cell.State.WALL), new Cell(3,1, Cell.State.PASSAGE),
                new Cell(3,2, Cell.State.PASSAGE), new Cell(3,3, Cell.State.PASSAGE),
                new Cell(3,4, Cell.State.WALL)},
            {new Cell(4,0, Cell.State.WALL), new Cell(4,1, Cell.State.WALL),
                new Cell(4,2, Cell.State.WALL), new Cell(4,3, Cell.State.WALL),
                new Cell(4,4, Cell.State.WALL)},

        };
        Maze maze = new Maze(5, 5, cellMatrix);
        Cell start = maze.getCellArray()[0][0];
        Cell end = maze.getCellArray()[1][3];
        boolean result = MultithreadingDFS.solve(maze, start, end);
        assertFalse(result);
    }
}
