package edu.project2;

import java.util.ArrayList;
import java.util.Random;

public class FirstGenerator implements Generator {

    private final int height;
    private final int width;
    private final Cell[][] cellArray;
    private final Random random;

    final static int MIN_SIZE_OF_FIELD = 6;

    public FirstGenerator(int height, int width) throws IllegalArgumentException {
        if (height < MIN_SIZE_OF_FIELD || width < MIN_SIZE_OF_FIELD) {
            throw new IllegalArgumentException("Height and width must be greater than 6.");
        }
        this.height = height + (height % 2 == 0 ? 1 : 0) + 2;
        this.width = width + (width % 2 == 0 ? 1 : 0) + 2;
        this.cellArray = new Cell[this.height][this.width];
        this.random = new Random();
    }

    @Override
    public Maze generateMaze() {
        initializeCells();
        generateOldosBroader();
        return new Maze(this.height, this.width, cellArray);
    }

    private void initializeCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cellArray[i][j] = new Cell(i, j);
            }
        }
    }

    private void generateOldosBroader() {
        Cell startCell = cellArray[1][1];
        startCell.setState(Cell.State.PASSAGE);
        startCell.setVisited(true);

        while (!isTheMazeReady()) {
            Cell randNeighbor = getRandomNeighbor(startCell);
            if (randNeighbor.getVisited()) {
                startCell = randNeighbor;
                continue;
            } else {
                startCell.setState(Cell.State.PASSAGE);
                deleteWall(startCell, randNeighbor);
                randNeighbor.setState(Cell.State.PASSAGE);
                randNeighbor.setVisited(true);
                startCell = randNeighbor;
            }
        }
        createBoarders();
    }

    private boolean isTheMazeReady() {
        for (int i = 1; i < height; i += 2) {
            for (int j = 1; j < width; j += 2) {
                if (!cellArray[i][j].getVisited()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void deleteWall(Cell startCell, Cell randNeighbor) {
        cellArray[(startCell.getRow() + randNeighbor.getRow()) / 2]
            [(startCell.getColumn() + randNeighbor.getColumn()) / 2]
            .setState(Cell.State.PASSAGE);
    }

    private Cell getRandomNeighbor(Cell cell) {
        ArrayList<Cell> neighbors = getListNeighbors(cell);
        int randomIndex = random.nextInt(neighbors.size());
        return neighbors.get(randomIndex);
    }

    private ArrayList<Cell> getListNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        if (cell.getRow() + 2 < height - 1) {
            neighbors.add(cellArray[cell.getRow() + 2][cell.getColumn()]);
        }
        if (cell.getRow() - 2 > 0) {
            neighbors.add(cellArray[cell.getRow() - 2][cell.getColumn()]);
        }
        if (cell.getColumn() + 2 < width - 1) {
            neighbors.add(cellArray[cell.getRow()][cell.getColumn() + 2]);
        }
        if (cell.getColumn() - 2 > 0) {
            neighbors.add(cellArray[cell.getRow()][cell.getColumn() - 2]);
        }
        return neighbors;
    }

    private void createBoarders() {
        for (int i = 1; i < width; ++i) {
            cellArray[0][i].setState(Cell.State.WALL);
            cellArray[height - 1][i].setState(Cell.State.WALL);
        }
        for (int i = 0; i < height; i++) {
            cellArray[i][0].setState(Cell.State.WALL);
            cellArray[i][width - 1].setState(Cell.State.WALL);
        }
    }
}
