import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface graphique du jeu La Communaute.
 * Affiche la fenetre principale avec le journal de texte,
 * les boutons de navigation et les boutons d'actions.
 *
 * @author Ilyas
 * @version 7.0
 */
public class UserInterface implements ActionListener
{
    private final GameEngine engine;
    private JTextField entryField;
    private JTextArea  log;
    private JLabel     image;
    private JFrame     frame;

    // Barre de suspicion
    private JProgressBar suspicionBar;

    // La boussole circulaire gere N/S/E/W/back en interne
    private CompassPanel compass;

    // Boutons haut et bas
    private ImageButton upButton, downButton;

    // Boutons d'actions
    private ImageButton helpButton, lookButton, takeButton;
    private ImageButton dropButton, inventaireButton, quitButton;
    private ImageButton chargeButton, fireButton, useButton;

    // Couleurs de l'interface
    static final Color BG       = new Color(10,  9,  8);
    static final Color PANEL_BG = new Color(16, 13, 11);
    static final Color GOLD     = new Color(180, 135, 55);
    static final Color GOLD_DIM = new Color(110,  82, 28);
    static final Color GOLD_HOV = new Color(220, 175, 80);
    static final Color TEXT_COL = new Color(220, 210,190);
    static final Color SEP_COL  = new Color( 90,  68, 25);

    // Polices de caracteres
    private static final Font FONT_TITLE = new Font("Palatino Linotype", Font.BOLD,  17);
    private static final Font FONT_LOG   = new Font("Palatino Linotype", Font.PLAIN, 20);
    private static final Font FONT_INPUT = new Font("Palatino Linotype", Font.PLAIN, 20);

    /**
     * Constructeur : cree l'interface et l'affiche.
     *
     * @param pGameEngine le moteur de jeu qui recoit les commandes
     */
    public UserInterface(GameEngine pGameEngine)
    {
        this.engine = pGameEngine;
        createGUI();
    }

    /**
     * Construit et affiche tous les composants de la fenetre.
     */
    private void createGUI()
    {
        frame = new JFrame("La Communaute");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BG);
        frame.add(buildImagePanel(),  BorderLayout.NORTH);
        frame.add(buildNavPanel(),    BorderLayout.WEST);
        frame.add(buildActionPanel(), BorderLayout.EAST);
        frame.add(buildCenterPanel(), BorderLayout.CENTER);
        registerListeners();
        frame.setSize(1600, 960);
        frame.setMinimumSize(new Dimension(1200, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Cree le panneau du haut qui affiche l'image de la salle.
     *
     * @return le panneau configure
     */
    private JPanel buildImagePanel()
    {
        image = new JLabel();
        image.setHorizontalAlignment(JLabel.CENTER);
        image.setBackground(BG);
        image.setOpaque(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(1600, 480));
        panel.setBorder(new OrnateBorder(GOLD, GOLD_DIM, 2, 10));
        panel.add(image, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Cree le panneau de navigation a gauche.
     * Contient la boussole circulaire et les boutons haut et bas en dessous.
     *
     * @return le panneau configure
     */
    private JPanel buildNavPanel()
    {
        compass    = new CompassPanel();
        upButton   = new ImageButton("images/buttons/up.png",   "up");
        downButton = new ImageButton("images/buttons/down.png", "down");

        JPanel udPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        udPanel.setOpaque(false);
        udPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        udPanel.add(upButton);
        udPanel.add(downButton);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.setPreferredSize(new Dimension(280, 0));
        outer.setBorder(new CompoundBorder(
                new OrnateBorder(GOLD, GOLD_DIM, 2, 8),
                BorderFactory.createEmptyBorder(16, 14, 16, 14)));
        outer.add(compass,  BorderLayout.CENTER);
        outer.add(udPanel,  BorderLayout.SOUTH);
        return outer;
    }

    /**
     * Cree le panneau d'actions a droite.
     * Contient 9 boutons : help, look, take, drop, inv, quit, charge, fire, use.
     *
     * @return le panneau configure
     */
    private JPanel buildActionPanel()
    {
        helpButton = new ImageButton("images/buttons/help.png","help");
        lookButton = new ImageButton("images/buttons/look.png","look");
        takeButton = new ImageButton("images/buttons/take.png","take");
        dropButton = new ImageButton("images/buttons/drop.png","drop");
        inventaireButton = new ImageButton("images/buttons/inventaire.png","inventaire");
        quitButton = new ImageButton("images/buttons/quit.png","quit");
        chargeButton = new ImageButton("images/buttons/charge.png","charge");
        fireButton = new ImageButton("images/buttons/fire.png","fire");
        useButton = new ImageButton("images/buttons/use.png","use");

        // Grille 3x3 pour les 9 boutons
        JPanel grid = new JPanel(new GridLayout(3, 3, 8, 8));
        grid.setOpaque(false);
        grid.add(helpButton);grid.add(lookButton);grid.add(inventaireButton);
        grid.add(takeButton);grid.add(dropButton);grid.add(useButton);
        grid.add(chargeButton);grid.add(fireButton);grid.add(quitButton);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.setPreferredSize(new Dimension(280, 0));
        outer.setBorder(new CompoundBorder(
                new OrnateBorder(GOLD, GOLD_DIM, 2, 8),
                BorderFactory.createEmptyBorder(16, 12, 16, 12)));
        outer.add(grid, BorderLayout.CENTER);
        return outer;
    }

    /**
     * Cree le panneau central avec le titre, le journal de texte
     * et la zone de saisie des commandes.
     *
     * @return le panneau configure
     */
    private JPanel buildCenterPanel()
    {
        log = new JTextArea();
        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setBackground(PANEL_BG);
        log.setForeground(TEXT_COL);
        log.setCaretColor(GOLD);
        log.setFont(FONT_LOG);
        log.setBorder(BorderFactory.createEmptyBorder(12, 22, 8, 22));

        JScrollPane scroll = new JScrollPane(log);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(PANEL_BG);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(PANEL_BG);
        center.setBorder(new OrnateBorder(GOLD, GOLD_DIM, 2, 8));
        center.add(buildTitleBar(), BorderLayout.NORTH);
        center.add(scroll,          BorderLayout.CENTER);
        center.add(buildInputRow(), BorderLayout.SOUTH);
        return center;
    }

    /**
     * Cree la barre du haut avec le titre et la barre de suspicion.
     *
     * @return le panneau configure
     */
    private JPanel buildTitleBar()
    {
        JPanel bar = new JPanel(new BorderLayout());

        bar.setBackground(PANEL_BG);

        bar.setBorder(BorderFactory.createEmptyBorder(16, 22, 12, 22));

        // titre
        JLabel title = new JLabel("Suspicion de la communauté");

        title.setFont(FONT_TITLE);

        title.setForeground(GOLD);

        // barre de progression
        suspicionBar = new JProgressBar(0, 100);
        suspicionBar.setValue(0);
        suspicionBar.setStringPainted(true);
        suspicionBar.setString("0%");
        suspicionBar.setBackground(new Color(20, 16, 12));
        suspicionBar.setForeground(GOLD);
        suspicionBar.setBorder(BorderFactory.createLineBorder(GOLD_DIM));
        suspicionBar.setPreferredSize(new Dimension(200, 22));

        // ajout dans le panel
        bar.add(title, BorderLayout.NORTH);

        bar.add(suspicionBar, BorderLayout.CENTER);

        return bar;
    }

    /**
     * Met a jour la barre de suspicion.
     *
     * @param value valeur entre 0 et 100
     */
    public void setSuspicion(final int value)
    {
        suspicionBar.setValue(value);
        suspicionBar.setString(value + "%");
    }

    /**
     * Cree la zone de saisie en bas du panneau central.
     *
     * @return le panneau de saisie configure
     */
    private JPanel buildInputRow()
    {
        JLabel prefix = new JLabel();
        prefix.setFont(FONT_INPUT);
        prefix.setForeground(GOLD);
        prefix.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));

        entryField = new JTextField();
        entryField.setBackground(new Color(8, 7, 6));
        entryField.setForeground(GOLD);
        entryField.setCaretColor(GOLD);
        entryField.setFont(FONT_INPUT);
        entryField.setBorder(BorderFactory.createEmptyBorder(9, 8, 9, 8));

        JPanel inner = new JPanel(new BorderLayout());
        inner.setBackground(new Color(8, 7, 6));
        inner.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(GOLD_DIM, 1),
                BorderFactory.createEmptyBorder(0, 6, 0, 0)));
        inner.add(prefix,     BorderLayout.WEST);
        inner.add(entryField, BorderLayout.CENTER);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(PANEL_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(7, 20, 22, 20));
        outer.add(inner, BorderLayout.CENTER);
        return outer;
    }

    /**
     * Enregistre tous les ecouteurs d'evenements sur les boutons
     * et le champ de saisie.
     */
    private void registerListeners()
    {
        entryField.addActionListener(this);

        // La boussole envoie ses directions directement au moteur
        compass.setDirectionListener(dir -> {
            if (dir.equals("back")) engine.interpretCommand("back");
            else                    engine.interpretCommand("go " + dir);
        });

        upButton.addActionListener(this);downButton.addActionListener(this);
        helpButton.addActionListener(this);lookButton.addActionListener(this);
        takeButton.addActionListener(this);dropButton.addActionListener(this);
        inventaireButton.addActionListener(this); quitButton.addActionListener(this);
        chargeButton.addActionListener(this);fireButton.addActionListener(this);
        useButton.addActionListener(this);
    }

    /**
     * Affiche un texte dans le journal du jeu, suivi d'un retour a la ligne.
     *
     * @param pText le texte a afficher
     */
    public void println(final String pText)
    {
        log.append(pText + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Charge et affiche une image dans le panneau du haut.
     * L'image est redimensionnee pour tenir dans la zone disponible.
     *
     * @param pImageName le nom du fichier image dans le dossier "images/"
     */
    public void showImage(final String pImageName)
    {
        ImageIcon icon = new ImageIcon("images/" + pImageName);
        int maxW = (frame != null ? frame.getWidth() - 410 : 1200);
        int maxH = 480;
        int w = icon.getIconWidth(), h = icon.getIconHeight();
        if (w <= 0 || h <= 0) { image.setIcon(icon); return; }
        double ratio = Math.min((double) maxW / w, (double) maxH / h);
        image.setIcon(new ImageIcon(icon.getImage()
                .getScaledInstance((int)(w*ratio), (int)(h*ratio), Image.SCALE_SMOOTH)));
    }

    /**
     * Active ou desactive le champ de saisie des commandes.
     *
     * @param pOn true pour activer, false pour desactiver
     */
    public void enable(final boolean pOn) { entryField.setEditable(pOn); }

    /**
     * Efface tout le contenu du journal de texte.
     */
    public void clear() { log.setText(""); }

    /**
     * Recoit les clics sur les boutons et les frappes dans le champ de saisie.
     * Envoie la commande correspondante au moteur de jeu.
     *
     * @param e l'evenement declanche
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        Object src = e.getSource();
        if      (src == helpButton) engine.interpretCommand("help");
        else if (src == lookButton) engine.interpretCommand("look");
        else if (src == quitButton) engine.interpretCommand("quit");
        else if (src == upButton) engine.interpretCommand("go up");
        else if (src == downButton) engine.interpretCommand("go down");
        else if (src == inventaireButton) engine.interpretCommand("inventaire");
        else if (src == chargeButton) engine.interpretCommand("charge");
        else if (src == fireButton) engine.interpretCommand("fire");
        else if (src == useButton) {
            String item = entryField.getText().trim();
            entryField.setText("");
            if (item.isEmpty()) { println("Entrez un objet a utiliser."); return; }
            engine.interpretCommand("use " + item);
        }
        else if (src == takeButton) {
            String item = entryField.getText().trim();
            entryField.setText("");
            if (item.isEmpty()) { println("Entrez un objet a prendre."); return; }
            engine.interpretCommand("take " + item);
        }
        else if (src == dropButton) {
            String item = entryField.getText().trim();
            entryField.setText("");
            if (item.isEmpty()) { println("Entrez un objet a deposer."); return; }
            engine.interpretCommand("drop " + item);
        }
        else {
            String input = entryField.getText().trim();
            entryField.setText("");
            if (!input.isEmpty()) engine.interpretCommand(input);
        }
    }

    /**
     * Bordure ornementee style medieval avec deux lignes dorees
     * et des losanges aux quatre coins.
     */
    static class OrnateBorder extends AbstractBorder
    {
        private final Color outerColor, innerColor;
        private final int   lineThickness, gap;

        /**
         * @param outerColor couleur de la ligne exterieure
         * @param innerColor couleur de la ligne interieure
         * @param lineThickness epaisseur de la ligne exterieure
         * @param gap espace entre les deux lignes
         */
        OrnateBorder(final Color outerColor, final Color innerColor, final int lineThickness, final int gap)
        {
            this.outerColor = outerColor; this.innerColor = innerColor;
            this.lineThickness = lineThickness; this.gap = gap;
        }

        @Override
        public Insets getBorderInsets(Component c)
        {
            int t = lineThickness + gap + 2;
            return new Insets(t, t, t, t);
        }

        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Ligne exterieure epaisse
            g2.setColor(outerColor);
            g2.setStroke(new BasicStroke(lineThickness));
            int o = lineThickness / 2;
            g2.drawRect(x+o, y+o, w-lineThickness, h-lineThickness);

            // Ligne interieure fine
            int inset = lineThickness + gap;
            g2.setColor(innerColor);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRect(x+inset, y+inset, w-inset*2, h-inset*2);

            // Losange dore a chaque coin
            int d = 5;
            int[] cx = { x+inset, x+w-inset, x+inset,   x+w-inset };
            int[] cy = { y+inset, y+inset,   y+h-inset, y+h-inset };
            g2.setColor(outerColor);
            for (int k = 0; k < 4; k++) {
                int px = cx[k], py = cy[k];
                g2.fillPolygon(new int[]{px,px+d,px,px-d}, new int[]{py-d,py,py+d,py}, 4);
            }
            g2.dispose();
        }
    }

    /**
     * Boussole circulaire avec images cliquables.
     * Dessine un cercle dore et place une image pour chaque direction.
     * Detecte les clics et appelle le listener avec la bonne direction.
     */
    static class CompassPanel extends JPanel
    {

        /**
         * Interface pour recevoir la direction cliquee.
         */
        interface DirectionListener { void onDirection(String direction); }

        private DirectionListener listener;
        private String directionSurvolee = null;

        // Les 4 directions et leurs angles sur le cercle
        private static final String[] DIRECTIONS = { "north", "east", "south", "west" };
        private static final double[] ANGLES = { -90.0,   0.0,   90.0,   180.0  };

        // Chemins des 5 images (4 directions + back au centre)
        private static final String[] CHEMINS = {"images/buttons/north.png", "images/buttons/east.png", "images/buttons/south.png", "images/buttons/west.png", "images/buttons/back.png"};

        private final ImageIcon[] imagesNormales = new ImageIcon[5];

        /**
         * Constructeur : charge les images et branche la souris.
         */
        CompassPanel()
        {
            setOpaque(false);
            chargerImages();

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    String avant = directionSurvolee;
                    directionSurvolee = trouverZone(e.getX(), e.getY());
                    if (!java.util.Objects.equals(avant, directionSurvolee)) repaint();
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String zone = trouverZone(e.getX(), e.getY());
                    if (zone != null && listener != null) listener.onDirection(zone);
                }
                @Override
                public void mouseExited(MouseEvent e) { directionSurvolee = null; repaint(); }
            });

            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        /**
         * Permet de brancher un ecouteur pour recevoir les directions cliquees.
         *
         * @param l le listener a utiliser
         */
        void setDirectionListener(DirectionListener l) { listener = l; }

        /**
         * Charge les 5 images depuis le disque.
         * Cree un placeholder dore si l'image est absente.
         */
        private void chargerImages()
        {
            for (int i = 0; i < CHEMINS.length; i++) {
                String label = (i < DIRECTIONS.length) ? DIRECTIONS[i] : "back";
                if (new File(CHEMINS[i]).exists()) {
                    imagesNormales[i] = new ImageIcon(
                            new ImageIcon(CHEMINS[i]).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                } else {
                    imagesNormales[i] = creerPlaceholder(label);
                }
            }
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(176, 176);
        }

        /**
         * Dessine le cercle dore et les images des boutons.
         *
         * @param g le contexte graphique
         */
        @Override
        protected void paintComponent(final Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int R = Math.min(cx, cy) - 5;

            // fond noir simple
            g2.setColor(new Color(8,6,4));
            g2.fillOval(cx - R, cy - R, R * 2, R * 2);

            // contour dore
            g2.setColor(GOLD);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(cx - R, cy - R, R * 2, R * 2);

            // images des directions
            for(int i = 0; i < DIRECTIONS.length; i++)
            {
                double rad = Math.toRadians(ANGLES[i]);
                int bx = cx + (int)(R * 0.68 * Math.cos(rad));
                int by = cy + (int)(R * 0.68 * Math.sin(rad));
                dessinerImage(g2, i, bx, by);
            }

            // bouton back
            dessinerImage(g2, 4, cx, cy);

            g2.dispose();
        }

        /**
         * Dessine une image centree sur un point.
         * Ajoute un cercle dore autour si le bouton est survole.
         *
         * @param g2 le contexte graphique
         * @param index index de l'image (0=nord 1=est 2=sud 3=ouest 4=back)
         * @param cx coordonnee X du centre
         * @param cy coordonnee Y du centre
         */
        private void dessinerImage(final Graphics2D g2, final int index, final int cx, final int cy)
        {
            // Verifie si cette direction est survolee par la souris
            String dir = (index < DIRECTIONS.length) ? DIRECTIONS[index] : "back";
            boolean survole = dir.equals(directionSurvolee);

            ImageIcon icone = imagesNormales[index];
            int w = icone.getIconWidth(), h = icone.getIconHeight();

            // Cercle dore autour de l'image si survole
            if (survole) {
                int r = Math.max(w, h) / 2 + 4;
                g2.setColor(new Color(75, 54, 20, 160));
                g2.fillOval(cx-r, cy-r, r*2, r*2);
                g2.setColor(GOLD_HOV);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(cx-r, cy-r, r*2, r*2);
            }

            g2.drawImage(icone.getImage(), cx-w/2, cy-h/2, null);
        }

        /**
         * Detecte dans quelle zone de la boussole se trouve la souris.
         *
         * @param mx position X de la souris
         * @param my position Y de la souris
         * @return "north", "south", "east", "west", "back" ou null
         */
        private String trouverZone(final int mx, final int my)
        {
            int cx = getWidth()/2, cy = getHeight()/2;
            int R  = Math.min(cx, cy) - 5;
            double dx = mx-cx, dy = my-cy;
            double dist = Math.sqrt(dx*dx + dy*dy);
            if (dist < R * 0.21) return "back";
            if (dist < R * 0.97) {
                double angle = Math.toDegrees(Math.atan2(dy, dx));
                if (angle < 0) angle += 360;
                if (angle >= 315 || angle < 45)  return "east";
                if (angle >= 45  && angle < 135) return "south";
                if (angle >= 135 && angle < 225) return "west";
                return "north";
            }
            return null;
        }

        /**
         * Cree un placeholder dore quand l'image est absente.
         *
         * @param label texte a afficher dans le cercle
         * @return l'image de remplacement
         */
        private static ImageIcon creerPlaceholder(final String label)
        {
            int t = 40;
            BufferedImage buf = new BufferedImage(t, t, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buf.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(26, 20, 12));
            g2.fillOval(0, 0, t, t);
            g2.setColor(new Color(180, 135, 55));
            g2.setFont(new Font("Palatino Linotype", Font.BOLD, 10));
            FontMetrics fm = g2.getFontMetrics();
            String txt = label.toUpperCase();
            g2.drawString(txt, (t-fm.stringWidth(txt))/2, t/2+fm.getAscent()/2-2);
            g2.dispose();
            return new ImageIcon(buf);
        }
    }

    /**
     * Bouton cliquable base sur une image.
     * Affiche une image normale.
     * Si l'image est absente, affiche un carre de remplacement dore.
     */
    static class ImageButton extends JLabel
    {
        private final List<ActionListener> listeners = new ArrayList<>();
        private ImageIcon normalIcon;
        private ImageIcon hoverIcon;

        /**
         * @param imagePath chemin vers l'image PNG du bouton
         * @param command   commande envoyee au jeu lors du clic
         */
        ImageButton(String imagePath, String command)
        {
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setOpaque(false);

            // Charge l'image ou cree un placeholder si elle est absente
            if (new File(imagePath).exists()) {
                normalIcon = new ImageIcon(new ImageIcon(imagePath)
                        .getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            } else {
                normalIcon = buildPlaceholder(command);
            }

            // Cree la version avec aureole doree pour le survol
            hoverIcon = buildHoverIcon(normalIcon);
            setIcon(normalIcon);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    setIcon(hoverIcon);
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    setIcon(normalIcon);
                }

                @Override
                public void mouseClicked(MouseEvent e)
                {
                    ActionEvent ae = new ActionEvent(
                            ImageButton.this, ActionEvent.ACTION_PERFORMED, command);
                    for (ActionListener l : listeners) l.actionPerformed(ae);
                }
            });
        }

        /**
         * Cree une version de l'icone avec une aureole doree autour.
         * Affichee quand la souris survole le bouton.
         *
         * @param src l'icone d'origine
         * @return l'icone avec aureole
         */
        private static ImageIcon buildHoverIcon(ImageIcon src)
        {
            int w = src.getIconWidth();
            int h = src.getIconHeight();
            int marge = 6;

            // On cree une image plus grande pour avoir de la place pour l aureole
            BufferedImage buf = new BufferedImage(w + marge * 2, h + marge * 2, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buf.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Aureole doree autour de l image
            g2.setColor(new Color(220, 175, 80, 180));
            g2.setStroke(new BasicStroke(3f));
            g2.drawRoundRect(1, 1, w + marge * 2 - 3, h + marge * 2 - 3, 12, 12);

            // Fond dore tres leger
            g2.setColor(new Color(220, 175, 80, 40));
            g2.fillRoundRect(1, 1, w + marge * 2 - 3, h + marge * 2 - 3, 12, 12);

            // Image originale centree
            g2.drawImage(src.getImage(), marge, marge, null);
            g2.dispose();

            return new ImageIcon(buf);
        }

        /**
         * Cree un carre de remplacement quand l'image est absente.
         *
         * @param label texte a afficher dans le carre
         * @return l'icone de remplacement
         */
        private static ImageIcon buildPlaceholder(final String label)
        {
            int s = 64;
            BufferedImage img = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(20, 16, 12));
            g2.fillRoundRect(0, 0, s, s, 8, 8);
            g2.setColor(new Color(110, 82, 28));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, s-3, s-3, 8, 8);
            g2.setFont(new Font("Palatino Linotype", Font.BOLD, 11));
            g2.setColor(new Color(180, 135, 55));
            FontMetrics fm = g2.getFontMetrics();
            String txt = label.toUpperCase();
            g2.drawString(txt, (s-fm.stringWidth(txt))/2, s/2+fm.getAscent()/2-2);
            g2.dispose();
            return new ImageIcon(img);
        }

        /**
         * Ajoute un ecouteur de clic sur ce bouton.
         *
         * @param l l'ecouteur a ajouter
         */
        public void addActionListener(final ActionListener l)
        {
            listeners.add(l);
        }
    }
}