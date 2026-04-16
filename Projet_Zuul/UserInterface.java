import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * UI du jeu Zuul
 * gère l'affichage + interactions joueur
 */
public class UserInterface implements ActionListener
{
    private GameEngine engine; // lien avec le moteur du jeu
    private JFrame frame; // la fenetre principale
    private JTextField entryField; // zone ou on tape les commandes
    private JTextArea log; // zone texte (histor)
    private JLabel image; // zone pour afficher les images

    // boutons commandes
    private JButton helpButton, lookButton, quitButton, backButton, bouton1, bouton2;
    private JButton northButton, southButton, eastButton, westButton, upButton, downButton;

    /**
     * constructeur = on lance direct l'UI
     */
    public UserInterface(GameEngine gameEngine)
    {
        this.engine = gameEngine;
        createGUI(); // crée toute la fenetre
    }

    /**
     * Crée toute l'interface graphique
     */
    private void createGUI()
    {
        frame = new JFrame("La Communauté");
        frame.setLayout(new BorderLayout());

        // IMAGE
        image = new JLabel();
        image.setHorizontalAlignment(JLabel.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(image, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(800, 450));

        frame.add(topPanel, BorderLayout.NORTH);

        // TEXTE
        log = new JTextArea();
        log.setEditable(false);
        log.setRows(8);

        JScrollPane listScroller = new JScrollPane(log);
        listScroller.setPreferredSize(new Dimension(600, 200));

        frame.add(listScroller, BorderLayout.CENTER);

        // SAISIE
        entryField = new JTextField();
        frame.add(entryField, BorderLayout.SOUTH);

        // BOUTONS
        JPanel buttonPanel = new JPanel(new GridLayout(3, 4));

        // boutons principaux
        helpButton = new JButton("help");
        lookButton = new JButton("look");
        quitButton = new JButton("quit");
        backButton = new JButton("back");
        bouton1 = new JButton("???");
        bouton2 = new JButton("???");

        // boutons déplacements
        northButton = new JButton("north");
        southButton = new JButton("south");
        eastButton = new JButton("east");
        westButton = new JButton("west");
        upButton = new JButton("up");
        downButton = new JButton("down");

        // disposition
        buttonPanel.add(quitButton);
        buttonPanel.add(bouton1);
        buttonPanel.add(northButton);
        buttonPanel.add(lookButton);

        buttonPanel.add(helpButton);
        buttonPanel.add(westButton);
        buttonPanel.add(backButton);
        buttonPanel.add(eastButton);

        buttonPanel.add(bouton2);
        buttonPanel.add(upButton);
        buttonPanel.add(southButton);
        buttonPanel.add(downButton);

        frame.add(buttonPanel, BorderLayout.EAST);

        // LISTENERS
        entryField.addActionListener(this);

        helpButton.addActionListener(this);
        lookButton.addActionListener(this);
        quitButton.addActionListener(this);
        backButton.addActionListener(this);

        bouton1.addActionListener(this);
        bouton2.addActionListener(this);

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        eastButton.addActionListener(this);
        westButton.addActionListener(this);
        upButton.addActionListener(this);
        downButton.addActionListener(this);

        // FENETRE
        frame.setSize(900, 700);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * affiche du texte dans le log
     */
    public void println(String text)
    {
        log.append(text + "\n");
    }

    /**
     * affiche une image
     */
    public void showImage(String imageName)
    {
        String path = "images/" + imageName;
        ImageIcon icon = new ImageIcon(path);

        int maxWidth = 1200;
        int maxHeight = 500;

        int imgWidth = icon.getIconWidth();
        int imgHeight = icon.getIconHeight();

        double ratio = Math.min((double)maxWidth / imgWidth, (double)maxHeight / imgHeight);

        int newWidth = (int)(imgWidth * ratio);
        int newHeight = (int)(imgHeight * ratio);

        Image scaled = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        image.setIcon(new ImageIcon(scaled));

        image.revalidate();
        image.repaint();
    }

    /**
     * methode appelée à chaque action
     */
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source == helpButton) engine.interpretCommand("help");
        else if(source == lookButton) engine.interpretCommand("look");
        else if(source == quitButton) engine.interpretCommand("quit");
        else if(source == backButton) engine.interpretCommand("back");

        else if(source == northButton) engine.interpretCommand("go north");
        else if(source == southButton) engine.interpretCommand("go south");
        else if(source == eastButton) engine.interpretCommand("go east");
        else if(source == westButton) engine.interpretCommand("go west");
        else if(source == upButton) engine.interpretCommand("go up");
        else if(source == downButton) engine.interpretCommand("go down");

        else if(source == bouton1) println("Bouton non utilisé.");
        else if(source == bouton2) println("Bouton non utilisé.");

        else {
            String input = entryField.getText();
            entryField.setText("");
            engine.interpretCommand(input);
        }
    }

    /**
     * act/des la saisie (fin du jeu)
     */
    public void enable(boolean on)
    {
        
        entryField.setEditable(on);
    }

    /**
     * Juste pour clear l'invite de commande 
     */
    public void clear()
    {
        log.setText("");
    }
}