/**
 * Classe Game - le moteur du jeu La Communauté
 *
 * @author Damiri Ilyas
 * @version 2025-2026
 */
public class Game
{
    private GameEngine aEngine;
    private UserInterface aGui;

    /**
     * Constructeur : crée le moteur et l'interface
     */
    public Game()
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface(this.aEngine);

        // liaison MVC
        this.aEngine.setGUI(this.aGui);
    }

    /**
     * Méthode principale (point d'entrée du programme)
     */
    public static void main(String[] args)
{
    javax.swing.SwingUtilities.invokeLater(() -> {
            new Game();
    });
}
}