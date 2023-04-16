package block;

import java.util.Arrays;

public class Block {

    private char[][] structure;

    private boolean moving = true;
    private int x;
    private int y;
    public Block(BlockType type, int x, int y) {
        this.x = x;
        this.y = y;
        structure = type.figure;
    }

    public Block(BlockType type) {
        this.x = 0;
        this.y = 0;
        structure = type.figure;
    }

    public Block(char[][] structure, int x, int y) {
        this.structure = structure;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Block{" +
                "structure=" + Arrays.toString(structure) +
                ", moving=" + moving +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public void stopMoving() {
        this.moving = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char[][] getStructure() {
        return structure;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void rotate() {
        final var len = structure[0].length;
        final var newStr = new char[len][];
        for (int x = 0; x < len; x++) {
            final var newLine = new char[structure.length];
            for (int y = 0; y < structure.length; y++) {
                newLine[y] = structure[structure.length-y-1][x];
            }
            newStr[x] = newLine;
        }
        structure = newStr;
    }

    public Block copy() {
        return new Block(structure, x, y);
    }
}
