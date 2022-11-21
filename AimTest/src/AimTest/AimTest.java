package AimTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AimTest extends JFrame {

    private Image screenImage;
    private Graphics screenGraphic;

    // ==========================이미지=======================================================
    // 시작화면 배경 이미지
    private Image BackGround = new ImageIcon(Main.class.getResource("../images/IntroBackGround.jpg")).getImage();
    // 상단 메뉴바 이미지
    private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
    // 상단 메뉴바 종료 버튼 이미지
    private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
    private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
    // 시작 버튼 이미지
    private ImageIcon StartButtonBasicImage = new ImageIcon(Main.class.getResource("../images/StartButtonBasic.png"));
    private ImageIcon StartButtonEnteredImage = new ImageIcon(
            Main.class.getResource("../images/StartButtonEntered.png"));
    // 종료 버튼 이미지
    private ImageIcon QuitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/QuitButtonBasic.png"));
    private ImageIcon QuitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/QuitButtonEntered.png"));
    // 스테이지 버튼
    private ImageIcon Seconds30ButtonBasicImage = new ImageIcon(Main.class.getResource("../images/Seconds30Basic.png"));
    private ImageIcon Seconds30ButtonEnteredImage = new ImageIcon(
            Main.class.getResource("../images/Seconds30Entered.png"));
    private ImageIcon Shots30ButtonBasicImage = new ImageIcon(Main.class.getResource("../images/Shots30Basic.png"));
    private ImageIcon Shots30ButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/Shots30Entered.png"));
    private ImageIcon Oneto50BasicImage = new ImageIcon(Main.class.getResource("../images/Oneto50Basic.png"));
    private ImageIcon Oneto50EenteredImage = new ImageIcon(Main.class.getResource("../images/Oneto50Eentered.png"));
    // 메인화면 버튼
    private ImageIcon BackButtonBasicImage = new ImageIcon(Main.class.getResource("../images/BackIconBasic.png"));
    private ImageIcon BackButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/BackIconEntered.png"));
    // 과녁 이미지
    private JLabel Target_Plate = new JLabel(new ImageIcon(Main.class.getResource("../images/Target_Plate.png")));

    // =========================================버튼 추가============================

    private JButton exitButton = new JButton(exitButtonBasicImage);
    private JButton StartButton = new JButton(StartButtonBasicImage);
    private JButton QuitButton = new JButton(QuitButtonBasicImage);
    private JButton Seconds30Button = new JButton(Seconds30ButtonBasicImage);
    private JButton Shots30Button = new JButton(Shots30ButtonBasicImage);
    private JButton Oneto50Button = new JButton(Oneto50BasicImage);
    private JButton BackButton = new JButton(BackButtonBasicImage);
    // ======================================================================================================

    // 윈도우 창 위치를 메뉴바를 끌어서 옮길 수 있도록 마우스 좌표 int
    private int mouseX, mouseY;

    // 게임 메뉴로 넘어왔는지를 표시하기 위한 변수

    public AimTest() {
        setUndecorated(true); // 기본 메뉴바 안보이게하기
        setTitle("Aim Test");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false); // 한번 만들어진 창은 사용자가 조절을 못한다.
        setLocationRelativeTo(null); // 실행했을떄 만튼 창이 컴퓨터 정중앙에 위치한다.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료하면 프로그램 전체가 종료
        setVisible(true); // 창이보이게해준다.
        setBackground(new Color(0, 0, 0, 0)); // 배경색
        setLayout(null); // 배치관리자 없음

        // 시작화면에 불필요한 버튼 숨기기
        Seconds30Button.setVisible(false);
        Shots30Button.setVisible(false);
        Oneto50Button.setVisible(false);
        BackButton.setVisible(false);

        // =========================================버튼
        // 기능=========================================

        ExitButton();
        StartButton();
        QuitButton();
        Seconds30Button();
        Shots30Button();
        BrokenButton();
        BackButton();
        MenuBar();
    }

    // 메뉴바
    private void MenuBar() {
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

    // Back 버튼
    private void BackButton() {
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
                EnterMain();
                remove(Target_Plate);
            }
        });
        add(BackButton);
    }

    // Shots30 버튼 타겟 30개를 빠르게 뿌수기 게임 1
    private void Shots30Button() {
        Shots30Button.setBounds(653, 123, 700, 200);
        Shots30Button.setBorderPainted(false); // JButton의 외곽선을 지운다.
        Shots30Button.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        Shots30Button.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        Shots30Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                Shots30Button.setIcon(Shots30ButtonEnteredImage);
                Shots30Button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                Shots30Button.setIcon(Shots30ButtonBasicImage);
                Shots30Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new Game1();
                dispose();
            }
        });
        add(Shots30Button);
    }

    // Broken 버튼 게임3
    private void BrokenButton() {
        Oneto50Button.setBounds(653, 440, 700, 200);
        Oneto50Button.setBorderPainted(false); // JButton의 외곽선을 지운다.
        Oneto50Button.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        Oneto50Button.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        Oneto50Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                Oneto50Button.setIcon(Oneto50EenteredImage);
                Oneto50Button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                Oneto50Button.setIcon(Oneto50BasicImage);
                Oneto50Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new Game2();
                dispose();
            }
        });
        add(Oneto50Button);
    }

    // Seconds30 버튼 게임 3
    private void Seconds30Button() {
        Seconds30Button.setBounds(653, 757, 700, 200);
        Seconds30Button.setBorderPainted(false); // JButton의 외곽선을 지운다.
        Seconds30Button.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        Seconds30Button.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        Seconds30Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                Seconds30Button.setIcon(Seconds30ButtonEnteredImage);
                Seconds30Button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                Seconds30Button.setIcon(Seconds30ButtonBasicImage);
                Seconds30Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {

                // 30초 게임 버튼 이벤트
                new Game3();
                dispose();
            }
        });
        add(Seconds30Button);
    }

    // Quit 버튼
    private void QuitButton() {
        QuitButton.setBounds(720, 850, 440, 130);
        QuitButton.setBorderPainted(false); // JButton의 외곽선을 지운다.
        QuitButton.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        QuitButton.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        QuitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                QuitButton.setIcon(QuitButtonEnteredImage);
                QuitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                QuitButton.setIcon(QuitButtonBasicImage);
                QuitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
        add(QuitButton);
    }

    // Start 버튼
    private void StartButton() {
        StartButton.setBounds(720, 700, 440, 130);
        StartButton.setBorderPainted(false); // JButton의 외곽선을 지운다.
        StartButton.setContentAreaFilled(false); // JButton의 내용영역 채우기 않함
        StartButton.setFocusPainted(false); // JButton이 선택 되었을떄 생기는 테두리 사용안함
        StartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역 안으로 들어올떄
                StartButton.setIcon(StartButtonEnteredImage);
                StartButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 버튼에 마우스가 올라가면 핸드커서
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역 밖으로 나갈떄
                StartButton.setIcon(StartButtonBasicImage);
                StartButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 버튼에 마우스가 내려가면 기본커서
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // 게임 시작 이벤트
                EnterMain();
            }
        });
        add(StartButton);
    }

    // Exit 버튼
    private void ExitButton() {
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

    // 메인 화면
    public void EnterMain() {
        // 메인화면일때 시작과 종료 버튼을 안보이게
        StartButton.setVisible(false);
        QuitButton.setVisible(false);
        Seconds30Button.setVisible(true);
        Shots30Button.setVisible(true);
        Oneto50Button.setVisible(true);
        BackButton.setVisible(false);
        BackGround = new ImageIcon(Main.class.getResource("../images/GameBackGround.png")).getImage();
    }

    // (3번 실수 하면 끝 오래버티기)
    public void GameStart3() {
        StartButton.setVisible(false);
        QuitButton.setVisible(false);
        Seconds30Button.setVisible(false);
        Shots30Button.setVisible(false);
        Oneto50Button.setVisible(false);
        BackButton.setVisible(true);
        BackGround = new ImageIcon(Main.class.getResource("../images/GameStartBackGround.png")).getImage();
    }

}