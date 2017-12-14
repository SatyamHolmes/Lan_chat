import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class ClientGui
{
	private JFrame frame;
	private ClientPanel mpanel; //the main content panel
	private JPanel hpanel;  //where all the messages are displayed
	private JPanel msgpanel; //where message is tyed
	private JScrollPane scroller;
	//private JTextArea text;
	private JTextArea message;
	private JButton button;
	private Socket s;
	private BufferedReader reader;
	private PrintWriter writer;

	public static void main(String args[])
	{
		ClientGui cl=new ClientGui();
		Thread th=new Thread(new Runnable(){
			public void run()
			{
				while(true)
				{
					cl.chat();
				}
			}
		});
		th.start();
	}

	public ClientGui()
	{
		frame=new JFrame();
		frame.setSize(1300,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mpanel=new ClientPanel();
		mpanel.setSize(1300,700);
		mpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		//mpanel.setOpaque(false);
		frame.setContentPane(mpanel);
		
		hpanel=new JPanel();
		hpanel.setLayout(new BoxLayout(hpanel,BoxLayout.Y_AXIS));
		hpanel.setOpaque(false);

		scroller=new JScrollPane(hpanel);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setPreferredSize(new Dimension(1200,600));
		scroller.getViewport().setOpaque(false);
		scroller.setOpaque(false);
		scroller.getVerticalScrollBar().setUI(new MyScrollBar());
		scroller.getVerticalScrollBar().setOpaque(false);
		scroller.setBorder(BorderFactory.createEmptyBorder());
		scroller.setViewportBorder(BorderFactory.createEmptyBorder());

		mpanel.add(scroller);

		scroller.getVerticalScrollBar().addAdjustmentListener((evb) -> {
			hpanel.repaint();
		});
		scroller.getVerticalScrollBar().setUnitIncrement(10);

		msgpanel=new JPanel();
		msgpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		msgpanel.setPreferredSize(new Dimension(1300,50));
		mpanel.add(msgpanel);
		msgpanel.setOpaque(false);
		
		message=new JTextArea();
		msgpanel.add(message);
		message.setPreferredSize(new Dimension(1100,50));
		message.setLineWrap(true);
		

		message.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent ev)
				{
					if(ev.getKeyCode()==KeyEvent.VK_ENTER)
					{
						try
						{
							if(message.getText()!=null)
							{
								writer.println(message.getText());
								writer.flush();
								message.setText("");
								System.out.println("sent");
							}
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}		
					}
				}

				public void keyReleased(KeyEvent ev)
				{
					if(ev.getKeyCode()==KeyEvent.VK_ENTER)
					{
						message.setText("");
					}
				}

			});
		

		button=new JButton("Send");
		msgpanel.add(button);
		button.setPreferredSize(new Dimension(100,50));
		button.addActionListener((e)->
				{
					try
					{
						if(message.getText()!=null)
						{
							writer.println(message.getText());
							writer.flush();
							message.setText("");
							System.out.println("sent");
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				});
		frame.setVisible(true);
		connect();
	}

	public void connect()
	{
	  try
		{
			s=new Socket("127.0.0.1",2020);
			writer=new PrintWriter(s.getOutputStream());
			//writer= new DataOutputStream(s.getOutputStream());
			reader=new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void chat()
	{
		try
		{
			String ad=reader.readLine();
			JPanel rpanel=new JPanel(){
				public void paintComponent(Graphics g)
				{
					g.setColor(Color.WHITE);
					g.setFont(new Font("Times new Roman",Font.PLAIN,14));
					g.drawString(ad,0,10);
				}
			};
			rpanel.setPreferredSize(new Dimension(1300,20));
			rpanel.setMaximumSize(new Dimension(1300,20));
			rpanel.setMinimumSize(new Dimension(1300,20));	
			hpanel.add(rpanel);
			hpanel.revalidate();
			hpanel.repaint();
			javax.swing.Timer t=new javax.swing.Timer(0,(eb)->
					scroller.getVerticalScrollBar().setValue(scroller.getVerticalScrollBar().getMaximum());
				);
			t.setRepeats(false);
			t.setInitialDelay(20);
			t.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	class ClientPanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			Image image=new ImageIcon("/home/satyamholmes/Desktop/java/Lan_Chat/blacktexture.jpg").getImage();
			g.drawImage(image,0,0,1300,700,this);
		}
	}

}

