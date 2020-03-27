package io.github.handicraftsman.jlayout;

import java.util.*;

public class Box extends LayoutManager<Box.BoxChildConfig> {
    private final List<BoxChildWrapper> children = new LinkedList<>();
    private final Map<IWidget, BoxChildWrapper> childMapper = new HashMap<>();
    private final BoxOrientation orientation;

    public Box(BoxOrientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public void addChild(
            IWidget widget,
            BoxChildConfig boxChildConfig,
            @SuppressWarnings("unused") /* we don't need this */ boolean force
    ) throws LayoutException {
        BoxChildWrapper wrapper = new BoxChildWrapper();
        wrapper.child = widget;
        wrapper.config = boxChildConfig;

        children.add(wrapper);
        childMapper.put(widget, wrapper);
        resizeChildren();
    }

    @Override
    public void removeChild(IWidget widget) throws LayoutException {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).child == widget) {
                children.remove(i);
                childMapper.remove(widget);
                resizeChildren();
                break;
            }
        }
    }

    @Override
    public void requestedSizeChanged(IWidget widget, Size newRequestedSize) throws LayoutException {
        resizeChildren();
    }

    @Override
    public Size getRequestedSize() {
        return null;
    }

    private int mainAxisOfSize(Size sz) {
        if (orientation == BoxOrientation.HORIZONTAL) {
            return sz.getWidth();
        } else {
            return sz.getHeight();
        }
    }

    private int mainAxisOfGeometry(Geometry g) {
        if (orientation == BoxOrientation.HORIZONTAL) {
            return g.getWidth();
        } else {
            return g.getHeight();
        }
    }

    private void setGeometryForChild(IWidget child, int mainAxisStart, int mainAxisSize) throws LayoutException {
        Geometry pg = getGeometry();
        if (orientation == BoxOrientation.HORIZONTAL) {
            Geometry g = new Geometry(
                    new Point(pg.getPointTL().getPosX() + mainAxisStart, pg.getPointTL().getPosY()),
                    new Point(pg.getPointTL().getPosX() + mainAxisStart + mainAxisSize, pg.getPointBR().getPosY())
            ).validate();
            child.setGeometry(g);
        } else {
            Geometry g = new Geometry(
                    new Point(pg.getPointTL().getPosX(), pg.getPointTL().getPosY() + mainAxisStart),
                    new Point(pg.getPointBR().getPosX(), pg.getPointTL().getPosY() + mainAxisStart + mainAxisSize)
            ).validate();
            child.setGeometry(g);
        }
    }

    private void setNonGrowGeometryForChild(IWidget child, int mainAxisStart) throws LayoutException {
        Geometry pg = getGeometry();
        if (orientation == BoxOrientation.HORIZONTAL) {
            Geometry g = new Geometry(
                    new Point(pg.getPointTL().getPosX() + mainAxisStart, pg.getPointTL().getPosY()),
                    new Point(pg.getPointTL().getPosX() + mainAxisStart + child.getRequestedSize().getWidth(), pg.getPointBR().getPosY())
            ).validate();
            child.setGeometry(g);
        } else {
            Geometry g = new Geometry(
                    new Point(pg.getPointTL().getPosX(), pg.getPointTL().getPosY() + mainAxisStart),
                    new Point(pg.getPointBR().getPosX(), pg.getPointTL().getPosY() + mainAxisStart + child.getRequestedSize().getHeight())
            ).validate();
            child.setGeometry(g);
        }
    }

    private void resizeChildren() throws LayoutException {
        int alloc = 0;
        int skip = 0;

        for (BoxChildWrapper child : children) {
            if (child.config.growCell) {
                alloc += 1;
            } else {
                skip += mainAxisOfSize(child.child.getRequestedSize());
            }
        }

        int toSplit = mainAxisOfGeometry(getGeometry()) - skip;
        int splitSize = toSplit / alloc;

        int pos = 0;

        for (BoxChildWrapper child : children) {
            if (child.config.growCell) {
                setGeometryForChild(child.child, pos, splitSize);
                pos += splitSize;
            } else {
                setNonGrowGeometryForChild(child.child, pos);
                pos += mainAxisOfSize(child.child.getRequestedSize());
            }
        }

    }

    /*
    public static void main(String[] args) throws LayoutException {
        Box box = new Box(BoxOrientation.HORIZONTAL);
        box.setGeometry(new Geometry(10, 10, 1000, 1000));

        SimpleWidget sw1 = new SimpleWidget();
        box.addChild(sw1, new Box.BoxChildConfig(true), false);

        SimpleWidget sw2 = new SimpleWidget();
        sw2.setRequestedSize(new Size(128, 128));
        box.addChild(sw2, new Box.BoxChildConfig(false), false);

        SimpleWidget sw3 = new SimpleWidget();
        box.addChild(sw3, new Box.BoxChildConfig(true), false);

        System.out.println(String.format("sw1 %s", sw1.getGeometry().inspect()));
        System.out.println(String.format("sw2 %s", sw2.getGeometry().inspect()));
        System.out.println(String.format("sw3 %s", sw3.getGeometry().inspect()));
    }
    */

    public BoxOrientation getOrientation() {
        return orientation;
    }

    public enum BoxOrientation {
        HORIZONTAL,
        VERTICAL
    }

    public static class BoxChildConfig {
        private final boolean growCell;

        public BoxChildConfig(boolean growCell) {
            this.growCell = growCell;
        }

        public boolean getGrowCell() {
            return growCell;
        }
    }

    private class BoxChildWrapper {
        public IWidget child;
        public BoxChildConfig config;
    }
}
