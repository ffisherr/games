import block.Block;

import java.util.Arrays;

import static block.BlockGenerator.generateBlock;

public class Panel {

    private static final int PANEL_HEIGHT = 15;
    private static final int PANEL_WEIGHT = 10;
    private static final char BLOCK_ELEMENT = '*';
    private static final char EMPTY_ELEMENT = ' ';

    private final char[][] field;
    private Block block;
    private boolean gameOver = false;

    public Panel() {
        field = new char[PANEL_HEIGHT][PANEL_WEIGHT];
        for (var line : field) {
            Arrays.fill(line, EMPTY_ELEMENT);
        }
    }

    public char[][] getField() {
        final var fieldCopy = new char[PANEL_HEIGHT][PANEL_WEIGHT];
        for (int i = 0; i < PANEL_HEIGHT; i++) {
            System.arraycopy(field[i], 0, fieldCopy[i], 0, PANEL_WEIGHT);
        }
        if (block != null) {
            blockToField(fieldCopy);
        }
        return fieldCopy;
    }

    /**
     * Game progress changer
     * @return false if game is over, true otherwise
     */
    public boolean tick() {
        if (gameOver) return false;
        boolean justCreated = block == null;
        if (block == null) {
            block = generateBlock(PANEL_WEIGHT);
        }
        if (canBeMovedDown()) {
            block.moveDown();
        } else {
            System.out.println("Block " + block + " cannot be moved down");
            blockToField(field);
            block = null;
            if (justCreated) gameOver = true;
            else {
                levelCleanUp();
                suppressLevels();
            }
            return !justCreated;
        }
        return true;
    }

    public void move(Command command) {
        if (command != null)
            performCommand(command);
    }

    private boolean levelCleanUp() {
        System.out.println("Start cleaning");
        boolean cleaned = false;
        for (int y = 0; y < field.length; y++) {
            boolean cleanLevel = true;
            for (int x = 0; x < field[y].length; x++) {
                if (field[y][x] != BLOCK_ELEMENT) {
                    cleanLevel = false;
                    break;
                }
            }
            if (cleanLevel) {
                System.out.println("Cleaning level " + y);
                cleaned = true;
                Arrays.fill(field[y], EMPTY_ELEMENT);
                break;
            }
        }
        if (cleaned) {
            return levelCleanUp();
        }
        return false;
    }

    private void suppressLevels() {
        int levelToMove = -1;
        for (int y = PANEL_HEIGHT-1; y >= 0; y--) {
            boolean isEmpty = true;
            for (int x = 0; x < PANEL_WEIGHT; x++) {
                if (field[y][x] == BLOCK_ELEMENT) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                levelToMove = y;
                break;
            }
        }
        if (levelToMove >= 0 && levelToMove < PANEL_HEIGHT-1) {
            System.out.println(levelToMove);
            for (int y = levelToMove; y >= 0; y--) {
                System.arraycopy(field[y], 0, field[y+1], 0, field[y].length);
            }
            Arrays.fill(field[0], EMPTY_ELEMENT);
            suppressLevels();
        }
    }

    private void performCommand(Command command) {
        if (block == null) return;
        switch (command) {
            case LEFT -> {if (canBeMovedLeft()) block.moveLeft();}
            case RIGHT -> {if (canBeMovedRight()) block.moveRight();}
            case ROTATE_RIGHT -> maybeRotate();
            case DOWN -> {if (canBeMovedDown()) block.moveDown();}
        }
    }

    private void maybeRotate() {
        final var possibleBlock = block.copy();
        possibleBlock.rotate();
        final var structure = possibleBlock.getStructure();
        for (int y = 0; y < structure.length; y++) {
            if (possibleBlock.getY() + y >= field.length) {
                return;
            }
            for (int x = 0; x < structure[y].length; x++) {
                if (possibleBlock.getX() + x >= field[y+possibleBlock.getY()].length) {
                    return;
                }
                if (field[y+possibleBlock.getY()][possibleBlock.getX()+x]==BLOCK_ELEMENT
                        && structure[y][x] == BLOCK_ELEMENT) {
                    return;
                }
            }
        }
        block = possibleBlock;
    }

    private boolean canBeMovedDown() {
        final var blockStructure = block.getStructure();
        for (int y = 0; y < blockStructure.length; y++) {
            final var yCoord = block.getY() + y;
            for (int x = 0; x < blockStructure[y].length; x++) {
                if (blockStructure[y][x] == BLOCK_ELEMENT) {
                    final var xCoord = block.getX() + x;
                    if (yCoord + 1 >= PANEL_HEIGHT) {
                        System.out.println("Stop coords " + (yCoord+1) + " " + xCoord);
                        return false;
                    }
                    if (field[yCoord+1][xCoord] == BLOCK_ELEMENT) {
                        System.out.println("Stop coord: " + (yCoord+1) + " " + xCoord);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canBeMovedLeft() {
        if (block.getX() == 0) return false;
        final var blockStructure = block.getStructure();
        for (int y = 0; y < blockStructure.length; y++) {
            if (blockStructure[y][0] == BLOCK_ELEMENT && field[block.getY()+y][block.getX()-1] == BLOCK_ELEMENT)
                return false;
        }
        return true;
    }

    private boolean canBeMovedRight() {
        final var blockStructure = block.getStructure();
        for (int y = 0; y < blockStructure.length; y++) {
            final var line = blockStructure[y];
            if (block.getX()+line.length >= PANEL_WEIGHT) return false;
            if (line[line.length-1] == BLOCK_ELEMENT && field[block.getY()+y][block.getX()+line.length] == BLOCK_ELEMENT)
                return false;
        }
        return true;
    }

    private void blockToField(char[][] field) {
        final var blockStructure = block.getStructure();
        final var initX = block.getX();
        final var initY = block.getY();
        for (int y = 0; y < blockStructure.length; y++) {
            System.arraycopy(blockStructure[y], 0, field[initY + y], initX, blockStructure[y].length);
        }
    }
}
