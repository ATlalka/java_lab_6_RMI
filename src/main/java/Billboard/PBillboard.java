package Billboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class PBillboard extends JFrame {
    private JPanel contentPane;
    private Billboard billboard;
    private JTextArea textArea;

    public PBillboard(Billboard billboard) {
        this.billboard = billboard;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 559, 376);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBounds(10, 10, 525, 319);
        contentPane.add(textArea);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    billboard.getiManager().unbindBillboard(billboard.getId());
                    billboard.getT().interrupt();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public void setText(String text){
        textArea.setText(text);
        contentPane.repaint();
    }
}
