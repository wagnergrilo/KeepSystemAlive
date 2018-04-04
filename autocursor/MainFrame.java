package autocursor;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends javax.swing.JFrame {

//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledExecutorService scheduler;
    boolean centerFlag;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Robot robot;

    //screen center coordinates
    int xCoord = screenSize.width / 2;
    int yCoord = screenSize.height / 2;

    public MainFrame() {
        this.setTitle("");
        this.setResizable(false);
        initComponents();
        centerFlag = true; //true means the cursor will be moved to the center
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            System.out.println("Error!");
            System.out.println(ex.getMessage());
        }
    }

    final Runnable movePointer = new Runnable() {
        @Override
        public void run() {
            if (centerFlag) {
                robot.mouseMove(xCoord, yCoord);
                xCoord = xCoord + 20;
                centerFlag = false;
            } else {
                robot.mouseMove(xCoord, yCoord);
                xCoord = xCoord - 20;
                centerFlag = true;
            }
//            robot.keyPress(KeyEvent.VK_G);
//            robot.keyRelease(KeyEvent.VK_G);
        }
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (startButton.getText().equals("Start")) {
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(movePointer, 2, 2, TimeUnit.SECONDS);
            startButton.setText("Stop");
        } else if (startButton.getText().equals("Stop")){
            scheduler.shutdown();
            startButton.setText("Start");
        }
    }//GEN-LAST:event_startButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
