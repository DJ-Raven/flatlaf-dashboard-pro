package raven.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import raven.menu.FormManager;
import raven.swing.slider.PanelSlider;

/**
 *
 * @author Raven
 */
public class MainForm extends JPanel {

    public MainForm() {
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"
                + "arc:30");
        setLayout(new MigLayout("wrap,fillx", "[fill]", ""));
        header = createHeader();
        panelSlider = new PanelSlider();
        add(header);
        add(panelSlider);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 3"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");

        JButton cmdMenu = createButton(new FlatSVGIcon("raven/resources/icon/menu.svg"));
        cmdMenu.addActionListener(e -> {
            FormManager.showMenu();
        });
        panel.add(cmdMenu);
        return panel;
    }

    private JButton createButton(Icon icon) {
        JButton button = new JButton(icon);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Button.toolbar.background;"
                + "arc:10;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        return button;
    }

    public void setForm(Component component) {
        panelSlider.addSlide(component, null);
    }

    private JPanel header;
    private PanelSlider panelSlider;
}
