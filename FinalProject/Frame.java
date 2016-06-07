import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

	public class Frame extends JFrame
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String username=new String();
		private JLabel Jlb_ID = new JLabel("�b��");
	    private JLabel Jlb_PW = new JLabel("�K�X");
	    private JTextField jid = new JTextField();
	    private JPasswordField jpw = new JPasswordField();
	    private JButton Jbtn_YES = new JButton("�T�w");
	    private JButton Jbtn_NO = new JButton("�M��");
	    private JButton Jbtn_ADD = new JButton("���U");
	    private ButtonHandler hbtHandler = new ButtonHandler();  //�B�z���s�ƥ� 
        boolean flag=false;


	    public Frame()
	    {
	        super("��J�b���K�X"); 
	        Container c = getContentPane(); 
	        c.setLayout(null);
	        
	        //�]�wJlb_ID�j�p��m����ܦr��
	        Jlb_ID.setLocation(20,10);
	        Jlb_ID.setSize(50,20);
	        Jlb_ID.setFont(new Font("Serif",Font.BOLD,16));
	        c.add(Jlb_ID);
	        
	        //�]�w�b����J�ؤj�p��m����ܦr��
	        jid.setLocation(70,10);
	        jid.setSize(180,20);
	        jpw.setToolTipText("��J�b��");
	        c.add(jid);
	        
	        //�]�wJlb_PW�j�p��m����ܦr��
	        Jlb_PW.setLocation(20,50);
	        Jlb_PW.setSize(50,20);
	        Jlb_PW.setFont(new Font("Serif",Font.BOLD,16));
	        c.add(Jlb_PW);
	        
	        //�]�w�K�X��J�ؤj�p��m����ܦr��
	        jpw.setLocation(70,50);
	        jpw.setSize(180,20);
	        //jpw.setEchoChar('��');
	        jpw.setToolTipText("�K�X����10�Ӧr��");
	        c.add(jpw);
	        
	        jpw.addActionListener(hbtHandler);
	                
	        //�]�w�T�w���s�j�p��m����ܦr��
	        Jbtn_YES.setLocation(10,90);
	        Jbtn_YES.setSize(80,20);
	        Jbtn_YES.addActionListener(hbtHandler);
	        c.add(Jbtn_YES);
	        
	        //�]�w�M�����s�j�p��m����ܦr��
	        Jbtn_NO.setLocation(100,90);
	        Jbtn_NO.setSize(80,20);
	        Jbtn_NO.addActionListener(hbtHandler);
	        c.add(Jbtn_NO);
	        
	        //�]�w���U���s�j�p��m����ܦr��
	        Jbtn_ADD.setLocation(190,90);
	        Jbtn_ADD.setSize(80,20);
	        Jbtn_ADD.addActionListener(hbtHandler);
	        c.add(Jbtn_ADD);
	        
	        //�]�w����
	        setSize(290,150);
	        setLocation(300,200); 
	         setResizable(false);//������j���s�L�� 
	         setVisible(true);
	         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    } 
	    
	    private class ButtonHandler implements ActionListener 
	    {
			public void actionPerformed(ActionEvent evtE) 
	        {
	            if(evtE.getSource() == Jbtn_YES)
	            {
	                Scanner s1 = null;
	                try {
						s1=new Scanner(new FileInputStream("log.txt"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                String name,pword;
	                while(s1.hasNext()) {
	                    name=s1.next();
	                    pword=s1.next();
	                    System.out.println("file: "+pword);
	                    System.out.println(jpw.getPassword());
	                    if(jid.getText().equals(name) && pword.equals(String.valueOf(jpw.getPassword()))) {
	                    	javax.swing.JOptionPane.showMessageDialog(null, "You are logged in!");
	                    	username=name;
	                    	flag=true;
	                        break;
	                    }                
	                }
	                if(!flag){
	                	//account not exit
	                	javax.swing.JOptionPane.showMessageDialog(null, "login failed!\nPlease create a new account.");                	
	                }
	                //System.out.println("Y");
	                jid.setText("");
	                jpw.setText("");
	                
	            }
	            else if(evtE.getSource() == Jbtn_NO)
	            {
	            	jid.setText("");
	                jpw.setText("");
	            }
	            else if(evtE.getSource() == Jbtn_ADD)
	            {
	            	String user = jid.getText();
	                char[] pass = jpw.getPassword();
	                //System.out.println(pass);
	                
	                String test = new String();
	                test = jpw.getPassword().toString();
	                int test2 = test.hashCode();
	                
	                //System.out.println(test2);
	                //System.out.println(test);
	                
	                String result = new String(); 
	                result=user+"\n"+String.valueOf(pass)+"\n" ;
	                //System.out.println("**"+result);
	                //consider same account
	                Scanner s1 = null;
	                try {
						s1=new Scanner(new FileInputStream("log.txt"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                boolean iflag=false;
	                String name,pword;
	                while(s1.hasNext()) {
	                    name=s1.next();
	                    pword=s1.next();
	                    if(jid.getText().equals(name)) {
	                    	//exist account
	                    	javax.swing.JOptionPane.showMessageDialog(null, "Already have same account!\nTry new username!");
	                        iflag=true;
	                        break;
	                    }                
	                }
	                if(!iflag){
	                	//no same account
	                	try {
	                	    Files.write(Paths.get("log.txt"),result.getBytes(), StandardOpenOption.APPEND);
	                	    javax.swing.JOptionPane.showMessageDialog(null, "registered successfully!");
	                	}catch (IOException e) {
	                	    //exception handling left as an exercise for the reader
	                	}
	                }
	                
	                jid.setText("");
	                jpw.setText("");
	            }
	        }
	    }
	}
