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

    private final PanelSlider panelSlider;
    private final MainForm mainForm;
    private final Menu menu;
    private Image oldFormImage;

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
        instance.oldFormImage = instance.panelSlider.createImage(instance.mainForm);
        instance.panelSlider.addSlide(instance.menu, SimpleTransition.getShowMenuTransition(instance.menu.getDrawerBuilder().getDrawerWidth()));
    }

    public static void showForm(Component component) {
        instance.mainForm.setForm(component);
        instance.panelSlider.addSlide(instance.mainForm, SimpleTransition.getSwitchFormTransition(instance.oldFormImage, instance.menu.getDrawerBuilder().getDrawerWidth()));
    }

    public static void hideMenu() {
        instance.panelSlider.addSlide(instance.mainForm, SimpleTransition.getHideMenuTransition(instance.menu.getDrawerBuilder().getDrawerWidth()));
    }
}
