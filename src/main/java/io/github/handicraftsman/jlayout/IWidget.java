package io.github.handicraftsman.jlayout;

public interface IWidget {
    LayoutManager<?> getParent();
    void setParent(LayoutManager<?> parent) throws LayoutException;
    Geometry getGeometry();
    void setGeometry(Geometry geometry) throws LayoutException;
    void draw();

    /**
     * Returns size needed to display content shown by the widget.
     * Please DON'T call any methods from parent here nor depend on widget's geometry as this would invoke
     * a recursion.
     */
    Size getRequestedSize();
}
