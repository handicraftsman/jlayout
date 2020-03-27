package io.github.handicraftsman.jlayout;

public class PaddingBox extends LayoutManager<PaddingBox.PaddingBoxChildConfig> {
    private IWidget child = null;
    private PaddingBoxChildConfig config = null;

    @Override
    public void addChild(IWidget child, PaddingBoxChildConfig paddingBoxConfig, boolean force) throws LayoutException {
        if (this.child != null && !force) {
            throw new LayoutException("This PaddingBox already has a child and force flag is not set");
        }
        this.config = paddingBoxConfig;
        this.child = child;
        child.setParent(this);
        if (getGeometry() != null) setChildGeometry();
    }

    @Override
    public void removeChild(IWidget child) {
        if (child == this.child) {
            this.child = null;
            this.config = null;
        }
    }

    @Override
    public void requestedSizeChanged(IWidget widget, Size newRequestedSize) throws LayoutException {
        LayoutManager<?> parent = getParent();
        if (parent != null) {
            parent.requestedSizeChanged(this, getRequestedSize());
        }
    }

    @Override
    public void draw() {
        child.draw();
    }

    @Override
    public Size getRequestedSize() {
        Size childRequestedSize = child.getRequestedSize();
        return new Size(
                config.paddingLeft + config.paddingRight + childRequestedSize.getWidth(),
                config.paddingTop + config.paddingBottom + childRequestedSize.getHeight()
        );
    }

    @Override
    public void setGeometry(Geometry geometry) throws LayoutException {
        super.setGeometry(geometry);
        if (getGeometry() != null) setChildGeometry();
    }

    private void setChildGeometry() throws LayoutException {
        if (child == null) return;
        PaddingBoxChildConfig config = this.config;
        child.setGeometry(this.getGeometry().mutate(
                pointTL -> pointTL.mutate(
                        x -> x + config.paddingLeft,
                        y -> y + config.paddingTop
                ),
                pointBR -> pointBR.mutate(
                        x -> x - config.paddingRight,
                        y -> y - config.paddingBottom
                )
        ).validate());
    }

    public static class PaddingBoxChildConfig {
        public int getPaddingTop() {
            return paddingTop;
        }

        public int getPaddingRight() {
            return paddingRight;
        }

        public int getPaddingBottom() {
            return paddingBottom;
        }

        public int getPaddingLeft() {
            return paddingLeft;
        }

        private final int paddingTop, paddingRight, paddingBottom, paddingLeft;

        public PaddingBoxChildConfig(int padding) {
            this(padding, padding, padding, padding);
        }

        public PaddingBoxChildConfig(int paddingTopBottom, int paddingLeftRight) {
            this(paddingTopBottom, paddingLeftRight, paddingTopBottom, paddingLeftRight);
        }

        public PaddingBoxChildConfig(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
            this.paddingTop = paddingTop;
            this.paddingRight = paddingRight;
            this.paddingBottom = paddingBottom;
            this.paddingLeft = paddingLeft;
        }
    }
}
