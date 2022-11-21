package AimTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
    private java.awt.Image screenImage;
    private Graphics screenGraphic;

    // 시작화면 배경 이미지
    private java.awt.Image BackGround = new ImageIcon(Main.class.getResource("../images/GameStartBackGround.png"))
            .getImage();
    // 상단 메뉴바 이미지
    private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

    // 윈도우 창 위치를 메뉴바를 끌어서 옮길 수 있도록 마우스 좌표 int
    private int mouseX, mouseY;

    // 타임바
    private TimerBar timerBar;
    private Thread threadBar;

    private TimerNum timerNum;
    private Thread threadNum;

    public void paint(Graphics g) {
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

    // 메뉴바
    private void MenuBar() {
        // 메뉴바
        menuBar.setBounds(0, 0, 1920, 50); // setbounds 는 위치와 크기를 정함
        menuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // 마우스로 메뉴바를 눌렸을떄
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        menuBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // 컴포넌트 위에서 마우스가 눌러진 상태로 드래그 될떄.
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });
        add(menuBar);
    }

    public TimerTest() {
        int second = 30; // 초

        // 컨테이너
        Container(second);
        MenuBar();
        MainFrame();
    }

    private void MainFrame() {
        setUndecorated(true); // 기본 메뉴바 안보이게하기
        setTitle("Aim Test");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false); // 한번 만들어진 창은 사용자가 조절을 못한다.
        setLocationRelativeTo(null); // 실행했을떄 만튼 창이 컴퓨터 정중앙에 위치한다.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료하면 프로그램 전체가 종료
        setVisible(true); // 창이보이게해준다.
        setBackground(new Color(0, 0, 0, 0)); // 배경색
        setLayout(null); // 배치관리자 없음
        setSize(1920, 1080);
        setVisible(true);
    }

    private void Container(int second) {
        Container c = getContentPane();
        c.setLayout(null);

        timerBar = new TimerBar(second);
        threadBar = new Thread(timerBar);
        threadBar.start();
        c.add(timerBar);

        timerNum = new TimerNum(second);
        threadNum = new Thread(timerNum);
        threadNum.start();
        c.add(timerNum);
    }

    public static void main(String[] args) {
        new TimerTest();
    }
}