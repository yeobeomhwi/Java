package AimTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game1 extends JFrame {
    private java.awt.Image screenImage;
    private Graphics screenGraphic;

    // ===========이미지=====================
    // 과녁 이미지
    private JLabel Target_Plate = new JLabel(new ImageIcon(Main.class.getResource("../images/Target_Plate.png")));
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
    // ======================================

    // ==============버튼추가================
    private JButton exitButton = new JButton(exitButtonBasicImage);
    private JButton BackButton = new JButton(BackButtonBasicImage);

    // 윈도우 창 위치를 메뉴바를 끌어서 옮길 수 있도록 마우스 좌표 int
    private int mouseX, mouseY;

    // 폰트
    Font font = new Font("문화재돌봄체", Font.BOLD, 30);
    Font font2 = new Font("문화재돌봄체", Font.BOLD, 30);
    Font font3 = new Font("문화재돌봄체", Font.BOLD, 50);

    long prevTime; // 시간
    int Target = 30; // 클릭횟수를 저장할 카운트

    long end = System.currentTimeMillis();

    private JLabel endStr = new JLabel("플레이 시간 : " + ((System.currentTimeMillis() - prevTime) / 1000) + "초");
    // 표적 갯수카운트
    private JLabel countJLabel = new JLabel("  현재 남은 표적 갯수 : " + Target);
    private JLabel StartText = new JLabel(
            "<html><body style='text-align:center;'>30개의 목표물을 빠르게 타격하라. <br> 시작하려면 타겟을 클릭하십시오.</html>"); // 시작전 텍스트

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

    public Game1() {
        MainFrame(); // 메인 프레임

        Container(); // 컨테이너 설정
        Target_Plate(); // 타겟 이미지 설정
        ClickCount(); // 클릭 카운트 설정
        StartText(); // 시작 카운트 설정
        ExitButton();
        BackButton();
        Translucentbackground();
        MenuBar();
        MainGame(); // 타켓 생성및 클릭
        Timer();

    }

    // 하늘색 반투명 배경 설정
    private JPanel Translucentbackground() {
        JPanel blue = new JPanel();
        blue.setBackground(new Color(80, 188, 233, 100)); // R,G,B,투명도
        blue.setSize(1600, 900);
        blue.setLayout(null);
        blue.setLocation(160, 150);
        blue.setVisible(true); // 시작버튼 누르면 나오게 숨겨둠
        add(blue);
        return blue;
    }

    // 컨테이너 추가 설정
    private void Container() {
        // 컨테이너
        Container c = getContentPane();
        c.setLayout(null);
        c.add(Target_Plate);
        // c.add(bt_start);
        c.add(StartText);
        c.add(endStr);
    }

    // 타겟이미지 크기 위치 보이기 설정
    private void Target_Plate() {
        // 타겟 이미지
        Target_Plate.setLocation(923, 500);
        Target_Plate.setSize(80, 80);
        Target_Plate.setVisible(true); // 시작버튼 누르면 나오게 숨겨둠
    }

    // 종료후 시간 텍스트 설정
    private void Timer() {
        endStr.setLocation(800, 515);
        endStr.setSize(500, 50);
        endStr.setFont(font3); // 폰트 적용
        endStr.setForeground(Color.white); // 폰트 색상 적용
        endStr.setVisible(false); // 시작버튼 누르면 나오게 숨겨둠
    }

    // 클릭횟수
    private void ClickCount() {
        // 클릭 횟수카운트
        countJLabel.setLocation(785, 70);
        countJLabel.setSize(350, 50);
        countJLabel.setFont(font); // 폰트 적용
        countJLabel.setForeground(Color.WHITE); // 폰트 색상 적용
        countJLabel.setVisible(true); // 시작버튼 누르면 나오게 숨겨둠
        countJLabel.setOpaque(true); // Opaque값을 true로 미리 설정해 주어야 배경색이 적용된다.
        countJLabel.setBackground(Color.BLACK);

        add(countJLabel);
    }

    // 시작전 텍스트
    private void StartText() {
        StartText.setLocation(720, 190);
        StartText.setSize(550, 500);
        StartText.setFont(font2);
        StartText.setForeground(Color.RED);
        StartText.setVisible(true);
    }

    // 메인프레임
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

    // 메인 게임
    private void MainGame() {
        Target_Plate.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                prevTime = System.currentTimeMillis();
                StartText.setVisible(false); // 시작전 설명을 안보이게하기
                super.mouseClicked(e);
                int x = (int) (Math.random() * 1520) + 160;
                int y = (int) (Math.random() * 800) + 130;
                Target_Plate.setLocation(x, y);
                Target--;
                countJLabel.setText("  현재 남은 표적 갯수 : " + Target);
                EndGame();
            }

            // 30개를 다 클릭하면
            private void EndGame() {
                if (Target == 0) {
                    remove(Target_Plate);
                    countJLabel.setVisible(false);
                    endStr.setVisible(true);
                    endStr.setText("플레이 시간 : " + ((prevTime - end) / 1000) + "초");

                    StartText.setVisible(true); // 시작전 설명을 안보이게하기
                    StartText.setText(
                            "<html><body style='text-align:center;'>선택창으로 가시려면 <br> 좌측상단 뒤로가기 버튼을 눌러주세요.<html>");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Game1();

    }
}
