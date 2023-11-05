package edu.project2;

public class MainTest {

    private MainTest() {

    }

    @SuppressWarnings({"MagicNumber", "RegexpSinglelineJava"})
    public static void main(String[] args) {
        FirstGenerator generator = new FirstGenerator(20, 20);
        Maze maze = generator.generateMaze();
        if (!FirstSolverDFS.solve(maze, maze.getRandomPassageCell(), maze.getRandomPassageCell())) {
            System.out.println("No path");
        }
        MazeDrawer drawer2 = new MazeDrawer(maze, 20, 1000);
        PrintMaze.render(maze);
        drawer2.setVisible(true);
    }
}
