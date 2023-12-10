package raven.swing.slider;

import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.UIManager;

/**
 *
 * @author Raven
 */
public class SimpleTransition {

    private static final float ZOOM_IN = 0.1f;

    public static SliderTransition getDefaultTransition(boolean toRight) {
        return new SliderTransition() {
            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                g.drawImage(image, 0, 0, null);
                g.dispose();
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                Graphics2D g2 = (Graphics2D) g;
                int move = UIScale.scale(200);
                int x = (int) (move * (1f - animate));
                if (toRight) {
                    x = -x;
                }
                g2.setComposite(AlphaComposite.SrcOver.derive(animate));
                g2.drawImage(image, x, 0, null);
                g2.dispose();
            }
        };
    }

    public static SliderTransition getShowMenuTransition(int drawerWidth) {

        return new SliderTransition() {

            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                renderImage(component, g, image, width, height, drawerWidth, animate);
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                if (animate != 1) {
                    g.drawImage(image, 0, 0, null);
                }
                g.dispose();
            }

            @Override
            public boolean closeAfterAnimation() {
                return false;
            }

            @Override
            public void render(Component component, Graphics g, Image imageOld, Image imageNew, int width, int height, float animate) {
                renderImageNew(component, g.create(), imageNew, width, height, animate);
                renderImageOld(component, g.create(), imageOld, width, height, animate);
            }
        };
    }

    public static SliderTransition getHideMenuTransition(int drawerWidth) {
        return new SliderTransition() {

            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                if (animate != 1) {
                    g.drawImage(image, 0, 0, null);
                }
                g.dispose();
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                renderImage(component, g, image, width, height, drawerWidth, 1f - animate);
            }
        };
    }

    public static SliderTransition getSwitchFormTransition(Image oldFormImage, int drawerWidth) {
        return new SliderTransition() {
            @Override
            public void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate) {
                g.drawImage(image, 0, 0, null);
                g.dispose();
            }

            @Override
            public void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate) {
                float newAnimate = 1f - animate;
                Graphics2D g2 = (Graphics2D) g;
                FlatUIUtils.setRenderingHints(g2);
                float zoomIn = ZOOM_IN;
                boolean ltr = component.getComponentOrientation().isLeftToRight();
                int x = (int) ((ltr ? newAnimate : -newAnimate) * UIScale.scale(drawerWidth + 60));
                if (!ltr) {
                    x += width * (newAnimate * zoomIn);
                }
                int y = (height / 2);
                int space = UIScale.scale(20);
                int arc = UIScale.scale(30);
                g2.translate(x, y);
                if (zoomIn > 0) {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    float scale = 1f - newAnimate * zoomIn;
                    g2.scale(scale, scale);
                }
                g2.translate(0, -y);
                g2.setColor(UIManager.getColor("Panel.background"));
                g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));
                g2.fill(new RoundRectangle2D.Double(((ltr ? -space : space) * 2) * newAnimate, (space * 2) * newAnimate, width, height - (space * 4) * newAnimate, arc, arc));
                g2.setComposite(AlphaComposite.SrcOver.derive(0.4f + (animate * 0.6f)));
                g2.drawImage(image, (int) ((ltr ? -space : space) * newAnimate), (int) (space * newAnimate), width, (int) (height - (space * 2) * newAnimate), null);
                g2.setComposite(AlphaComposite.SrcOver.derive(newAnimate));
                g2.drawImage(oldFormImage, 0, 0, null);
                g2.dispose();
            }
        };
    }

    private static void renderImage(Component component, Graphics g, Image image, int width, int height, int drawerWidth, float animate) {
        Graphics2D g2 = (Graphics2D) g;
        FlatUIUtils.setRenderingHints(g2);
        float zoomIn = ZOOM_IN;
        boolean ltr = component.getComponentOrientation().isLeftToRight();
        int x = (int) ((ltr ? animate : -animate) * UIScale.scale(drawerWidth + 60));
        if (!ltr) {
            x += width * (animate * zoomIn);
        }
        int y = (height / 2);
        int space = UIScale.scale(20);
        int arc = UIScale.scale(30);
        g2.translate(x, y);
        if (zoomIn > 0) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            float scale = 1f - animate * zoomIn;
            g2.scale(scale, scale);
        }
        g2.translate(0, -y);
        g2.setColor(UIManager.getColor("Panel.background"));
        g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));
        g2.fill(new RoundRectangle2D.Double(((ltr ? -space : space) * 2) * animate, (space * 2) * animate, width, height - (space * 4) * animate, arc, arc));
        g2.fill(new RoundRectangle2D.Double((ltr ? -space : space) * animate, space * animate, width, height - (space * 2) * animate, arc, arc));
        g2.setComposite(AlphaComposite.SrcOver);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
}
