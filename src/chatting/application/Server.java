package chatting.application;
import javax.swing.*;//for showing frame,diagram,image
import javax.swing.border.EmptyBorder;
import java.awt.*;//for color
import java.awt.event.*;//use for action or event
import java.io.*;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.util.Calendar;
import java.text.*;
import java.net.*;


public class Server implements ActionListener {
    JTextField text;//globally decleared for acsess in constructer and out off constructor field
    JPanel p2;
    static Box vertical = Box.createVerticalBox();
     static JFrame f= new JFrame();
     static DataOutputStream dout;
    Server(){
        f.setLayout(null);//set component on page

        JPanel p1=new JPanel();//JPanel is use for dividig to the page
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,400,60);//given the location to the privious background
        p1.setLayout(null);
       f.add(p1);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));//for image
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);// for minimise the image
        ImageIcon i3=new ImageIcon(i2);//we can't pass i2 in level then we creat i3 then pass in level method
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));//for image
        Image i5=i4.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);// for minimise the image
        ImageIcon i6=new ImageIcon(i5);//we can't pass i2 in level then we creat i3 then pass in level method
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,40,40);
        p1.add( profile);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));//for image
        Image i8=i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);// for minimise the image
        ImageIcon i9=new ImageIcon(i8);//we can't pass i2 in level then we creat i3 then pass in level method
        JLabel video=new JLabel(i9);// JLable use for write on the frame
        video.setBounds(250,17,25,25);
        p1.add( video);

        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));//for image
        Image i11=i10.getImage().getScaledInstance(30,25,Image.SCALE_DEFAULT);// for minimise the image
        ImageIcon i12=new ImageIcon(i11);//we can't pass i2 in level then we creat i3 then pass in level method
        JLabel phone=new JLabel(i12);
        phone.setBounds(300,17,30,25);
        p1.add( phone);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));//for image
        Image i14=i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);// for minimise the image
        ImageIcon i15=new ImageIcon(i14);//we can't pass i2 in level then we creat i3 then pass in level method
        JLabel e1=new JLabel(i15);
        e1.setBounds(350,17,10,25);
        p1.add( e1);

        JLabel name = new JLabel(("Maurya ji"));
        name.setBounds(110,10,120,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status = new JLabel(("Active now"));
        status.setBounds(110,30,120,25);
        status.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,13));
        p1.add(status);

        p2=new JPanel();
        p2.setBounds(5,65,390,500);
        f.add(p2);

        text= new JTextField();// for typing
        text.setBounds(5,565,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        JButton send= new JButton("Send");
        send.setBounds(315,565,83,39);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);


        f.setSize(400,605);
        f.setLocation(200,40);//given the location of the page where it is open
        f.setUndecorated(true);//remove to the heder baar
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);//by default frame is hide then we can use the setVisible function for show the frame
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {


            String out = text.getText();

            JPanel p3 = formatlabel(out);

            p2.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));
            p2.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);
            text.setText("");//for blanc textarea after send msg

            f.validate();//for realod the page
        }catch (Exception e1){
            e1.printStackTrace();
        }

    }
    public static JPanel formatlabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width:120px\">"+out+"</p></html>");
        output.setFont(new Font("tahoma",Font.PLAIN,18));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);//use for show text background color
        output.setBorder(new EmptyBorder(15,15,15,50));//txt padding
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time= new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String[] args) {

        new Server();//anonymous object
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){//use for infinete time accept msg
                Socket s=skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());//use for accept data
                dout= new DataOutputStream(s.getOutputStream());
                while (true){
                    String msg = din.readUTF();
                    JPanel panel= formatlabel(msg);
                    JPanel left= new JPanel((new BorderLayout()));
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
