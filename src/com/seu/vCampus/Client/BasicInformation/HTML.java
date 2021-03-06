package com.seu.vCampus.Client.BasicInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HTML extends JLabel {

    private String text;
    private URL url;
    private Color color = Color.BLUE;

    public HTML(String t, String URL, Date date){
        super("<html>" + t + "</html>");
        text = t;

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = format1.format(date);
        setText(text+" /"+tempDate);
        setForeground(color);
        try {
            url = new URL(URL);
        }catch (MalformedURLException me){
            me.printStackTrace();
        }
        this.setToolTipText(url.toString());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.gray);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(color);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    Desktop.getDesktop().browse(url.toURI());
                }catch (URISyntaxException use){
                    use.printStackTrace();
                }catch (IOException ie){
                    ie.printStackTrace();
                }
                color = Color.magenta;
                setForeground(color);
            }
        });


    }
}
