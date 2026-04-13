public class UserInterface
{
    /**
     * Affiche du texte (temporaire)
     */
    public void println(String pText)
    {
        System.out.println(pText);
    }

    /**
     * Affiche une image (temporaire)
     */
    public void showImage(String pImageName)
    {
        System.out.println("[Image: " + pImageName + "]");
    }

    /**
     * Active/désactive l'interface
     */
    public void enable(boolean pOn)
    {
        // rien pour l'instant
    }
}