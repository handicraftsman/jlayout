package io.github.handicraftsman.jlayout;

import java.util.function.Function;

public class Geometry {
    public Point getPointTL() {
        return pointTL;
    }

    public Point getPointBR() {
        return pointBR;
    }

    private final Point pointTL, pointBR;

    public Geometry(Point pointTL, Point pointBR) {
        this.pointTL = pointTL;
        this.pointBR = pointBR;
    }

    public Geometry(int x1, int y1, int x2, int y2) {
        this.pointTL = new Point(x1, y1);
        this.pointBR = new Point(x2, y2);
    }

    public int getWidth() {
        return Math.abs(getPointTL().getPosX() - getPointBR().getPosX());
    }

    public int getHeight() {
        return Math.abs(getPointTL().getPosY() - getPointBR().getPosY());
    }

    public Geometry mutate(Function<Point, Point> mutateTL, Function<Point, Point> mutateBR) {
        return new Geometry(
                mutateTL != null ? mutateTL.apply(this.pointTL) : this.pointTL,
                mutateBR != null ? mutateBR.apply(this.pointBR) : this.pointBR
        );
    }

    public Geometry validate() throws LayoutException {
        if ((pointTL.getPosX() > pointBR.getPosX()) || (pointTL.getPosY() > pointBR.getPosY())) {
            // Fallback :)
            return new Geometry(pointBR, pointBR);
        }
        return this;
    }

    public String inspect() {
        return String.format(
                "((%d %d) (%d %d))",
                this.pointTL.getPosX(),
                this.pointTL.getPosY(),
                this.pointBR.getPosX(),
                this.pointBR.getPosY()
        );
    }
}
