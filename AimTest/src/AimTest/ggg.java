package AimTest;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

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

class MyPanel extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
    private java.awt.Image screenImage;
    private Graphics screenGraphic;

    static boolean isStopWatch = false;
    Rect[] rect = new Rect[25];
    Random ran;
    StopWatch sw = new StopWatch();

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

    // ==============버튼추가================
    private JButton exitButton = new JButton(exitButtonBasicImage);
    private JButton BackButton = new JButton(BackButtonBasicImage);

    // 윈도우 창 위치를 메뉴바를 끌어서 옮길 수 있도록 마우스 좌표 int
    private int mouseX, mouseY;

    JPanel topPanel;
    JLabel mainText;
    JLabel subText;
    JButton reset;
    static JLabel timeTextLb;
    JLabel answerText;
    int a = 1;

    // 가장 첫번쨰로 화면을 그려주는 함수
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

    MyPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        ran = new Random();
        sw.run();
        MainFrame(); // 메인 프레임
        Container(); // 컨테이너
        Reset(); // 리셋 버튼
        TopPanel(); // 맨 위Panel
        MainText(); // 메인 제목 "1 to 50"
        SubText(); // sub제목 "1부터 50까지 순서대로 터치하여 없애보세요."
        Time(); // 시간
        NextNum(); // 다음 숫자
        num_shuffle(); // 박스안 숫자넣기
        setBasic(); // 박스 생성
        ExitButton(); // 종료 버튼
        BackButton(); // 메인화면 이동 버튼
        MenuBar(); // 상단 메뉴바

    }

    private void Container() {
        // 컨테이너
        Container c = getContentPane();
        c.setLayout(null);
        c.add(topPanel);
        c.add(mainText);
        c.add(subText);
        c.add(reset);
        c.add(timeTextLb);
        c.add(answerText);
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

    // 맨 위Panel
    private void TopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setSize(1920, 100);
        topPanel.setLocation(0, 980);
        add(topPanel);
    }

    // main제목 "1 to 50"
    private void MainText() {

        Font font = new Font("", Font.BOLD, 40);
        mainText = new JLabel("1 to 50");
        mainText.setFont(font);
        mainText.setSize(400, 100);
        mainText.setLocation(860, 35);
        mainText.setForeground(Color.white); // 폰트 색상 적용
        add(mainText);
    }

    // sub제목 "1부터 50까지 순서대로 터치하여 없애보세요."
    private void SubText() {

        Font font;
        font = new Font("", Font.PLAIN, 30);
        subText = new JLabel("1부터 50까지 순서대로 터치하여 없애보세요.");
        subText.setFont(font);
        subText.setForeground(Color.LIGHT_GRAY);
        subText.setSize(700, 50);
        subText.setLocation(610, 100);
        add(subText);
    }

    // 리셋버튼
    private void Reset() {

        reset = new JButton("다시하기");
        reset.setSize(150, 80);
        reset.setBackground(Color.WHITE);
        reset.setLocation(150, 150);
        reset.addActionListener(this);
        add(reset);
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
    public void reset2() {
        a = 1;
        answerText.setText(a + "");
        timeTextLb.setText("0");
        isStopWatch = false;

    }

    // 종료 버튼
    private void ExitButton() {
        // Exit 버튼
        exitButton.setBounds(1870, 0, 50, 50);
        exitButton.setBorderPainted(false); // JButton의 외곽선을 지운다.
        exitButton.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        exitButton.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                exitButton.setIcon(exitButtonEnteredImage);
                exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                exitButton.setIcon(exitButtonBasicImage);
                exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);
    }

    // 뒤로가기 버튼
    private void BackButton() {
        // Back 버튼
        BackButton.setBounds(10, 0, 50, 50);
        BackButton.setBorderPainted(false); // JButton의 외곽선을 지운다.
        BackButton.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        BackButton.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                BackButton.setIcon(BackButtonEnteredImage);
                BackButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                BackButton.setIcon(BackButtonBasicImage);
                BackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // 선택창으로 이동
                new AimTest().EnterMain();
                dispose();
            }
        });
        add(BackButton);
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

    protected void MainGame(Graphics g) {
        super.paintComponents(g);
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
            reset2();
        }
    }
}

public class ggg {
    public static void main(String[] args) {
        new ggg();
    }
}
