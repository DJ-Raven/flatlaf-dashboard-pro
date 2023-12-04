package raven.swing.slider;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Raven
 */
public abstract class SliderTransition {

    public abstract void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate);

    public abstract void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate);

    public boolean closeAfterAnimation() {
        return true;
    }

    public void render(Component component, Graphics g, Image imageOld, Image imageNew, int width, int height, float animate) {
        renderImageOld(component, g.create(), imageOld, width, height, animate);
        renderImageNew(component, g.create(), imageNew, width, height, animate);

    }
}
