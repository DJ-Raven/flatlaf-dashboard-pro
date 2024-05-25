package raven.menu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import raven.drawer.component.DrawerPanel;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.header.SimpleHeaderStyle;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import raven.drawer.component.menu.SimpleMenuStyle;
import raven.drawer.component.menu.data.Item;
import raven.drawer.component.menu.data.MenuItem;
import raven.forms.DashboardForm;
import raven.forms.InboxForm;
import raven.forms.ReadForm;
import raven.model.ModelUser;
import raven.swing.AvatarIcon;

/**
 *
 * @author Raven
 */
public class MyDrawerBuilder extends SimpleDrawerBuilder {

    private ModelUser user;
    private final ThemesChange themesChange;

    public void setUser(ModelUser user) {
        this.user = user;
        SimpleHeaderData headerData = header.getSimpleHeaderData();
        headerData.setTitle(user.getUserName());
        header.setSimpleHeaderData(headerData);
        rebuildMenu();
    }

    public MyDrawerBuilder() {
        themesChange = new ThemesChange();
    }

    @Override
    public Component getFooter() {
        return themesChange;
    }

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        AvatarIcon icon = new AvatarIcon(getClass().getResource("/raven/resources/image/profile.png"), 60, 60, 999);
        icon.setBorder(2);
        return new SimpleHeaderData()
                .setIcon(icon)
                .setTitle("Ra Ven")
                .setDescription("raven@gmail.com")
                .setHeaderStyle(new SimpleHeaderStyle() {

                    @Override
                    public void styleTitle(JLabel label) {
                        label.putClientProperty(FlatClientProperties.STYLE, ""
                                + "[light]foreground:#FAFAFA");
                    }

                    @Override
                    public void styleDescription(JLabel label) {
                        label.putClientProperty(FlatClientProperties.STYLE, ""
                                + "[light]foreground:#E1E1E1");
                    }
                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData();
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {

        MenuItem items[] = new MenuItem[]{
            new Item.Label("MAIN"),
            new Item("Dashboard", "dashboard.svg"),
            new Item.Label("WEB APP"),
            new Item("Email", "email.svg")
            .subMenu("Inbox")
            .subMenu("Read")
            .subMenu(
            new Item("Group Read")
            .subMenu("Read 1")
            .subMenu("Read 2")
            .subMenu(
            new Item("Group Item")
            .subMenu("Item 1")
            .subMenu("Item 2")
            .subMenu("Item 3")
            .subMenu("Item 4")
            .subMenu("Item 5")
            .subMenu("Item 6")
            )
            .subMenu("Read 3")
            .subMenu("Read 4")
            .subMenu("Read 5")
            )
            .subMenu("Compost"),
            new Item("Chat", "chat.svg"),
            new Item("Calendar", "calendar.svg"),
            new Item.Label("COMPONENT"),
            new Item("Advanced UI", "ui.svg")
            .subMenu("Cropper")
            .subMenu("Owl Carousel")
            .subMenu("Sweet Alert"),
            new Item("Forms", "forms.svg")
            .subMenu("Basic Elements")
            .subMenu("Advanced Elements")
            .subMenu("SEditors")
            .subMenu("Wizard"),
            new Item.Label("OTHER"),
            new Item("Charts", "chart.svg")
            .subMenu("Apex")
            .subMenu("Flot")
            .subMenu("Sparkline"),
            new Item("Icons", "icon.svg")
            .subMenu("Feather Icons")
            .subMenu("Flag Icons")
            .subMenu("Mdi Icons"),
            new Item("Special Pages", "page.svg")
            .subMenu("Blank page")
            .subMenu("Faq")
            .subMenu("Invoice")
            .subMenu("Profile")
            .subMenu("Pricing")
            .subMenu("Timeline"),
            new Item("Logout", "logout.svg")
        };

        SimpleMenuOption simpleMenuOption = new SimpleMenuOption() {
            @Override
            public Icon buildMenuIcon(String path, float scale) {
                FlatSVGIcon icon = new FlatSVGIcon(path, scale);
                FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter();
                colorFilter.add(Color.decode("#969696"), Color.decode("#FAFAFA"), Color.decode("#969696"));
                icon.setColorFilter(colorFilter);
                return icon;
            }
        };

        simpleMenuOption.setMenuValidation(new MenuValidation() {

            private boolean checkMenu(int[] index, int[] indexHide) {
                if (index.length == indexHide.length) {
                    for (int i = 0; i < index.length; i++) {
                        if (index[i] != indexHide[i]) {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            }

            @Override
            public boolean menuValidation(int[] index) {
                if (user == null) {
                    return false;
                }
                if (!user.isAdmin()) {
                    // non user admin going to hide
                    boolean act
                            // `Email`->`Gropu Read`->`Read 3`
                            = checkMenu(index, new int[]{1, 2, 3})
                            // `Email`->`Gropu Read`->`Read 5`
                            && checkMenu(index, new int[]{1, 2, 5})
                            // `Email`->`Group Read`->`Group Item->`Item 4`
                            && checkMenu(index, new int[]{1, 2, 2, 3})
                            // `Advanced UI`->`Owl Carousel`
                            && checkMenu(index, new int[]{4, 1})
                            // `Special Pages`
                            && checkMenu(index, new int[]{8});
                    return act;
                }
                return true;
            }
        });

        simpleMenuOption.setMenuStyle(new SimpleMenuStyle() {
            @Override
            public void styleMenuItem(JButton menu, int[] index) {
                menu.putClientProperty(FlatClientProperties.STYLE, ""
                        + "[light]foreground:#FAFAFA;"
                        + "arc:10");
            }

            @Override
            public void styleMenu(JComponent component) {
                component.putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:$Drawer.background");
            }

            @Override
            public void styleLabel(JLabel label) {
                label.putClientProperty(FlatClientProperties.STYLE, ""
                        + "[light]foreground:darken(#FAFAFA,15%);"
                        + "[dark]foreground:darken($Label.foreground,30%)");
            }
        });
        simpleMenuOption.addMenuEvent(new MenuEvent() {
            @Override
            public void selected(MenuAction action, int[] index) {
                if (index.length == 1) {
                    if (index[0] == 0) {
                        FormManager.showForm(new DashboardForm());
                    }
                    if (index[0] == 9) {
                        // logout
                        FormManager.logout();
                    }
                } else if (index.length == 2) {
                    if (index[0] == 1) {
                        if (index[1] == 0) {
                            FormManager.showForm(new InboxForm());
                        } else if (index[1] == 1) {
                            FormManager.showForm(new ReadForm());
                        }
                    }
                }
            }
        });

        simpleMenuOption.setMenus(items)
                .setBaseIconPath("raven/resources/menu")
                .setIconScale(0.45f);
        return simpleMenuOption;
    }

    @Override

    public void build(DrawerPanel drawerPanel) {
        drawerPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Drawer.background");
    }

    @Override
    public int getDrawerWidth() {
        return 270;
    }
}
