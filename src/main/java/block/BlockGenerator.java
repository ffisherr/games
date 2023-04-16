package block;

import java.util.Map;
import java.util.Random;

public class BlockGenerator {

    private static final int POSSIBLE_POSITIONS = 4;
    private static final Random random = new Random();
    private static final Map<Integer, BlockType> TYPES = Map.of(
            0, BlockType.LINE,
            1, BlockType.CUBE,
            2, BlockType.LEFT_CURV,
            3, BlockType.RIGHT_CURV,
            4, BlockType.LEFT_G,
            5, BlockType.RIGHT_G
    );

    public static Block generateBlock(int xLim) {
        final var typeKey = random.nextInt(BlockType.values().length);
//        final var position = random.nextInt(POSSIBLE_POSITIONS);
        final var type = TYPES.get(typeKey);
        final var x = random.nextInt(xLim-type.initWidth);
        return new Block(type, x, 0);
    }
}
