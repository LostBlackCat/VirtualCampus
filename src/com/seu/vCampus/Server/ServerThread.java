package com.seu.vCampus.Server;

/**
 * @ServerThread.java
 * @服务端线程
 * @完成日期：2019_8_24
 * @作者：wxy
 */



import com.seu.vCampus.util.*;
import com.seu.vCampus.Database.DatabaseConnection;
import com.seu.vCampus.Database.DatabaseActions;

import java.util.ArrayList;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.net.Socket;
import java.io.*;
import java.sql.SQLException;


public class ServerThread  extends Thread{
    private Socket socket;

    /**
     * 输入的流
     **/
    private InputStream is;
    private BufferedInputStream bis ;
    private ObjectInputStream ois;

    /**
     * 输出的流
     **/
    private ObjectOutputStream oos;
    private OutputStream os;

    /**
     * 输入的message对象与输出的message对象
     **/
    private Message msg;
    private Connection conn = DatabaseConnection.getConn();
    private DatabaseActions act = new DatabaseActions();

    public ServerThread(Socket s){
        this.socket = s;
        is = null;
        bis = null;
        ois = null;

        os = null;
        oos = null;

        msg = new Message();
    }


    @Override
    public void run(){
        try {

            is = socket.getInputStream();          //获得socket的输入流
            os = socket.getOutputStream();         //获得socket的输出流

            while (true){
                bis = new BufferedInputStream(is);     //构建缓冲输入流
                ois = new ObjectInputStream(bis);      //反序列化获得对象
                oos = new ObjectOutputStream(os);      //
                msg = (Message) ois.readObject();   //获得Message对象
                System.out.println(msg.getECardNumber());
                switch (msg.getType()){
                    case TYPE_LOGIN:
                        act.validatePassword(conn, (Login) msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_QUERY_PERSON:
                        System.out.println("是获取基本信息mes，一卡通号是："+msg.getECardNumber());
                        act.PersonMessageSend(conn,(Person)msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_DELETE_COURSE:
                        System.out.println("是删除课程mes，一卡通号是："+msg.getECardNumber());
                        act.deselectCourse(conn, (Course) msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_SELECT_COURSE:
                        act.selectCourse(conn, (Course) msg);
                        System.out.println(msg.getType());
                        break;
                    case TYPE_GET_COURSES_AVAILABLE: //Message must be a person object with the last
                        // element of courses list containing semester info.
                    {
                        int l = ((Person) msg).getCourses().size();
                        String semester = ((Person) msg).getCourses().get(l).getCourseSemester();
                        act.getCoursesAvailable(conn, (Person) msg, semester);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    }
                    case TYPE_GET_COURSES_SELECTED: {
                        int l = ((Person) msg).getCourses().size();
                        if (l != 0) {
                            String semester = ((Person) msg).getCourses().get(l).getCourseSemester();
                            act.getCoursesSelected(conn, (Person) msg, semester);
                        } else {
                            act.getCoursesSelected(conn, (Person) msg);
                        }
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    }
                    case TYPE_GET_GRADES:
                        act.getGrades(conn, (Person) msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_QUERY_GOODS:
                        act.getShopMessage(conn,(ShopManage) msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_ADD_GOODS:
                        act.insertGoods(conn,(Goods)msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                    case TYPE_DELETE_GOODS:
                        act.deleteGoods(conn,(Goods)msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_QUERY_PERSON_MANAGE:
                        act.getPersonManage(conn,(PersonManage)msg);
                        System.out.println(msg.getType());
                        oos.writeObject(msg);
                        break;
                    case TYPE_RECHARGE_ECARD:

                        break;
                    case TYPE_QUERY_BANK_COUNT:

                        break;



                }
            }

        }catch (IOException | ClassNotFoundException ioe){
            ioe.printStackTrace();
        }

    }
}
