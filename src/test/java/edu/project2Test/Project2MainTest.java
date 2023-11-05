package edu.project2Test;

import edu.project2.FirstGenerator;
import edu.project2.FirstSolverDFS;
import edu.project2.Maze;
import edu.project2.MazeDrawer;
import org.junit.jupiter.api.Test;

public class Project2MainTest {


    @Test
    public void project2MainTest() {
        FirstGenerator generator = new FirstGenerator(70, 70);
        Maze maze = generator.generateMaze();
        if(!FirstSolverDFS.solve(maze, maze.getRandomPassageCell(), maze.getRandomPassageCell())) {
            System.out.println("No path");
        }
        MazeDrawer drawer2 = new MazeDrawer(maze, 10);
        drawer2.setVisible(true);
    }

}
