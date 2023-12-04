package raven.menu;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import raven.drawer.component.DrawerBuilder;
import raven.drawer.component.DrawerPanel;

/**
 *
 * @author Raven
 */
public class Menu extends JPanel {

    private final DrawerBuilder drawerBuilder;

    public DrawerBuilder getDrawerBuilder() {
        return drawerBuilder;
    }

    public Menu(DrawerBuilder drawerBuilder) {
        this.drawerBuilder = drawerBuilder;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Drawer.background");

        setLayout(new MigLayout("wrap,fill", "[fill," + drawerBuilder.getDrawerWidth() + "!]", "[fill]"));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    FormManager.hideMenu();
                }
            }
        });
        DrawerPanel drawerPanel = new DrawerPanel(drawerBuilder);
        drawerPanel.addMouseListener(new MouseAdapter() {
        });
        drawerBuilder.build(drawerPanel);
        add(drawerPanel);
    }
}
