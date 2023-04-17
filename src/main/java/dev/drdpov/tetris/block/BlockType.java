package dev.drdpov.tetris.block;

public enum BlockType {

    LINE(new char[][]{
            {'*'}, // 0 0
            {'*'}, // 1 0
            {'*'}, // 2 0
            {'*'}  // 3 0
    }, 1),
    CUBE(new char[][]{
            {'*', '*'},
            {'*', '*'}
    }, 2),
    LEFT_CURV(new char[][]{
            {'*', '*', ' '},
            {' ', '*', '*'}
    }, 3),
    RIGHT_CURV(new char[][]{
            {' ', '*', '*'},
            {'*', '*', ' '}
    }, 3),
    LEFT_G(new char[][]{
            {'*', '*'},
            {' ', '*'},
            {' ', '*'}
    }, 2),
    RIGHT_G(new char[][]{
            {'*', '*'},
            {'*', ' '},
            {'*', ' '}
    }, 2),
    SMALL_T(new char[][]{
            {'*', '*', '*'},
            {' ', '*', ' '}
    }, 3);

    final char[][] figure;
    final int initWidth;

    BlockType(char[][] figure, int initWidth) {
        this.figure = figure;
        this.initWidth = initWidth;
    }
}
