package com.seu.vCampus.Client.Home;

import com.alee.laf.WebLookAndFeel;
import com.seu.vCampus.Client.AcademicAffairs.Admin.AdminMainPanel;
import com.seu.vCampus.Client.AcademicAffairs.Student.SelectCoursesPanel;
import com.seu.vCampus.Client.AcademicAffairs.Student.StudentAcademicMainPanel;
import com.seu.vCampus.Client.AcademicAffairs.Teacher.TeacherMainPanel;
import com.seu.vCampus.Client.Bank.Bank;
import com.seu.vCampus.Client.BasicInformation.BasicInformationPanel;
import com.seu.vCampus.Client.Common;
import com.seu.vCampus.Client.Launcher.Launcher;
import com.seu.vCampus.Client.Library.AdminLib;
import com.seu.vCampus.Client.Library.StuLib;
import com.seu.vCampus.Client.Shop.MainShop;
import com.seu.vCampus.Client.Shop.MangerShop;
import com.seu.vCampus.util.*;

import static java.lang.Thread.sleep;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;


public class Home extends JFrame{

    private Common homeData;
    private static Point origin = new Point();
    private static ImageIcon TitleIcon = new ImageIcon(Common.picturePath +"/icon/head/logo.png");
    private static ImageIcon Home = new ImageIcon(Common.picturePath +"/icon/left/Home.png");
    private static ImageIcon Library = new ImageIcon(Common.picturePath +"/icon/left/library.png");
    private static ImageIcon Shop = new ImageIcon(Common.picturePath +"/icon/left/shop.png");
    private static ImageIcon Bank = new ImageIcon(Common.picturePath +"/icon/left/Bank.png");
    private static ImageIcon Edu = new ImageIcon(Common.picturePath +"/icon/left/school.png");
    private static ImageIcon UserImage = new ImageIcon(Common.picturePath +"/icon/left/user.png");

    private JTabbedPane tabbedPane;
    private Bank bankPanel;
    private BasicInformationPanel homePanel;
    private AdminMainPanel adminMainPanel;
    private TeacherMainPanel teacherMainPanel;
    private SelectCoursesPanel coursePanelS;
    private MainShop mainShopPanel;
    private MangerShop mangerShopPanel;
    private JPanel libraryPanel;
    private AdminLib libraryManager;
    private StudentAcademicMainPanel sam;
    private TeacherMainPanel teacherPanel;
    private AdminMainPanel adminPanel;

    private int skinNumber = 1;
    private Thread updatePanel = new Thread(new Runnable() {
        @Override
        public void run() {
            double EFlag = homeData.getUser().getECardBalance();
            short BFlag = homeData.getUser().getLendBooksNumber();
            while (true){
                if(EFlag != homeData.getUser().getECardBalance() || BFlag != homeData.getUser().getLendBooksNumber()){
                    homePanel = new BasicInformationPanel("0"+Integer.toString(skinNumber));
                    tabbedPane.setComponentAt(0,homePanel);
                    EFlag = homeData.getUser().getECardBalance();
                    BFlag = homeData.getUser().getLendBooksNumber();
                }
                try{
                    sleep(1000);
                }catch (InterruptedException ie ){
                    ie.printStackTrace();
                }
            }
        }
    });


    private void LoadCommon(){
        homeData = Common.getInstance();
    }


    private void initialize() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        InitGlobalFont(new Font("Microsoft Yahei", Font.BOLD, 17));

        LoadCommon();
        homeData.setSkinNumber(skinNumber);
        WebLookAndFeel.install ();



        {
            String ECard = homeData.getUser().getECardNumber();
            System.out.println(ECard);
            homeData.getShopInformation().setECardNumber(ECard);
            homeData.getBookInformation().setECardNumber(ECard);
            homeData.getUserCount().setECardNumber(ECard);


            homeData.getBookInformation().setType(Message.MESSAGE_TYPE.TYPE_QUERY_BOOKS);
            homeData.getIO().SendMessages(homeData.getBookInformation());
            homeData.setBookInformation((BookManage)homeData.getIO().ReceiveMessage());

            homeData.getUserCount().setType(Message.MESSAGE_TYPE.TYPE_QUERY_BANK_COUNT);
            homeData.getIO().SendMessages(homeData.getUserCount());
            homeData.setUserCount((BankCount)homeData.getIO().ReceiveMessage());
            System.out.println(homeData.getUserCount().getBankPassword());

            homeData.getShopInformation().setType(Message.MESSAGE_TYPE.TYPE_QUERY_GOODS);
            homeData.getIO().SendMessages(homeData.getShopInformation());
            homeData.setShopInformation((ShopManage)homeData.getIO().ReceiveMessage());

            homeData.getNewsList().setType(Message.MESSAGE_TYPE.TYPE_QUERY_NEWS);
            homeData.getIO().SendMessages(homeData.getNewsList());
            homeData.setNewsList((NewsManage)homeData.getIO().ReceiveMessage());
            System.out.println("");
        }


        {
            homePanel = new BasicInformationPanel("01");
            bankPanel = new Bank();
            mainShopPanel = new MainShop();
            mangerShopPanel = new MangerShop();
            libraryPanel = new StuLib().LibMPanel;
            libraryManager = new AdminLib();
            teacherPanel = new TeacherMainPanel();
            adminMainPanel = new AdminMainPanel();
        }


        setBounds(200, 150, 1200, 864);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(63, 87, 123));

        /**
         * @此处代码块用来初始化Home的顶部与侧部装饰元素
         * */
        {
            JLabel Icon = new JLabel(TitleIcon);
            Icon.setBounds(10, 0, 350, 64);
            getContentPane().add(Icon);


            JLabel LogOut = new JLabel("注销");
            LogOut.setBounds(1000, 8, 50, 50);
            LogOut.setForeground(Color.WHITE);
            LogOut.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                    homeData.reset();
                    new Launcher();
                }
            });
            LogOut.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    LogOut.setForeground(Color.gray);
                }
            });
            LogOut.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    LogOut.setForeground(Color.WHITE);
                }
            });
            getContentPane().add(LogOut);

            JLabel User = new JLabel("欢迎！" + homeData.getUser().getName());
            User.setForeground(Color.WHITE);
            User.setBounds(850, 8, 150, 50);
            getContentPane().add(User);


            JLabel Time = new JLabel();
            Date now = new Date();
            Time.setBounds(500, 8, 300, 50);
            getContentPane().add(Time);
            Time.setForeground(Color.WHITE);
            Timer timer;
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Time.setText(new SimpleDateFormat("yyyy年MM月dd日 EEEE hh:mm:ss").format(new Date()));
                }
            });
            timer.start();

            JLabel Smallest = new JLabel("—");
            Smallest.setBounds(1120, 8, 25, 50);
            Smallest.setForeground(Color.WHITE);
            getContentPane().add(Smallest);
            Smallest.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setExtendedState(JFrame.ICONIFIED);
                }
            });
            Smallest.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Smallest.setForeground(Color.gray);
                }
            });
            Smallest.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    Smallest.setForeground(Color.WHITE);
                }
            });


            JLabel exit = new JLabel("X");
            exit.setBounds(1160, 8, 25, 50);
            exit.setForeground(Color.WHITE);
            getContentPane().add(exit);
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    exit.setForeground(Color.gray);
                }
            });
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    exit.setForeground(Color.WHITE);
                }
            });


            tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
            tabbedPane.setBackground(new Color(63, 87, 123));
            tabbedPane.setBounds(0, 64, 1200, 800);
            getContentPane().add(tabbedPane);


            tabbedPane.addTab("主页", Home, homePanel, null);
            tabbedPane.setForeground(Color.WHITE);

            JLabel skin = new JLabel("换肤");
            skin.setBounds(400, 8, 50, 50);
            skin.setForeground(Color.WHITE);
            getContentPane().add(skin);
            skin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    skin.setForeground(Color.gray);
                }
            });
            skin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    skin.setForeground(Color.WHITE);
                }
            });
            skin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(skinNumber == 4){
                        skinNumber = 1;
                    }else {
                        skinNumber++;
                    }
                    homeData.setSkinNumber(skinNumber);

                    switch (skinNumber) {
                        case 1:
                            getContentPane().setBackground(new Color(63, 87, 123));
                            tabbedPane.setBackground(new Color(63, 87, 123));
                            homePanel = new BasicInformationPanel("01");
                            tabbedPane.setComponentAt(0, homePanel);
                            mainShopPanel.initialization();
                            bankPanel.initialization();
                            mangerShopPanel.initialization();
                            break;
                        case 2:
                            getContentPane().setBackground(Color.BLACK);
                            tabbedPane.setBackground(Color.BLACK);
                            homePanel = new BasicInformationPanel("02");
                            tabbedPane.setComponentAt(0, homePanel);
                            mainShopPanel.initialization();
                            bankPanel.initialization();
                            mangerShopPanel.initialization();
                            break;
                        case 3:
                            getContentPane().setBackground(new Color(85, 20, 0));
                            tabbedPane.setBackground(new Color(85, 20, 0));
                            homePanel = new BasicInformationPanel("03");
                            tabbedPane.setComponentAt(0, homePanel);
                            mainShopPanel.initialization();
                            bankPanel.initialization();
                            mangerShopPanel.initialization();
                            break;
                        case 4:
                            getContentPane().setBackground(new Color(0, 70, 40));
                            tabbedPane.setBackground(new Color(0, 70, 40));
                            homePanel = new BasicInformationPanel("04");
                            tabbedPane.setComponentAt(0, homePanel);

                            skinNumber = 0;
                            mainShopPanel.initialization();
                            bankPanel.initialization();
                            mangerShopPanel.initialization();

                            break;
                    }

                }
            });
        }


        System.out.println(homeData.getUser().getAuthorityLevel());
        switch (homeData.getUser().getAuthorityLevel()){
            case GROUP_USER_MANAGER:
            {
                JPanel UserManager = new JPanel();
                tabbedPane.addTab("用户管理",UserImage,UserManager,null);
                break;
            }
            case GROUP_STUDENT:{
                tabbedPane.addTab("图书", Library, libraryPanel, null);

                sam = new StudentAcademicMainPanel();
                tabbedPane.addTab("教务", Edu, sam, null);


                JPanel panel_3 = new JPanel();
                tabbedPane.addTab("商店",Shop, mainShopPanel.getPanel(), null);

                JPanel panel_4 = new JPanel();
                tabbedPane.addTab("银行", Bank, bankPanel.getPanel(), null);
                break;
            }
            case GROUP_TEACHER:{
                JPanel panel_1 = new JPanel();
                tabbedPane.addTab("图书馆", Library, libraryPanel, null);


                tabbedPane.addTab("教务", Edu, teacherPanel, null);

                JPanel panel_3 = new JPanel();

                tabbedPane.addTab("商店",Shop, mainShopPanel.getPanel(), null);


                JPanel panel_4 = new JPanel();
                tabbedPane.addTab("银行", Bank, bankPanel.getPanel(), null);
                break;
            }
            case GROUP_LIBRARY_MANAGER:{
                tabbedPane.addTab("图书管理",Library,libraryManager.getPanel(),null);
                break;
            }
            case GROUP_SHOP_MANAGER:{
                tabbedPane.addTab("商品管理",Shop,mangerShopPanel.$$$getRootComponent$$$(),null);
                break;
            }
            case GROUP_ACADEMIC_MANAGER: {
                tabbedPane.addTab("教务管理", Edu, adminMainPanel,null);
                break;
            }

        }


        /***
         * @此处的代码块用来为窗口提供鼠标拖动功能
         */

        {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    origin.x = e.getX();
                    origin.y = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point p = getLocation();
                    setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                }
            });
        }

        setVisible(true);

    }

    public Home() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.setUndecorated(true);
        initialize();
        updatePanel.start();
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Common temp = Common.getInstance();
                    Person test = new Person();
                    test.setAuthorityLevel(Person.USER_GROUP.GROUP_STUDENT);
                    temp.setUser(test);
                    News A = new News("https://ak.hypergryph.com/index","title",new Date());
                    ArrayList<News> aa = new ArrayList<>();
                    for(int i =0;i<4;i++){
                        aa.add(A);
                    }
                    temp.getNewsList().setNews(aa);
                    new Home();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
             keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

}
