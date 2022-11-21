package AimTest;

import javax.swing.*;
import java.awt.*;

class TimerBar extends JLabel implements Runnable {
    int width = 450, height = 50;
    int x = 10, y = 50;
    Color color = new Color(255, 0, 0);

    int second;

    public TimerBar(int second) {
        setBackground(color);
        setOpaque(true);
        setBounds(x, y, width, height);

        this.second = second;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / (width / second));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getWidth() > 0) {
                width -= 1; // 너비가 1씩 줄어듦
                // System.out.println(width);
                setBounds(x, y, width, height);
            } else {
                // System.out.println("종료");
                break;
            }
        }
    }
}

class TimerNum extends JLabel implements Runnable {
    int width = 75, height = 75;
    int x = 200, y = 150;

    int second;

    public TimerNum(int second) {
        setOpaque(true);
        setBounds(x, y, width, height);
        setForeground(Color.BLUE);
        setText(second + "");
        setFont(new Font("맑은고딕", Font.PLAIN, 50));
        setHorizontalAlignment(JLabel.CENTER);

        this.second = second;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // 1초
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (second > 0) {
                second -= 1; // 1초씩 줄어듦
                setText(second + "");
            } else {
                // System.out.println("종료");
                break;
            }
        }
    }
}

public class TimerTest extends JFrame {
    private JPanel panel;

    private TimerBar timerBar;
    private Thread threadBar;

    private TimerNum timerNum;
    private Thread threadNum;

    class TimerNum extends JLabel implements Runnable {
        int width = 75, height = 75;
        int x = 200, y = 150;

        int second;

        public TimerNum(int second) {
            setOpaque(true);
            setBounds(x, y, width, height);
            setForeground(Color.BLUE);
            setText(second + "");
            setFont(new Font("맑은고딕", Font.PLAIN, 50));
            setHorizontalAlignment(JLabel.CENTER);

            this.second = second;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000); // 1초
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (second > 0) {
                    second -= 1; // 1초씩 줄어듦
                    setText(second + "");
                } else {
                    // System.out.println("종료");
                    break;
                }
            }
        }
    }

    public TimerTest() {
        int second = 30; // 초

        panel = new JPanel();
        panel.setLayout(null);

        timerBar = new TimerBar(second);
        threadBar = new Thread(timerBar);
        threadBar.start();
        panel.add(timerBar);

        timerNum = new TimerNum(second);
        threadNum = new Thread(timerNum);
        threadNum.start();
        panel.add(timerNum);

        add(panel);
        setTitle("타이머");
        setSize(470, 300);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TimerTest();
    }
}