package raven.menu;

import java.awt.Component;
import java.awt.Image;
import javax.swing.JFrame;
import raven.components.MainForm;
import raven.swing.slider.PanelSlider;
import raven.swing.slider.SimpleTransition;

/**
 *
 * @author Raven
 */
public class FormManager {

    private static FormManager instance;
    private final JFrame frame;

    private boolean menuShowing = true;
    private final PanelSlider panelSlider;
    private final MainForm mainForm;
    private final Menu menu;

    public static void install(JFrame frame) {
        instance = new FormManager(frame);
    }

    private FormManager(JFrame frame) {
        this.frame = frame;
        panelSlider = new PanelSlider();
        mainForm = new MainForm();
        menu = new Menu(new MyDrawerBuilder());
        this.frame.getContentPane().add(panelSlider);
    }

    public static void showMenu() {
        instance.menuShowing = true;
        instance.panelSlider.addSlide(instance.menu, SimpleTransition.getShowMenuTransition(instance.menu.getDrawerBuilder().getDrawerWidth()));
    }

    public static void showForm(Component component) {
        if (instance.menuShowing == true) {
            instance.menuShowing = false;
            Image oldImage = instance.panelSlider.createOldImage();
            instance.mainForm.setForm(component);
            instance.panelSlider.addSlide(instance.mainForm, SimpleTransition.getSwitchFormTransition(oldImage, instance.menu.getDrawerBuilder().getDrawerWidth()));
        } else {
            instance.mainForm.showForm(component);
        }
    }

    public static void hideMenu() {
        instance.menuShowing = false;
        instance.panelSlider.addSlide(instance.mainForm, SimpleTransition.getHideMenuTransition(instance.menu.getDrawerBuilder().getDrawerWidth()));
    }
}
