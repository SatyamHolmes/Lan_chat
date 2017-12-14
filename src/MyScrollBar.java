import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.plaf.basic.*;
import java.awt.geom.*;

class MyScrollBar extends BasicScrollBarUI
{
	Image thumb=new ImageIcon("/home/satyamholmes/Desktop/java/Lan_Chat/thumb.png").getImage();
	
	@Override
	protected void paintThumb(Graphics g,JComponent c, Rectangle thumbbounds)
	{
		g.translate(thumbbounds.x,thumbbounds.y);
		g.setColor(new Color(160,160,160));
		//((Graphics2D)g).fillRoundRect(0,0,thumbbounds.width-6,thumbbounds.height-6,10,10);
		g.drawImage(thumb,7,7,thumbbounds.width-7,thumbbounds.height-7,null);
		g.translate(-thumbbounds.x,-thumbbounds.y);
	}

	@Override
	protected void paintTrack(Graphics g,JComponent c,Rectangle trackbounds)
	{
		/*
		g.translate(trackbounds.x,trackbounds.y);
		g.setColor(new Color(100,100,100));
		((Graphics2D)g).fillRoundRect(0,0,trackbounds.width-6,trackbounds.height-6,10,10);
		g.translate(-trackbounds.x,-trackbounds.y);
	*/}

	protected JButton createButton()
	{
		JButton button=new JButton();
		button.setPreferredSize(new Dimension(0,0));
		button.setMinimumSize(new Dimension(0,0));
		button.setMaximumSize(new Dimension(0,0));
		return button;
	}

	@Override
	protected JButton createDecreaseButton(int o)
	{
		return createButton();
	}

	@Override
	protected JButton createIncreaseButton(int o)
	{
		return createButton();
	}
}
