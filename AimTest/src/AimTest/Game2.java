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

import javax.swing.ImageIcon;
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

        // 맨 위Panel
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setSize(1920, 100);
        topPanel.setLocation(0, 0);
        add(topPanel);
        // 버튼
        reset = new JButton("다시하기");
        reset.setSize(200, 50);
        reset.setBackground(Color.WHITE);
        reset.setLocation(50, 220);
        reset.addActionListener(this);
        add(reset);
        // main제목
        Font font = new Font("", Font.BOLD, 35);
        mainText = new JLabel("1 to 50");
        mainText.setFont(font);
        mainText.setSize(200, 100);
        mainText.setLocation(850, 100);
        add(mainText);
        // sub제목
        font = new Font("", Font.PLAIN, 20);
        subText = new JLabel("1부터 50까지 순서대로 터치하여 없애보세요.");
        subText.setFont(font);
        subText.setForeground(Color.red);
        subText.setSize(500, 50);
        subText.setLocation(720, 160);
        add(subText);
        // 시간
        font = new Font("", Font.BOLD, 50);
        timeTextLb = new JLabel("0");
        timeTextLb.setFont(font);
        timeTextLb.setForeground(Color.BLUE);
        timeTextLb.setSize(200, 100);
        timeTextLb.setLocation(20, 0);
        topPanel.add(timeTextLb);
        // 다음 숫자
        font = new Font("", Font.BOLD, 50);
        answerText = new JLabel(a + "");
        answerText.setText(a + "");
        answerText.setFont(font);
        answerText.setSize(100, 100);
        answerText.setLocation(1850, 0);
        topPanel.add(answerText);

        setBasic();
        num_shuffle();

    }

    public void setBasic() {

        int i = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                rect[i] = new Rect();
                rect[i].size = 100;
                rect[i].x = 670 + x * rect[i].size;
                rect[i].y = 300 + y * rect[i].size;
                rect[i].num = i + 1;
                rect[i].color = new Color(178, 204, 255);
                rect[i].back = 26 + i;
                i += 1;
            }
        }
    }

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

    public void reset() {
        a = 1;
        answerText.setText(a + "");
        timeTextLb.setText("0");
        isStopWatch = false;

        Font font = new Font("", Font.BOLD, 45);

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
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        // System.out.println(x+" "+y);
        for (int i = 0; i < 25; i++) {
            if (rect[i].x < x && x < rect[i].x + rect[i].size
                    && rect[i].y < y && y < rect[i].y + rect[i].size) {
                rect[i].color = new Color(255, 192, 205); // 마우스 올리면 바뀌는색상
            } else {
                rect[i].color = new Color(102, 0, 102); // 기본색상
            }
        }

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        System.out.println("!");
        if (e.getSource() == reset) {
            setBasic();
            num_shuffle();
            reset();
        }
    }
}

public class Game2 extends JFrame {
    private java.awt.Image screenImage;
    private Graphics screenGraphic;
    // 시작화면 배경 이미지
    private java.awt.Image BackGround = new ImageIcon(Main.class.getResource("../images/GameStartBackGround.png"))
            .getImage();
    // 상단 메뉴바 이미지
    private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
    // 상단 메뉴바 종료 버튼 이미지
    private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
    private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
    // 메인화면 버튼
    private ImageIcon BackButtonBasicImage = new ImageIcon(Main.class.getResource("../images/BackIconBasic.png"));
    private ImageIcon BackButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/BackIconEntered.png"));
    // ======================================+

    // ==============버튼추가================
    private JButton exitButton = new JButton(exitButtonBasicImage);
    private JButton BackButton = new JButton(BackButtonBasicImage);

    public void paint(Graphics g) // 가장 첫번쨰로 화면을 그려주는 함수
    {
        screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void screenDraw(Graphics g) {
        g.drawImage(BackGround, 0, 0, null);
        paintComponents(g); // 항상 고정하는 이미지 이지에 paintComponents로 구현
        this.repaint(); // 다시 페인트 함수를 불러오는것
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("순발력 테스트");
        frame.setUndecorated(true); // 기본 메뉴바 안보이게하기
        frame.setResizable(false); // 한번 만들어진 창은 사용자가 조절을 못한다.
        frame.setLocationRelativeTo(null); // 실행했을떄 만튼 창이 컴퓨터 정중앙에 위치한다.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료하면 프로그램 전체가 종료
        frame.setVisible(true); // 창이보이게해준다.
        frame.setBackground(new Color(0, 0, 0, 0)); // 배경색
        frame.setLayout(null); // 배치관리자 없음
        frame.setSize(1920, 1080);
        MyPanel mp = new MyPanel();
        frame.setContentPane(mp);

        StopWatch sw = new StopWatch();
        sw.run();
    }
}
