package raven.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;

/**
 *
 * @author Raven
 */
public class SimpleForm extends JPanel {

    public SimpleForm() {
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"
                + "background:null");
    }
}
