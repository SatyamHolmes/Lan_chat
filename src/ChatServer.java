import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class ChatServer
{
	ArrayList<PrintWriter> list;
	Socket sock;
	String message;
	
	public static void main(String [] arg)
	{
		ChatServer abc=new ChatServer();
		abc.go();
		
	}

	public ChatServer()
	{
		list=new ArrayList<PrintWriter>();
	}

	public void go()
	{
		try 
		{
			ServerSocket ssoc=new ServerSocket(2020);
			while(true)
			{
				sock=ssoc.accept();
				Thread th=new Thread(new Runnable()
					{
						public void run()
						{
							try
							{
								BufferedReader reader=new BufferedReader(new InputStreamReader(sock.getInputStream()));
								list.add(new PrintWriter(sock.getOutputStream()));
								while(true)
								{
									message=reader.readLine();
									if(message!=null)
									{
										send(message);
									}
								}
							}
							catch(Exception exe)
							{

							}
						}
					});
				th.start();
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void send(String message)
	{
		try
		{
			int i=0;
			while(i<list.size())
			{
				list.get(i).println(message);
				list.get(i).flush();
				i++;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}