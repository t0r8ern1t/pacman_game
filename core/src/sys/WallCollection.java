package sys;

public enum WallCollection {
    PDOWN(new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}}),
    GRIGHT(new int[][]{{1, 1, 1, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}}),
    GLEFT(new int[][]{{0, 1, 1, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 0}}),
    DOT(new int[][]{{0, 0, 0, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}}),
    LINELEFT(new int[][]{{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}}),
    LINEDOWN(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}, {1, 1, 1, 1}});

    private int wall[][];

    WallCollection(int[][] matrix) {
        wall = matrix;
    }
    public int[][] getWall() { return wall; }

}
