package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.io.FileLoader;
import be.elgem.gameoflife.io.FileSaver;
import be.elgem.gameoflife.render.EGridVisibility;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private GamePanel gamePanel;

    private FileSaver fileSaver;
    private FileLoader fileLoader;

    private JMenu file, option, help;

    public MenuBar(GamePanel gamePanel) {
        super();

        this.gamePanel = gamePanel;

        fileSaver = new FileSaver(gamePanel);
        fileLoader = new FileLoader(gamePanel);

        file = createFileMenu();
        option = createOptionMenu();
        help = createHelpMenu();

        add(file);
        add(option);
        add(help);
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem open = createJMenuItem("Open", fileLoader::openFile);
        JMenuItem save = createJMenuItem("Save", fileSaver::saveFile);

        fileMenu.add(open);
        fileMenu.add(save);

        return fileMenu;
    }

    private JMenu createOptionMenu() {
        JMenu optionMenu = new JMenu("Option");

        JMenu gridVisibilitySubmenu = createVisibilitySubmenu();

        optionMenu.add(gridVisibilitySubmenu);

        return optionMenu;
    }

    private JMenu createHelpMenu() {
        return new JMenu("Help");
    }

    private JMenuItem createJMenuItem(String itemName, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(itemName);
        item.addActionListener(actionListener);

        return item;
    }

    private JRadioButtonMenuItem createJRadioButtonMenuItem(String radioName, ButtonGroup group, ActionListener actionListener) {
        JRadioButtonMenuItem radio = new JRadioButtonMenuItem(radioName);

        group.add(radio);
        radio.addActionListener(actionListener);

        return radio;
    }

    private JMenu createVisibilitySubmenu() {
        JMenu visibilitySubmenu = new JMenu("Grid visibility");
        ButtonGroup visibilityGroup = new ButtonGroup();

        JMenuItem alwaysShown = createJRadioButtonMenuItem("Always shown", visibilityGroup, x -> gamePanel.getRenderer().setGridVisibility(EGridVisibility.ALWAYS_SHOWN));

        JMenuItem hybrid = createJRadioButtonMenuItem("Hybrid", visibilityGroup, x -> gamePanel.getRenderer().setGridVisibility(EGridVisibility.HYBRID));
        hybrid.setSelected(true);

        JMenuItem alwaysHide = createJRadioButtonMenuItem("Always hide", visibilityGroup, x -> gamePanel.getRenderer().setGridVisibility(EGridVisibility.ALWAYS_HIDE));

        visibilitySubmenu.add(alwaysShown);
        visibilitySubmenu.add(hybrid);
        visibilitySubmenu.add(alwaysHide);

        return visibilitySubmenu;
    }

}
