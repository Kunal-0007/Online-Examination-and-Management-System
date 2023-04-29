/*Oracle db
 * sql
 * db tables
desc userinfo;
Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 UNAME                                              VARCHAR2(30)
 PNAME                                              VARCHAR2(30)
 EMAIL                                     NOT NULL VARCHAR2(30)
 MOBILE                                             VARCHAR2(30)

SQL> desc ques;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 QNO                                                NUMBER(3)
 QTAG                                               VARCHAR2(100)
 OP1                                                VARCHAR2(50)
 OP2                                                VARCHAR2(50)
 OP3                                                VARCHAR2(50)
 OP4                                                VARCHAR2(50)
 ANS                                                VARCHAR2(50)
*/



//program
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import java.sql.*;
//Screen 1
public class Mcqproj extends JFrame implements ActionListener
{
	//JFrame f1=new JFrame("Main Screen");
	ArrayList<String> n=new ArrayList();
	JButton b1,b2;
	Image im;
	Mcqproj() 
	{
		setSize(500,500);
		setLocation(400,100);
		b1=new JButton("Sign-in");
		b2=new JButton("Sign-up");
		b1.setBounds(100,100,100,20);
		b2.setBounds(100,140,100,20);
		b1.addActionListener(this);
		b2.addActionListener(this);
		add(b1);
		add(b2);
		//image part
		n.add("1");n.add("2");n.add("3");n.add("4");n.add("5");
		Collections.shuffle(n);
		im=Toolkit.getDefaultToolkit().getImage("C:\\Users\\kunal\\Desktop\\Images\\"+n.get(0)+".jpg");
		
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1) {
			dispose();
			new Signin();
		}
		else if(e.getSource()==b2) {
			dispose();
			new Signup();
		}
		
	}
	public void paint(Graphics g)
	{
		g.drawImage(im, 250,100,200,200,this);
	}
	public static void main(String[] args) 
	{
		Mcqproj ob=new Mcqproj();
		
	}
}



//Screen 2 -connected with main screen
class Signin extends MouseAdapter
{
	JFrame f=new JFrame("Sign-in");
	JButton b1;
	JLabel l1,l2,l3;
	JTextField t1,t2;
	public static String user,pass;
	Signin()
	{
		f.setSize(500,500);
		f.setLocation(400,100);
		l1=new JLabel("UserName :");
		l2=new JLabel("Password :");
		l3=new JLabel();
		t1=new JTextField();
		t2=new JTextField();
		b1=new JButton("Login");
		l1.setBounds(100,100,130,20);
		l2.setBounds(100,130,130,20);
		l3.setBounds(120, 200, 200, 20);
		t1.setBounds(230,100,100,20);
		t2.setBounds(230,130,100,20);
		b1.setBounds(165,170,70,20);
		b1.addMouseListener(this);
		f.setLayout(null);
		f.add(b1);f.add(l1);f.add(l2);f.add(t1);f.add(t2);f.add(l3);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==b1) {
			try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				PreparedStatement ps=con.prepareStatement("select * from userinfo");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					user=t1.getText();
					pass=t2.getText();
					if(pass.equals(rs.getString(2)) && user.equals(rs.getString(1)))
					{
						f.dispose();
						new Profile();
					}
					else{
						l3.setText("Invalid Username or Password");
						l3.setVisible(true);
						
					}
				}
				con.close();
			}catch(Exception e2) {System.out.println(e2);}
		}
	}
	
}



//screen 3 
class Signup extends MouseAdapter
{
	JFrame f=new JFrame();
	JButton b1;
	JLabel l1,l2,l3,l4;
	JTextField t1,t2,t3,t4;
	Signup()
	{
		f.setSize(500,500);
		f.setLocation(400,100);
		l1=new JLabel("UserName :");
		l2=new JLabel("Password :");
		l3=new JLabel("Email :");
		l4=new JLabel("Mobile :");
		t1=new JTextField();
		t2=new JTextField();
		t3=new JTextField();
		t4=new JTextField();
		b1=new JButton("Register");
		l1.setBounds(100,100,130,20);
		l2.setBounds(100,130,130,20);
		l3.setBounds(100,160,130,20);
		l4.setBounds(100,190,130,20);
		t1.setBounds(230,100,150,20);
		t2.setBounds(230,130,150,20);
		t3.setBounds(230,160,150,20);
		t4.setBounds(230,190,150,20);
		b1.setBounds(165,230,100,20);
		b1.addMouseListener(this);
		f.setLayout(null);
		f.add(b1);f.add(l1);f.add(l2);f.add(l3);f.add(l4);f.add(t1);f.add(t2);f.add(t3);f.add(t4);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void mouseClicked(MouseEvent e){
		if(e.getSource()==b1) {
			 try
             {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
                PreparedStatement ps=con.prepareStatement("insert into userinfo values(?,?,?,?)");
                ps.setString(1,t1.getText());
                ps.setString(2,t2.getText());
                ps.setString(3,t3.getText());
                ps.setString(4,t4.getText());
                int r=ps.executeUpdate();
                con.close();
             }
            catch(Exception ee)
            {System.out.println(ee);}
			 
			 f.dispose();
			new Signin();
		}
	}
	
}



//screen 4
class Profile extends MouseAdapter
{
	JFrame f=new JFrame("Profile");
	JLabel l1,l2,l3,l4,l5;
	JLabel res,res1;
	JButton b1,b2,b3,b4;
	Profile()
	{
		f.setSize(600,500);
		f.setLocation(400,100);
		l1=new JLabel("WELCOME");
		l2=new JLabel();
		l3=new JLabel();
		l4=new JLabel();
		l5=new JLabel();
		l1.setFont(new Font("Courier", Font.BOLD, 30));
		
		b1=new JButton("Profile");
		b2=new JButton("Test");
		b3=new JButton("Result");
		b4=new JButton("Logout");
		
		l1.setBounds(220,50,150,30);
		l2.setBounds(320,100,200,30);
		l3.setBounds(320,130,200,30);
		l4.setBounds(320,160,200,30);
		l5.setBounds(320,190,200,30);
		l2.setFont(new Font("Courier", Font.BOLD, 20));
		l3.setFont(new Font("Courier", Font.BOLD, 20));
		l4.setFont(new Font("Courier", Font.BOLD, 20));
		l5.setFont(new Font("Courier", Font.BOLD, 20));
		f.add(l1);f.add(l2);f.add(l3);f.add(l4);f.add(l5);
		
		res=new JLabel();
		res1=new JLabel();
		res.setBounds(320,150,150,20);
		res1.setBounds(220,250,250,20);
		res.setFont(new Font("Courier", Font.BOLD, 20));
		f.add(res);f.add(res1);
		
		b1.setBounds(100,100,100,20);
		b2.setBounds(100,130,100,20);
		b3.setBounds(100,160,100,20);
		b4.setBounds(100,190,100,20);
		b1.addMouseListener(this);
		b4.addMouseListener(this);
		b2.addMouseListener(this);
		b3.addMouseListener(this);
		f.add(b1);f.add(b2);f.add(b3);f.add(b4);
		f.setLayout(null);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==b4) {
			f.dispose();
			new Mcqproj();
		}
		else if(e.getSource()==b2) {
			f.dispose();
			new Testscr();
		}
		else if(e.getSource()==b1) 
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				PreparedStatement ps=con.prepareStatement("select * from userinfo where uname in ('"+Signin.user+"')");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					
					l2.setText(rs.getString(1));
					l3.setText(rs.getString(2));
					l4.setText(rs.getString(3));
					l5.setText(rs.getString(4));
				}
			}catch(Exception e1) {System.out.println(e1);e1.printStackTrace();}
		}
		else if(e.getSource()==b3)//Result button
		{
			l2.setText("");
			l3.setText("");
			l4.setText("");
			l5.setText("");
			if(Testscr.result==0)
			{
				res1.setText("APPEAR THE TEST FIRST!!");
			}
			else 
			{
				res.setText("Result="+Testscr.result+"/6");
			}
		}
	}
}	




//screen 5
//works to do- next->finish, add instructions 
class Question
{
   String qtag;
   String op1,op2,op3,op4,ans;
   Question(String q,String o1,String o2,String o3,String o4,String an)
   {
        qtag=q;op1=o1;op2=o2;op3=o3;op4=o4;ans=an;
   }
}
class Testscr extends Frame implements ActionListener,ItemListener
{
    Label l,res,head,timer;
    int r=0,i=0;
	Button b1;
	Checkbox  c1,c2,c3,c4;
	CheckboxGroup cg;
	public static int result;
    String ans="";
    String cans="";
    Question que[]=new Question[6];
	Testscr()
	{
		setSize(500,500);
		setLocation(400,100);
		 head=new Label("MCQ");
	       res=new Label("Result="+0);
	       cg=new CheckboxGroup();
	       l=new Label();
	       c1=new Checkbox("",cg,false);
	       c2=new Checkbox("",cg,false);
	       c3=new Checkbox("",cg,false);
	       c4=new Checkbox("",cg,false);
	       b1=new Button("Next");
	       
	       
	       //Timer logic
	       timer=new Label();
	       Timer t=new Timer();
			TimerTask  tt=new TimerTask()
			{
				int i2=50;
				public void run()
				{
						timer.setText("Time left :"+i2);
						i2--;
						
						if (i2 < 0)
						{
		                    	t.cancel();
		                    	//logic to be added to jump to the next question
						}
				}
			};
			t.scheduleAtFixedRate(tt, 0, 1000);
			timer.setBounds(380,60,100,40);
			Font f=new Font("Serif", Font.BOLD, 16);
			timer.setFont(f);
			add(timer);
	       
	       
	       head.setBounds(100,30,200,20);
	       res.setBounds(220,30,120,20);
	       res.setFont(f);
	       l.setBounds(50,50,400,30);
	       c1.setBounds(50,90,180,30);
	       c2.setBounds(240,90,180,30);
	       c3.setBounds(50,130,180,30);
	       c4.setBounds(240,130,180,30);
	       b1.setBounds(350,200,100,20);
		add(b1);add(c1);add(c2);add(c3);add(c4);add(l);add(res);
		c1.addItemListener(this);
        c2.addItemListener(this);
        c3.addItemListener(this);
        c4.addItemListener(this);
        b1.addActionListener(this);
		setLayout(null);
		setVisible(true);
		loadQue();
	}
	void loadQue()
    {
        que=new Question[6];
         try{  int sno=0;
               
                	Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
				PreparedStatement ps=con.prepareStatement("select * from Ques");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					  System.out.println(rs.getString(2));
					  que[sno++]=new Question(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
				}
				con.close();


            } catch(Exception e) {System.out.println(e);}
        
    }
	public void paint(Graphics g)
    {
           cg.setCurrent(null);
           l.setText(que[i].qtag);
           c1.setLabel(que[i].op1);
           c2.setLabel(que[i].op2);
           c3.setLabel(que[i].op3);
           c4.setLabel(que[i].op4);
     }
	public void actionPerformed(ActionEvent e)
	{
          if(e.getSource()==b1)
          {
	             if(ans.equals(que[i].ans)) {
	            	 r++;
	                 res.setText("Result="+r);
	            	 
	             }
	               
	             if(i==que.length-2) //converting next ->finish
	             {
	            	 b1.setLabel("Finish");
	            	 b1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e1){dispose();new Profile();}
					});
	             }
	              
	              if(i==que.length-1)
	              {
	            	  res.setText("Result="+r);
	            	  result=r;
	              }
	               else
	                 i++;
	               repaint();
          }
    }
  public void itemStateChanged(ItemEvent e)
  {
       ans=(String)e.getItem();
  }

}