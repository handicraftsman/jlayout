package io.github.handicraftsman.jlayout;

import java.util.function.Function;

public class Point {
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    private final int posX, posY;

    public Point(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Point mutate(Function<Integer, Integer> mutateX, Function<Integer, Integer> mutateY) {
        return new Point(
                mutateX != null ? mutateX.apply(this.posX) : this.posX,
                mutateY != null ? mutateY.apply(this.posY) : this.posY
        );
    }
}
