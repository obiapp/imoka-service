package org.imoka.service.Form.util;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is a panel with an icon in the center.
 * When there is not anough space for the panel, no scroll bar will be created.
 * 
 * @author Heidi Rakels.
 */
public class Picture extends JPanel
{
	
	// Constructors.

	/**
	 * Constructs a pael with an icon in the middle.
	 */
	public Picture(Icon picture)
	{

		JLabel label = new JLabel(picture);
	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel innerPanel = new JPanel(new FlowLayout());
		innerPanel.add(label);
		
		this.add(Box.createVerticalGlue());
		this.add(innerPanel);
		this.add(Box.createVerticalGlue());

		label.setBackground(Color.white);
		innerPanel.setBackground(Color.white);
		this.setBackground(Color.white);
		
	}
	
}
