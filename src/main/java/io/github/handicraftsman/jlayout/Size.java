package io.github.handicraftsman.jlayout;

import java.util.function.Function;

public class Size {
    private final int width, height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Size mutate(Function<Integer, Integer> mutateWidth, Function<Integer, Integer> mutateHeight) {
        return new Size(
                mutateWidth != null ? mutateWidth.apply(this.width) : this.width,
                mutateHeight != null ? mutateHeight.apply(this.height) : this.height
        );
    }
}
