package za.co.wethinkcode.robotworlds.client.SwingGUI;//import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;


public class HelperMethods {
    private static Random RANDOM = new Random();

    static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    /**
     * generate a random number less than giving number
     * @param endExclusive  exclusive maximum number
     */
    static int nextInt(final int endExclusive) {
        return RANDOM.nextInt(endExclusive);
    }

    /**
     * Play an audio file located under directory "assets/audios"
     */
//    static void playAudio(final String audioFile) {
//        File file = new File("assets/audios/" + audioFile);
//        Media media = new Media(file.toURI().toString());
//        new MediaPlayer(media).play();
//    }

    /**
     * Get {@link Image} from given file
     * @param imageFileName name of image file under directory "assets/images"
     */
    public static Image getImage(final String imageFileName) {
        return new ImageIcon("assets/images/" + imageFileName).getImage();
    }

    static void sleepSilently(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // -> Ignore
        }
    }

    /**
     * Set Swing Theme: Windows or Nimbus
     */
    public static void setTheme() {
        String theme = System.getProperty("os.name").startsWith("Windows") ? "Windows" : "Nimbus";
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (theme.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(HelperMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Use googles gson module to serialize an Object of any class to a Json string
     * Usage: String json = serialize(myClassInstance)
     * */
    static String serialize(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Use googles gson module to deserialize a Json string to its corresponding class.
     * Usage: Object deserializedClass = deserialize(myJsonSerializedClass)
     * */
    static Object deSerialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, json.getClass());
    }



// TANK DIAGONAL
    //        int dist = (WIDTH - 120) / 11;
//        for (int i = 0; i < 12; i++) {
//            client.Direction direction = client.Direction.values()[i % 8];
//            g.drawImage(direction.getImage("etank"), 42 + dist * i, HEIGHT / 2 + 100, null);
//        }

    //MISSLE DIAGONAL

    //        boolean upBull = g.drawImage(client.Direction.Left.getImage("missile"), WIDTH / 2 - 18, bulletY, null);
//        g.drawImage(client.Direction.Right.getImage("missile"), WIDTH / 2 + 14, bulletY, null);
//        g.drawImage(client.Direction.Down.getImage("missile"), WIDTH / 2, bulletY + 12, null);
//        g.drawImage(client.Direction.LeftUp.getImage("missile"), WIDTH / 2 - 12, bulletY - 18, null);
//        g.drawImage(client.Direction.RightUp.getImage("missile"), WIDTH / 2 + 12, bulletY - 18, null);
//        g.drawImage(client.Direction.RightDown.getImage("missile"), WIDTH / 2 + 12, bulletY + 12, null);
//        g.drawImage(client.Direction.LeftDown.getImage("missile"), WIDTH / 2 - 12, bulletY + 12, null);
}
