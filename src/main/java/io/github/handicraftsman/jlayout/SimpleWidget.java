package io.github.handicraftsman.jlayout;

public class SimpleWidget implements IWidget {
    private LayoutManager<?> parent = null;
    private Geometry geometry = null;
    private Size requestedSize = new Size(0, 0);

    @Override
    public LayoutManager<?> getParent() {
        return this.parent;
    }

    @Override
    public void setParent(LayoutManager<?> parent) throws LayoutException {
        if (this.parent != null) {
            throw new LayoutException("Unable to set parent - it already exists");
        }
        this.parent = parent;
    }

    @Override
    public Geometry getGeometry() {
        return this.geometry;
    }

    @Override
    public void setGeometry(Geometry geometry) throws LayoutException {
        this.geometry = geometry;
    }

    @Override
    public void draw() {

    }

    @Override
    public Size getRequestedSize() {
        return requestedSize;
    }

    public void setRequestedSize(Size size) {
        requestedSize = size;
    }
}
