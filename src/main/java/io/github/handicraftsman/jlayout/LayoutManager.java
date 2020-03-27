package io.github.handicraftsman.jlayout;

public abstract class LayoutManager<ChildConfig> extends SimpleWidget {
    private LayoutManager<?> parent = null;
    private Geometry geometry = null;

    public abstract void addChild(IWidget widget, ChildConfig config, boolean force) throws LayoutException;
    public abstract void removeChild(IWidget widget) throws LayoutException;

    public abstract void requestedSizeChanged(IWidget widget, Size newRequestedSize) throws LayoutException;
}
