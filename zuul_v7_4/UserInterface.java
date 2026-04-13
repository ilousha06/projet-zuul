import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * IG du jeu Zuul
 */
public class UserInterface implements ActionListener
{
    private GameEngine engine;
    private JFrame frame;
    private JTextField entryField;
    private JTextArea log;
    private JLabel image;

    // Boutons
    private JButton helpButton, lookButton, quitButton;
    private JButton northButton, southButton, eastButton, westButton, upButton, downButton;

    public UserInterface(GameEngine gameEngine)
    {
        engine = gameEngine;
        createGUI();
    }

    private void createGUI()
    {
        frame = new JFrame("La Communauté");
        entryField = new JTextField(34);

        log = new JTextArea();
        log.setEditable(false);

        JScrollPane listScroller = new JScrollPane(log);
        listScroller.setPreferredSize(new Dimension(200, 200));

        image = new JLabel();

        // PANEL POUR BOUTONS
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));

        // Création boutons 
        helpButton = new JButton("help");
        lookButton = new JButton("look");
        quitButton = new JButton("quit");

        northButton = new JButton("north");
        southButton = new JButton("south");
        eastButton = new JButton("east");
        westButton = new JButton("west");
        upButton = new JButton("up");
        downButton = new JButton("down");

        // Ajout boutons au panel sous forme de matrice 
        buttonPanel.add(northButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(lookButton);

        buttonPanel.add(westButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(eastButton);

        buttonPanel.add(upButton);
        buttonPanel.add(southButton);
        buttonPanel.add(downButton);

        // Ajout composants à la fenêtre
        frame.getContentPane().add(image, BorderLayout.NORTH);
        frame.getContentPane().add(listScroller, BorderLayout.CENTER);
        frame.getContentPane().add(entryField, BorderLayout.SOUTH);
        frame.getContentPane().add(buttonPanel, BorderLayout.EAST);

        // LISTENERS
        entryField.addActionListener(this);

        helpButton.addActionListener(this);
        lookButton.addActionListener(this);
        quitButton.addActionListener(this);

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        eastButton.addActionListener(this);
        westButton.addActionListener(this);
        upButton.addActionListener(this);
        downButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Affiche du texte dans la fen
     */
    public void println(String text)
    {
        log.append(text + "\n");
    }

    /**
     * Affiche image
     */
    public void showImage(String imageName)
    {
        ImageIcon icon = new ImageIcon("images/" + imageName);
        image.setIcon(icon);
        frame.pack();
    }

    /**
     * Gestion des événements (b + t)
     */
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        // COMMANDES BOUTONS
        if(source == helpButton) engine.interpretCommand("help");
        else if(source == lookButton) engine.interpretCommand("look");
        else if(source == quitButton) engine.interpretCommand("quit");

        else if(source == northButton) engine.interpretCommand("go north");
        else if(source == southButton) engine.interpretCommand("go south");
        else if(source == eastButton) engine.interpretCommand("go east");
        else if(source == westButton) engine.interpretCommand("go west");
        else if(source == upButton) engine.interpretCommand("go up");
        else if(source == downButton) engine.interpretCommand("go down");

            // SINON : champ texte
        else {
            String input = entryField.getText();
            entryField.setText("");
            engine.interpretCommand(input);
        }
    }

    /**
     * Act/dés le jeu
     */
    public void enable(boolean on)
    {
        entryField.setEditable(on);
    }
}