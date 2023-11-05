package edu.project2Test;

import edu.project2.FirstGenerator;
import edu.project2.FirstSolverDFS;
import edu.project2.Maze;
import edu.project2.MazeDrawer;
import edu.project2.PrintMaze;
import org.junit.jupiter.api.Test;

public class Project2MainTest {


    @Test
    public void project2MainTest() {
        FirstGenerator generator = new FirstGenerator(20, 20);
        Maze maze = generator.generateMaze();
        if(!FirstSolverDFS.solve(maze, maze.getRandomPassageCell(), maze.getRandomPassageCell())) {
            System.out.println("No path");
        }
        MazeDrawer drawer2 = new MazeDrawer(maze, 20, 0);
        PrintMaze.render(maze);
        drawer2.setVisible(true);
    }

}
