package AimTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//시간
class StopWatch extends Thread {
    static String timeText;
    long time = 0l;
    static long preTime = 0l;
    boolean play = true;

    // thread 클래스 안에 있는 메서드
    public void run() {
        // 현재시간 초로 변환된 값
        preTime = System.currentTimeMillis();
        while (play) {
            try {
                sleep(10);
                if (MyPanel.isStopWatch) {
                    time = System.currentTimeMillis() - preTime;
                    int m = (int) (time / 1000.0 / 60.0);
                    int s = (int) (time % (1000.0 * 60) / 1000.0);
                    int ms = (int) (time % 1000 / 10.0);
                    timeText = m + ":" + s + ":" + ms;
                    MyPanel.timeTextLb.setText(timeText);
                }
                // System.out.println(timeText);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Rect {
    int x;
    int y;
    int size;
    int num;
    Color color;
    int back;
}

class MyPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    static boolean isStopWatch = false;
    Rect[] rect = new Rect[25];
    Random ran;

    JPanel topPanel;
    JLabel mainText;
    JLabel subText;
    JButton reset;
    static JLabel timeTextLb;
    JLabel answerText;

    int a = 1;

    MyPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);
        ran = new Random();

        TopPanel();
        Reset();
        MainText();
        SubText();
        Time();
        NextNum();

        setBasic();
        num_shuffle();

    }

    // 상단 다음 숫자
    private void NextNum() {
        Font font;
        font = new Font("", Font.BOLD, 50);
        answerText = new JLabel(a + "");
        answerText.setText(a + "");
        answerText.setFont(font);
        answerText.setSize(100, 100);
        answerText.setLocation(940, 0);
        topPanel.add(answerText);
    }

    // 시간
    private void Time() {
        Font font;
        font = new Font("", Font.BOLD, 40);
        timeTextLb = new JLabel("0");
        timeTextLb.setFont(font);
        timeTextLb.setForeground(Color.BLUE);
        timeTextLb.setSize(200, 100);
        timeTextLb.setLocation(20, 0);
        topPanel.add(timeTextLb);
    }

    // sub제목 "1부터 50까지 순서대로 터치하여 없애보세요."
    private void SubText() {
        Font font;
        font = new Font("", Font.PLAIN, 20);
        subText = new JLabel("1부터 50까지 순서대로 터치하여 없애보세요.");
        subText.setFont(font);
        subText.setForeground(Color.LIGHT_GRAY);
        subText.setSize(500, 50);
        subText.setLocation(270, 160);
        add(subText);
    }

    // main제목 "1 to 50"
    private void MainText() {
        Font font = new Font("", Font.BOLD, 35);
        mainText = new JLabel("1 to 50");
        mainText.setFont(font);
        mainText.setSize(400, 100);
        mainText.setLocation(400, 100);
        add(mainText);
    }

    // 리셋버튼
    private void Reset() {
        reset = new JButton("다시하기");
        reset.setSize(100, 50);
        reset.setBackground(Color.WHITE);
        reset.setLocation(820, 750);
        reset.addActionListener(this);
        add(reset);
    }

    // 맨 위Panel
    private void TopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setSize(1000, 100);
        topPanel.setLocation(0, 0);
        add(topPanel);
    }

    public void setBasic() {

        int i = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                rect[i] = new Rect();
                rect[i].size = 100;
                rect[i].x = 220 + x * rect[i].size;
                rect[i].y = 300 + y * rect[i].size;
                rect[i].num = i + 1;
                rect[i].color = new Color(178, 204, 255);
                rect[i].back = 26 + i;
                i += 1;
            }
        }
    }

    // 숫자 넣기
    public void num_shuffle() {
        for (int i = 0; i < 1000; i++) {
            int r = ran.nextInt(25);
            int temp = rect[r].num;
            rect[r].num = rect[0].num;
            rect[0].num = temp;

            r = ran.nextInt(25);
            temp = rect[r].back;
            rect[r].back = rect[0].back;
            rect[0].back = temp;
        }
    }

    // 리셋
    public void reset() {
        a = 1;
        answerText.setText(a + "");
        timeTextLb.setText("0");
        isStopWatch = false;

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Thread.sleep(10);
            repaint();
        } catch (Exception e) {
        }
        Font font = new Font("", Font.BOLD, 45);

        for (int i = 0; i < 25; i++) {
            g.setColor(rect[i].color);
            g.fillRect(rect[i].x, rect[i].y, rect[i].size, rect[i].size);
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawRect(rect[i].x, rect[i].y, rect[i].size, rect[i].size);
            if (rect[i].num < 10) {
                g.drawString(rect[i].num + "", rect[i].x + 35, rect[i].y + 65);
            } else {
                g.drawString(rect[i].num + "", rect[i].x + 25, rect[i].y + 65);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // System.out.println(x+" "+y);
        for (int i = 0; i < 25; i++) {
            if (rect[i].x < x && x < rect[i].x + rect[i].size) {
                if (rect[i].y < y && y < rect[i].y + rect[i].size) {
                    if (rect[i].num == a) {
                        if (a == 1) {
                            isStopWatch = true;
                            StopWatch.preTime = System.currentTimeMillis();
                        }
                        if (1 <= a && a < 26) {
                            rect[i].num = rect[i].back;
                            a += 1;
                        } else {
                            a += 1;
                        }
                        if (a > 50) {
                            a = 50;
                            isStopWatch = false;
                            timeTextLb.setText(StopWatch.timeText);
                        }
                        answerText.setText(a + "");
                    }
                }
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // System.out.println(x+" "+y);
        for (int i = 0; i < 25; i++) {
            if (rect[i].x < x && x < rect[i].x + rect[i].size
                    && rect[i].y < y && y < rect[i].y + rect[i].size) {
                rect[i].color = new Color(103, 152, 255);
            } else {
                rect[i].color = new Color(178, 204, 255);
            }
        }

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("!");
        if (e.getSource() == reset) {
            setBasic();
            num_shuffle();
            reset();
        }
    }
}

public class Game2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("1 to 50 ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);
        frame.setLocationRelativeTo(null); // 실행했을떄 만튼 창이 컴퓨터 정중앙에 위치한다.
        MyPanel mp = new MyPanel();
        frame.setContentPane(mp);
        frame.setVisible(true);

        StopWatch sw = new StopWatch();
        sw.run();
    }
}