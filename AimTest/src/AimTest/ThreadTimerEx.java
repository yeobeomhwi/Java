package AimTest;

import java.awt.*;
import javax.swing.*;

class TimerThread extends Thread {
    private JLabel timerLabel; // 타이머 값이 출력되는 레이블

    public TimerThread(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    // 스레드 코드.run()이 종료하면 스레드 종료
    @Override
    public void run() {
        int n = 3;
        while (true) {
            timerLabel.setText(Integer.toString(n));
            if (n == 0) {
                return;
            }
            n--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return; // 예외가 발생하면 스레드 종료
            }
            Thread.interrupted();
        }
    }
}

public class ThreadTimerEx extends JFrame {
    public ThreadTimerEx() {
        setTitle("카운트 다운");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        // 타이머 값을 출력할 레이블 생성
        JLabel timerLabel = new JLabel();
        timerLabel.setFont(new Font("Gothic", Font.BOLD, 80));
        c.add(timerLabel);

        TimerThread th = new TimerThread(timerLabel);

        setSize(1920, 1080);
        setVisible(true);

        th.start();

    }

    public static void main(String[] args) {
        new ThreadTimerEx();
    }
}