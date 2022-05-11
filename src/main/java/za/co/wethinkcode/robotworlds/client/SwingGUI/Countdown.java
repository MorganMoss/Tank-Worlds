package za.co.wethinkcode.robotworlds.client.SwingGUI;

import javax.swing.*;
import java.awt.*;

public class Countdown {
    private final JLabel label = new JLabel("...");
    private final Timer timer;
    private int countTime=5;


    public Countdown(Graphics g,String timerText) {
        final JButton button = new JButton(timerText);

        timer = new Timer(1000, e -> {
            if (countTime > 0) {
                label.setText(String.valueOf(countTime--));
            } else {
                ((Timer) (e.getSource())).stop();
                countTime = 5;
                button.setEnabled(true);
            }
        });
        timer.setInitialDelay(0);

        button.addActionListener(e -> {
            timer.start();
            button.setEnabled(false);
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.PAGE_START);
        frame.add(button, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
    }

}
