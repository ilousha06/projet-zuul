import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * La classe SoundManager gere la musique de fond du jeu.
 * Elle joue une musique en boucle selon la zone dans laquelle
 * se trouve le joueur, et ne change la piste que si la zone change.
 * Elle permet aussi de jouer des effets sonores ponctuels.
 *
 * @author Ilyas
 * @version 2.0
 */
public class SoundManager
{
    /** Clip audio actuellement joue en boucle */
    private Clip currentClip;

    /** Nom de la zone musicale actuellement active */
    private String currentZone;

    /**
     * Joue une musique en boucle continue.
     * Arrete la musique precedente avant de lancer la nouvelle.
     *
     * @param fileName le nom du fichier audio dans le dossier sounds/
     */
    private void playMusic(String fileName)
    {
        try {
            stopMusic();

            File file = new File("sounds/" + fileName);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            currentClip = AudioSystem.getClip();
            currentClip.open(audio);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            currentClip.start();
        }
        catch (UnsupportedAudioFileException e) {
            System.out.println("Format invalide : " + fileName);
        }
        catch (IOException e) {
            System.out.println("Fichier introuvable : " + fileName);
        }
        catch (LineUnavailableException e) {
            System.out.println("Lecture impossible : " + fileName);
        }
    }

    /**
     * Arrete et libere la musique en cours de lecture.
     */
    public void stopMusic()
    {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
        }
    }

    /**
     * Change la musique de fond selon la zone indiquee.
     * Ne fait rien si le joueur reste dans la meme zone.
     *
     * Zones disponibles : "communaute", "jardin", "hall", "interdit", "cave".
     *
     * @param zone le nom de la zone musicale a activer
     */
    public void changeZoneMusic(String zone)
    {
        // Meme zone : pas de changement necessaire
        if (zone.equals(currentZone)) return;

        currentZone = zone;

        switch (zone) {
            case "communaute" -> playMusic("communaute.wav");
            case "jardin"     -> playMusic("jardin.wav");
            case "hall"       -> playMusic("hall.wav");
            case "interdit"   -> playMusic("interdit.wav");
            case "cave"       -> playMusic("cave.wav");
        }
    }

    /**
     * Joue un effet sonore une seule fois sans interrompre la musique de fond.
     *
     * @param fileName le nom du fichier audio dans le dossier sounds/
     */
    public void playEffect(String fileName)
    {
        try {
            File file = new File("sounds/" + fileName);
            AudioInputStream son = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(son);
            clip.start();
        }
        catch (Exception e) {
            System.out.println("Impossible de jouer le son : " + fileName);
        }
    }
}
