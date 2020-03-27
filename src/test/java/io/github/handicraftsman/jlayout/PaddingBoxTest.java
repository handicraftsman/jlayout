package io.github.handicraftsman.jlayout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaddingBoxTest {
    @Test
    void testPaddingBox() throws LayoutException {
        PaddingBox paddingBox = new PaddingBox();
        paddingBox.setGeometry(new Geometry(10, 10, 1000, 1000));
        SimpleWidget simpleWidget = new SimpleWidget();
        paddingBox.addChild(
                simpleWidget,
                new PaddingBox.PaddingBoxChildConfig(
                        2,
                        4,
                        8,
                        16
                ),
                false
        );
        Geometry simpleWidgetGeometry = simpleWidget.getGeometry();
        assertEquals(10+2, simpleWidgetGeometry.getPointTL().getPosY());
        assertEquals(1000-4, simpleWidgetGeometry.getPointBR().getPosX());
        assertEquals(1000-8, simpleWidgetGeometry.getPointBR().getPosY());
        assertEquals(10+16, simpleWidgetGeometry.getPointTL().getPosX());
    }
}
