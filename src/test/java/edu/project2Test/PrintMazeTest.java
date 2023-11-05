package edu.project2Test;

import edu.project2.Cell;
import edu.project2.FirstGenerator;
import edu.project2.FirstSolverDFS;
import edu.project2.Maze;
import edu.project2.PrintMaze;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrintMazeTest {

    @Test
    public void PrintTest() {
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
                new Cell(4,4, Cell.State.WALL)}
        };

        Maze maze = new Maze(cellMatrix[0].length, cellMatrix.length, cellMatrix);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        PrintMaze.render(maze);
        String output = outputStream.toString();
        String correctOutput =
            " Û  Û  Û  Û  Û " + "\n" +
            " Û  .  Û  Û  Û " + "\n" +
            " Û  .  Û  .  Û " + "\n" +
            " Û  .  .  .  Û " + "\n" +
            " Û  Û  Û  Û  Û " + "\n";

        assertTrue(output.contains(correctOutput));
    }

    @Test
    public void PrintWithPathTest() {
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
                new Cell(4,4, Cell.State.WALL)}
        };

        Maze maze = new Maze(cellMatrix[0].length, cellMatrix.length, cellMatrix);

        FirstSolverDFS.solve(maze, maze.getCellArray()[1][1],  maze.getCellArray()[2][3]);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        PrintMaze.render(maze);
        String output = outputStream.toString();
        String correctOutput =
                " Û  Û  Û  Û  Û " + "\n" +
                " Û  S  Û  Û  Û " + "\n" +
                " Û  *  Û  E  Û " + "\n" +
                " Û  *  *  *  Û " + "\n" +
                " Û  Û  Û  Û  Û " + "\n";
        assertEquals(output, correctOutput);
        assertTrue(output.equalsIgnoreCase(correctOutput));
        assertTrue(output.contains(correctOutput));
    }
}
