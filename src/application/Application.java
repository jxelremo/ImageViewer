package application;

import control.Command;
import control.NextImageCommand;
import control.PrevImageCommand;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import view.ImageDisplay;

public class Application extends JFrame{
    
    private final Map<String, Command> commands = new HashMap<>();
    private ImageDisplay imageDisplay;
    
    public static void main(String[] args) {
        new Application().setVisible(true);
    }

    public Application(){
        deployUI();
        addCommands();
    }

    private void deployUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(elements());
        this.setTitle("Image Viewer - "+imageDisplay.image().name());
        this.add(toolBar(),BorderLayout.SOUTH);
    }

    private void addCommands() {
        commands.put("Prev", new PrevImageCommand(imageDisplay));
        commands.put("Next", new NextImageCommand(imageDisplay));
    }

    private JPanel elements() {
        ImagePanel panel = new ImagePanel(new FileImageReader("C:\\Users\\Public\\Pictures\\Sample Pictures").read());
        panel.addMouseListener(new MouseListener() {
            private int getXaxis;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getXaxis = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (getXaxis-e.getX()<0){
                    setTitle("Image Viewer - "+imageDisplay.image().prev().name());
                    commands.get("Prev").execute();
                } else {
                    setTitle("Image Viewer - "+imageDisplay.image().next().name());
                    commands.get("Next").execute();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right arrow");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left arrow");
        panel.getActionMap().put("left arrow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Image Viewer - "+imageDisplay.image().prev().name());
                commands.get("Prev").execute();
            }
        });
        panel.getActionMap().put("right arrow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Image Viewer - "+imageDisplay.image().next().name());
                commands.get("Next").execute();
            }
        });

        imageDisplay = panel;
        return panel;
    }

    private JMenuBar toolBar() {
        JMenuBar menu = new JMenuBar();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu.add(prevButton());
        menu.add(nextButton());
        return menu;
    }

    private JButton prevButton() {
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Image Viewer - "+imageDisplay.image().prev().name());
                commands.get("Prev").execute();
            }
        });
        return prevButton;
    }

    private JButton nextButton() {
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Image Viewer - "+imageDisplay.image().next().name());
                commands.get("Next").execute();
            }
        });
        return nextButton;
    }
}
