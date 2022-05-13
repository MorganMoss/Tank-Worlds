package za.co.wethinkcode.robotworlds.client.SwingGUI;//import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelperMethods {


    /**
     * Get {@link Image} from given file
     *
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
}
