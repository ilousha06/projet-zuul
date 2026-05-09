import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * La classe SoundEffect fournit une methode statique pour jouer
 * des effets sonores ponctuels (victoire, game over, prise d objet...).
 * Elle est independante du SoundManager et ne touche pas la musique de fond.
 *
 * @author Ilyas
 * @version 2.0
 */
public class SoundEffect
{
    /**
     * Joue un effet sonore une seule fois de maniere asynchrone.
     *
     * @param fileName le nom du fichier audio dans le dossier sounds/
     */
    public static void play(String fileName)
    {
        try {
            File file = new File("sounds/" + fileName);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
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
}
