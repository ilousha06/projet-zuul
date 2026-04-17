import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe UserInterface
 *
 * Représente l'interface graphique du jeu Zuul (vue dans le modèle MVC).
 *
 * Cette classe gère :
 * - l'affichage des images (salles)
 * - l'affichage du texte (historique du jeu)
 * - les interactions utilisateur (boutons + saisie clavier)
 * - l'envoi des commandes vers le GameEngine
 *
 * Organisation de l'interface :
 * - Haut : image de la salle
 * - Centre : texte + champ de saisie
 * - Gauche : navigation (boussole)
 * - Droite : actions (take, drop, inventaire, etc.)
 *
 * @author Ilyas
 * @version 1.0
 */
public class UserInterface implements ActionListener
{
    /** Référence vers le moteur du jeu */
    private GameEngine engine;

    /** Fenêtre principale */
    private JFrame frame;

    /** Champ de saisie des commandes */
    private JTextField entryField;

    /** Zone d'affichage du texte */
    private JTextArea log;

    /** Zone d'affichage des images */
    private JLabel image;

    // ===== BOUTONS =====

    /** Boutons d'action */
    private JButton helpButton, lookButton, quitButton, backButton;

    /** Boutons d'interaction avec les items */
    private JButton bouton1, bouton2, inventaireButton;

    /** Boutons de déplacement */
    private JButton northButton, southButton, eastButton, westButton, upButton, downButton;

    /**
     * Constructeur de l'interface utilisateur.
     * Initialise le moteur et construit l'interface graphique.
     *
     * @param pGameEngine le moteur du jeu (contrôleur)
     */
    public UserInterface(GameEngine pGameEngine)
    {
        this.engine = pGameEngine;
        createGUI();
    }

    /**
     * Initialise et construit tous les composants graphiques.
     *
     * Cette méthode organise la fenêtre en 4 zones :
     * - NORTH : image
     * - CENTER : texte + input
     * - WEST : navigation
     * - EAST : actions
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
        topPanel.setPreferredSize(new Dimension(800, 400));

        frame.add(topPanel, BorderLayout.NORTH);

        // CENTRE
        JPanel centerPanel = new JPanel(new BorderLayout());

        log = new JTextArea();
        log.setEditable(false);

        JScrollPane scroll = new JScrollPane(log);
        centerPanel.add(scroll, BorderLayout.CENTER);

        entryField = new JTextField();
        centerPanel.add(entryField, BorderLayout.SOUTH);

        frame.add(centerPanel, BorderLayout.CENTER);

        // NAVIGATION
        JPanel navPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);

        Dimension big = new Dimension(50,70);

        northButton = new JButton("N");
        southButton = new JButton("S");
        eastButton = new JButton("E");
        westButton = new JButton("W");
        backButton = new JButton("●");
        upButton = new JButton("U");
        downButton = new JButton("D");

        for(JButton b : new JButton[]{northButton,southButton,eastButton,westButton,backButton,upButton,downButton}) {
            b.setPreferredSize(big);
            b.setFocusPainted(false);
        }

        c.gridx=1; c.gridy=0; navPanel.add(northButton,c);
        c.gridx=0; c.gridy=1; navPanel.add(westButton,c);
        c.gridx=1; c.gridy=1; navPanel.add(backButton,c);
        c.gridx=2; c.gridy=1; navPanel.add(eastButton,c);
        c.gridx=1; c.gridy=2; navPanel.add(southButton,c);
        c.gridx=0; c.gridy=3; navPanel.add(upButton,c);
        c.gridx=2; c.gridy=3; navPanel.add(downButton,c);

        frame.add(navPanel, BorderLayout.WEST);

        // ACTIONS
        JPanel actionPanel = new JPanel(new GridLayout(3,2,10,10));

        helpButton = new JButton("help");
        lookButton = new JButton("look");
        quitButton = new JButton("quit");

        bouton1 = new JButton("take");
        bouton2 = new JButton("drop");
        inventaireButton = new JButton("inv");

        actionPanel.add(helpButton);
        actionPanel.add(lookButton);
        actionPanel.add(bouton1);
        actionPanel.add(bouton2);
        actionPanel.add(inventaireButton);
        actionPanel.add(quitButton);

        frame.add(actionPanel, BorderLayout.EAST);

        // LISTENERS
        entryField.addActionListener(this);

        helpButton.addActionListener(this);
        lookButton.addActionListener(this);
        quitButton.addActionListener(this);
        backButton.addActionListener(this);

        bouton1.addActionListener(this);
        bouton2.addActionListener(this);
        inventaireButton.addActionListener(this);

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        eastButton.addActionListener(this);
        westButton.addActionListener(this);
        upButton.addActionListener(this);
        downButton.addActionListener(this);

        // FENETRE
        frame.setSize(1000,700);
        frame.setMinimumSize(new Dimension(800,600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Affiche une ligne de texte dans la zone d'historique.
     *
     * @param pText le texte à afficher
     */
    public void println(String pText)
    {
        log.append(pText + "\n");
    }

    /**
     * Affiche une image correspondant à la salle actuelle.
     * L'image est redimensionnée automatiquement.
     *
     * @param pImageName nom du fichier image
     */
    public void showImage(String pImageName)
    {
        String path = "images/" + pImageName;
        ImageIcon icon = new ImageIcon(path);

        int maxWidth = 1200;
        int maxHeight = 500;

        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        double ratio = Math.min((double)maxWidth/w,(double)maxHeight/h);

        Image scaled = icon.getImage().getScaledInstance(
                (int)(w*ratio),
                (int)(h*ratio),
                Image.SCALE_SMOOTH
        );

        image.setIcon(new ImageIcon(scaled));
    }

    /**
     * Méthode appelée automatiquement lors d'une interaction utilisateur.
     *
     * Cette méthode :
     * - détecte quel bouton a été cliqué
     * - transforme l'action en commande texte
     * - envoie la commande au GameEngine
     *
     * @param e événement utilisateur
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

        else if(source == bouton1) {
            String item = entryField.getText().trim();
            entryField.setText("");

            if(item.isEmpty()) {
                println("Enter an item to take.");
                return;
            }

            engine.interpretCommand("take " + item);
        }

        else if(source == bouton2) {
            String item = entryField.getText().trim();
            entryField.setText("");

            if(item.isEmpty()) {
                println("Enter an item to drop.");
                return;
            }

            engine.interpretCommand("drop " + item);
        }

        else if(source == inventaireButton) {
            engine.interpretCommand("inventaire");
        }

        else {
            String input = entryField.getText();
            entryField.setText("");
            engine.interpretCommand(input);
        }
    }

    /**
     * Active ou désactive la saisie utilisateur.
     *
     * @param pOn true pour activer, false pour désactiver
     */
    public void enable(boolean pOn)
    {
        entryField.setEditable(pOn);
    }

    /**
     * Efface le contenu du terminal (zone texte).
     */
    public void clear()
    {
        log.setText("");
    }
}