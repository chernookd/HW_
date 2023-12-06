package edu.hw9.Task3;

public class Cell {
    private final int row;
    private final int column;

    private Cell previous;

    private State state;

    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    public Cell getPrevious() {
        return previous;
    }

    public enum State {

        START,
        END,
        WALL,
        PASSAGE,
        PATH;
    }


    private boolean visited;

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }

    public Cell(int x, int y) {
        this.row = x;
        this.column = y;
        this.visited = false;
        this.state = State.WALL;
    }

    public Cell(int x, int y, State state) {
        this.row = x;
        this.column = y;
        this.visited = false;
        this.state = state;
    }

    public boolean isVisited() {
        return visited;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
