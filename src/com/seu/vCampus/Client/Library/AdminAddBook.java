package com.seu.vCampus.Client.Library;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.seu.vCampus.Client.Common;
import com.seu.vCampus.util.Book;
import com.seu.vCampus.util.Message;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminAddBook {
    private JTextField BNameText;
    private JTextField ISBNText;
    private JTextField AuthorText;
    private JButton ApplyButton;
    private JLabel ISBNLable;
    private JLabel NameLable;
    private JLabel AuthorLable;
    private JPanel AddMPanel;
    private JTextField TypeText;
    private JLabel TypeLable;

    private Common AABookData;


    public AdminAddBook() {
        ApplyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                AABookData = Common.getInstance();
                int Blistsize = AABookData.getBookInformation().getBookList().size();
                int cnt = 0;
                String addIsbn = ISBNText.getText();
                boolean find = false;
                while (cnt < Blistsize) {

                    Book NBook = AABookData.getBookInformation().getBookList().get(cnt);
                    if (addIsbn.equals(NBook.getECardNumber())) {
                        find = true;
                        break;
                    }
                    cnt++;
                }
                if (!find) {
                    Object[] data = {
                            BNameText.getText(), AuthorText.getText(), TypeText.getText(), addIsbn
                    };
                    AdminLib.AModel.addRow(data);
                    Book nBook = new Book();
                    nBook.setName(BNameText.getText());
                    nBook.setAuthor(AuthorText.getText());
                    nBook.setBID(addIsbn);
                    AABookData.getBookInformation().AddBook(nBook);
                    nBook.setType(Message.MESSAGE_TYPE.TYPE_ADD_BOOK);
                    AABookData.getIO().SendMessages(nBook);
                    nBook = (Book) AABookData.getIO().ReceiveMessage();
                    if (nBook.getType() == Message.MESSAGE_TYPE.TYPE_SUCCESS) {
                        JOptionPane.showMessageDialog(null, "添加图书数据库操作成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "添加图书数据库操作失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    protected static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("AdminAddBook");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Create and set up the content pane.
        frame.setContentPane(new AdminAddBook().AddMPanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        AddMPanel = new JPanel();
        AddMPanel.setLayout(new GridLayoutManager(6, 4, new Insets(20, 10, 20, 10), -1, -1));
        AddMPanel.setMinimumSize(new Dimension(200, 200));
        AddMPanel.setPreferredSize(new Dimension(400, 250));
        BNameText = new JTextField();
        AddMPanel.add(BNameText, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), new Dimension(300, -1), 0, false));
        ISBNText = new JTextField();
        ISBNText.setText("");
        AddMPanel.add(ISBNText, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), new Dimension(300, -1), 0, false));
        AuthorText = new JTextField();
        AddMPanel.add(AuthorText, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), new Dimension(300, -1), 0, false));
        ApplyButton = new JButton();
        ApplyButton.setText("确定");
        AddMPanel.add(ApplyButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(100, 30), new Dimension(100, 30), 0, false));
        final Spacer spacer1 = new Spacer();
        AddMPanel.add(spacer1, new GridConstraints(4, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        NameLable = new JLabel();
        NameLable.setText("书名");
        AddMPanel.add(NameLable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AuthorLable = new JLabel();
        AuthorLable.setText("作者");
        AddMPanel.add(AuthorLable, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ISBNLable = new JLabel();
        ISBNLable.setText("ISBN");
        AddMPanel.add(ISBNLable, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TypeText = new JTextField();
        AddMPanel.add(TypeText, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), new Dimension(300, -1), 0, false));
        TypeLable = new JLabel();
        TypeLable.setText("类型");
        AddMPanel.add(TypeLable, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NameLable.setLabelFor(BNameText);
        AuthorLable.setLabelFor(AuthorText);
        TypeLable.setLabelFor(TypeText);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return AddMPanel;
    }
}
