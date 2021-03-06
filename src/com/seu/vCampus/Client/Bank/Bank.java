package com.seu.vCampus.Client.Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.seu.vCampus.Client.Common;
import com.seu.vCampus.util.BankBill;
import com.seu.vCampus.util.Message;


public class Bank {
    private JPanel bankPanel;
    private JTextField money;
    private JPasswordField password;
    private JLabel BankBalance;
    private JLabel UserNumber;
    private JLabel ECardBalance;
    private JLabel ECardNumber;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel RechargeMoney;
    private JLabel Password;
    private JButton charge;
    private JTable bankBill;
    private JLabel Advertisement;
    private JScrollPane Bill;
    private JPanel panel2;
    private Common bankData;
    private static String[] header = {"编号", "状态", "金额", "日期"};
    private static DefaultTableModel bankBillModel = new DefaultTableModel(null, header) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };


    public Bank() {
        $$$setupUI$$$();
        bankData = Common.getInstance();
        charge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(password.getPassword());
                System.out.println(bankData.getUserCount().getBankPassword());
                if (bankData.getUserCount().getBankPassword().equals(String.valueOf(password.getPassword()))) {
                    if (bankData.getUserCount().getBankBalance() >= Double.parseDouble(money.getText())) {
                        double temp = bankData.getUserCount().getBankBalance() - Double.parseDouble(money.getText());
                        double Etemp = bankData.getUser().getECardBalance() + Double.parseDouble(money.getText());
                        bankData.getUserCount().setBankBalance(temp);
                        bankData.getUser().setECardBalance(Etemp);
                        BankBill thisBill = new BankBill();
                        thisBill.setBillAmount(Double.parseDouble(money.getText()));
                        thisBill.setECardNumber(bankData.getUser().getECardNumber());
                        thisBill.setBillDate(new Date());
                        thisBill.setBillType(BankBill.BILL_TYPE.TYPE_EXPENDITURE);

                        thisBill.setType(Message.MESSAGE_TYPE.TYPE_RECHARGE_ECARD);
                        bankData.getUserCount().addBill(thisBill);
                        bankData.getIO().SendMessages(thisBill);
                        bankData.getIO().ReceiveMessage();

                        bankData.getUserCount().setType(Message.MESSAGE_TYPE.TYPE_UPDATE_COUNT);
                        bankData.getIO().SendMessages(bankData.getUserCount());
                        bankData.getIO().ReceiveMessage();

                        bankData.getUser().setType(Message.MESSAGE_TYPE.TYPE_UPDATE_USER);
                        bankData.getIO().SendMessages(bankData.getUser());
                        bankData.getIO().ReceiveMessage();

                        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                        String tempDate = format1.format(thisBill.getBillDate());

                        Object[] TempData = {bankData.getUserCount().getCountBill().size(), thisBill.getBillTypeString(),
                                thisBill.getBillAmount(), tempDate};
                        bankBillModel.addRow(TempData);
                        JOptionPane.showMessageDialog(null, "钱，可以让我变得更强!", "成功", JOptionPane.INFORMATION_MESSAGE);
                        initialization();
                    } else {
                        JOptionPane.showMessageDialog(null, "银行卡余额不足！！！！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "密码错误！！！", "错误", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        initialization();

    }

    public void initialization() {

        bankBill.setRowHeight(30);
        ECardNumber.setText(bankData.getUser().getECardNumber());
        BankBalance.setText(Double.toString(bankData.getUserCount().getBankBalance()));
        UserNumber.setText(bankData.getUserCount().getCounterNumber());
        ECardBalance.setText(Double.toString(bankData.getUser().getECardBalance()));
        Advertisement.setText("");
        Advertisement.setIcon(new ImageIcon(Common.picturePath + "/icon/Advertisement3.jpg"));

        switch (bankData.getSkinNumber()) {
            case 1:
                panel2.setBackground(new Color(63, 87, 123));
                break;
            case 2:
                panel2.setBackground(Color.BLACK);
                break;
            case 3:
                panel2.setBackground(new Color(85, 20, 0));
                break;
            case 4:
                panel2.setBackground(new Color(0, 70, 40));
                break;
        }

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        bankData = Common.getInstance();
        Bill = new JScrollPane();
        int counter = 0;
        while (counter < bankData.getUserCount().getCountBill().size()) {
            Object[] TempData = {counter + 1, bankData.getUserCount().getCountBill().get(counter).getBillTypeString(),
                    bankData.getUserCount().getCountBill().get(counter).getBillAmount(), bankData.getUserCount().getCountBill().get(counter).getBillDate()};
            bankBillModel.addRow(TempData);
            counter++;
        }

        bankBill = new JTable(bankBillModel);
        bankPanel = new JPanel();
    }


    public JPanel getPanel() {
        return bankPanel;
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        bankPanel = new JPanel();
        bankPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-8355712));
        bankPanel.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        label1 = new JLabel();
        label1.setForeground(new Color(-1));
        label1.setText("一卡通账号：");
        panel2.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2 = new JLabel();
        label2.setForeground(new Color(-1));
        label2.setText("一卡通余额：");
        panel2.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setForeground(new Color(-1));
        label3.setText("银行卡账号：");
        panel2.add(label3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label4 = new JLabel();
        label4.setForeground(new Color(-1));
        label4.setText("银行卡余额：");
        panel2.add(label4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RechargeMoney = new JLabel();
        RechargeMoney.setForeground(new Color(-1));
        RechargeMoney.setText("充值金额：");
        panel2.add(RechargeMoney, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Password = new JLabel();
        Password.setForeground(new Color(-1));
        Password.setText("充值密码：");
        panel2.add(Password, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ECardNumber = new JLabel();
        ECardNumber.setForeground(new Color(-1));
        ECardNumber.setText("Label");
        panel2.add(ECardNumber, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ECardBalance = new JLabel();
        ECardBalance.setForeground(new Color(-1));
        ECardBalance.setText("Label");
        panel2.add(ECardBalance, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        UserNumber = new JLabel();
        UserNumber.setForeground(new Color(-1));
        UserNumber.setText("Label");
        panel2.add(UserNumber, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BankBalance = new JLabel();
        BankBalance.setForeground(new Color(-1));
        BankBalance.setText("Label");
        panel2.add(BankBalance, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        money = new JTextField();
        panel2.add(money, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        password = new JPasswordField();
        panel2.add(password, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        charge = new JButton();
        charge.setBackground(new Color(-14672351));
        charge.setForeground(new Color(-16777216));
        charge.setText("充值");
        panel2.add(charge, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(4, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel2.add(spacer3, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel2.add(spacer4, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel2.add(spacer5, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel2.add(spacer6, new GridConstraints(0, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Advertisement = new JLabel();
        Advertisement.setText("Label");
        panel2.add(Advertisement, new GridConstraints(7, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel2.add(spacer7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel2.add(spacer8, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel2.add(spacer9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panel2.add(spacer10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        panel2.add(spacer11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        panel2.add(spacer12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        panel2.add(spacer13, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        bankPanel.add(Bill, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Bill.setViewportView(bankBill);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return bankPanel;
    }
}
