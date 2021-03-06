package com.seu.vCampus.Client.Launcher;

import com.seu.vCampus.Client.Common;
import com.seu.vCampus.Client.Home.Home;
import com.seu.vCampus.util.Login;
import com.seu.vCampus.util.Message;
import com.seu.vCampus.util.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class LauncherPanel extends JPanel {
    private Icon image;
    private JTextField ECardNumber;
    private JPasswordField passWord;
    private JButton Login;
    private JButton config;
    private Common launcherData;
    private JLabel exit;

    public LauncherPanel(){
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(null);
        launcherData = Common.getInstance();


        exit = new JLabel("X");
        exit.setForeground(Color.WHITE);
        exit.setBounds(470,7,20,20);
        add(exit);
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

        Login = new JButton("登录");
        Login.setEnabled(true);
        Login.setBounds(265,270,100,30);
        Login.setBackground(new Color(0,153,68));
        add(Login);

        config = new JButton("配置");
        config.setBounds(130,270,100,30);
        config.setBackground(new Color(137,81,161));
        config.setEnabled(true);
        add(config);

        ECardNumber = new JTextField();
        ECardNumber.setBounds(200,180,150,25);
        ECardNumber.setText("09017211");
        add(ECardNumber);
        JLabel cardNumber = new JLabel("一卡通号：");
        cardNumber.setBounds(130,180,80,25);
        add(cardNumber);

        passWord = new JPasswordField();
        passWord.setBounds(200,230,150,25);
        add(passWord);
        JLabel psw = new JLabel("密码：");
        psw.setBounds(130,230,80,25);
        add(psw);

        JLabel IPLabel = new JLabel("服务端IP："+launcherData.getIpAddress());
        IPLabel.setBounds(160,140,200,30);
        add(IPLabel);

        ImageIcon image = new ImageIcon(Common.picturePath +"/BackGroundImage/Launcher.png");
        setBackground(image);

        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Login.setEnabled(false);
                try{
                    launcherData.startIO();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"连接超时",
                            "错误",JOptionPane.ERROR_MESSAGE);
                    Login.setEnabled(true);
                    return;
                }

                com.seu.vCampus.util.Login LoginMessage = new Login();
                LoginMessage.setPassWord(String.valueOf(passWord.getPassword()));
                LoginMessage.setECardNumber(ECardNumber.getText());

                launcherData.getIO().SendMessages(LoginMessage);
                try {
                    LoginMessage = (Login)launcherData.getIO().ReceiveMessage();
                }catch (Exception e2){
                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(null,"连接超时",
                            "错误",JOptionPane.ERROR_MESSAGE);
                    Login.setEnabled(true);
                    return;
                }


                if(LoginMessage.getType() == Message.MESSAGE_TYPE.TYPE_SUCCESS){
                    launcherData.setLogin(true);
                    Person user = new Person();
                    user.setType(Message.MESSAGE_TYPE.TYPE_QUERY_PERSON);
                    user.setECardNumber(ECardNumber.getText());
                    launcherData.getIO().SendMessages(user);
                    user = (Person)launcherData.getIO().ReceiveMessage();
                    System.out.println(user.getName()+user.getStudentNumber());


                    if(user.getType() == Message.MESSAGE_TYPE.TYPE_SUCCESS){
                        launcherData.setUser(user);
                        try {
                            new Home();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (UnsupportedLookAndFeelException ex) {
                            ex.printStackTrace();
                        } catch (InstantiationException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(user.getType() == Message.MESSAGE_TYPE.TYPE_FAIL){
                        JOptionPane.showMessageDialog(null,"网络连接异常",
                                "错误",JOptionPane.ERROR_MESSAGE);
                        Login.setEnabled(true);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"用户名或密码错误",
                            "错误",JOptionPane.ERROR_MESSAGE);
                    Login.setEnabled(true);
                    setEnabled(true);
                }
            }
        });

        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);

                class ConfigWindow extends JFrame{
                    private ConfigWindow(){
                        setTitle("配置");
                        setBounds(450,450,300,200);

                        JPanel configPanel = new JPanel();
                        setContentPane(configPanel);
                        configPanel.setBorder(new EmptyBorder(5,5,5,5));
                        configPanel.setBounds(0,0,300,200);
                        configPanel.setLayout(null);

                        JTextField ipAdd = new JTextField();
                        ipAdd.setBounds(80,30,150,25);
                        ipAdd.setText(launcherData.getIpAddress());

                        JLabel ipLabel = new JLabel("IP地址：");
                        ipLabel.setBounds(30,30,50,25);
                        configPanel.add(ipLabel);
                        configPanel.add(ipAdd);

                        JLabel portLabel = new JLabel("端口：");
                        portLabel.setBounds(30,75,50,25);
                        JLabel port = new JLabel(Integer.toString(launcherData.getPort()));
                        port.setBounds(80,75,150,25);
                        configPanel.add(port);
                        configPanel.add(portLabel);

                        JButton button = new JButton("确认修改");
                        button.setBounds(80,110,100,25);
                        configPanel.add(button);
                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                launcherData.setIpAddress(ipAdd.getText());
                                setVisible(false);
                                LauncherPanel.this.setEnabled(true);
                                IPLabel.setText("服务端IP："+launcherData.getIpAddress());
                            }
                        });

                        addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                super.windowClosing(e);
                                LauncherPanel.this.setEnabled(true);
                            }
                        });
                        setVisible(true);
                    }
                }

                new ConfigWindow();


            }
        });

    }

    @Override
    protected  void paintComponent(Graphics g){
        if (null != image) {
            processBackground(g);
        }
        System.out.println("f:paintComponent(Graphics g)");
    }

    private void setBackground(Icon icon) {
        this.image = icon;
        this.repaint();
    }

    private void processBackground(Graphics g) {
        ImageIcon icon = (ImageIcon) image;
        Image image = icon.getImage();
        int cw = getWidth();
        int ch = getHeight();
        int iw = image.getWidth(this);
        int ih = image.getHeight(this);
        int x = 0;
        int y = 0;
        while (y <= ch) {
            g.drawImage(image, x, y, this);
            x += iw;
            if (x >= cw) {
                x = 0;
                y += ih;
            }
        }
    }
}
