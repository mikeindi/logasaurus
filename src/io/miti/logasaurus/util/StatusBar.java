package io.miti.logasaurus.util;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

/**
 * Status bar for the main frame.
 * 
 * @author mwallace
 * @version 1.0
 */
public final class StatusBar extends JPanel
{
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * The left-side label.
   */
  private JLabel label1 = null;
  
  /**
   * The right-side label.
   */
  private JLabel label2 = null;
  
  
  /**
   * Default constructor.
   */
  public StatusBar()
  {
    // Create borders
    final Border cellBorder = new SoftBevelBorder(SoftBevelBorder.LOWERED);
    final Border panelBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    
    // Set the GUI elements of the panel
    // setLayout(new BorderLayout(20, 0));
    setLayout(new GridLayout(1, 2));
    setForeground(Color.black);
    setBorder(panelBorder);
//    setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2),
//                                 new SoftBevelBorder(SoftBevelBorder.LOWERED)));
    
    // Create and add the two label fields
    label1 = new JLabel(" Ready", SwingConstants.LEFT);
    label1.setBorder(cellBorder);
    label2 = new JLabel("", SwingConstants.LEFT);
    label2.setBorder(cellBorder);
    add(label1);
    add(label2);
  }
  
  
  /**
   * Set the text in the left-side label.
   * 
   * @param text the text to display
   */
  public void setText(final String text)
  {
    label1.setText(text);
  }
  
  
  /**
   * Set the text in the right-side label.
   * 
   * @param text the text to display
   */
  public void setText2(final String text)
  {
    label2.setText(text);
  }
}
