package utilities;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Skarab
 */
public class LogWindow extends JFrame {

    private JTextArea textArea;
    private String log;

    public LogWindow(OutputStream os) {
        super("Logger");

        OutputStream myCaptureStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        };
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                log("" + (char) b);
            }
        };
        PrintStream output = new PrintStream(out, true);
        System.setOut(output);

        log = "";
        textArea = new JTextArea(32, 26);
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        add(scroll, BorderLayout.CENTER);
        textArea.setEditable(false);
        pack();
        setVisible(true);
    }

    public void log(String str) {
        log += str;
        textArea.setText(log);
    }
}
