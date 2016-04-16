package io.miti.logasaurus.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class FilePanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  private static FilePanel filer = null;
  
  private JTextArea viewer = null;
  
  private FilePanel()
  {
    super();
  }
  
  
  private FilePanel(final LayoutManager layout)
  {
    super(layout);
  }
  
  
  public static FilePanel getInstance()
  {
    if (filer == null)
    {
      filer = new FilePanel(new BorderLayout());
      filer.init();
    }
    
    return filer;
  }
  
  
  private void init()
  {
    viewer = new JTextArea();
    viewer.setEditable(false);
    filer.add(new JScrollPane(viewer));
  }
  
  
  public void setText(final String text)
  {
    viewer.setText(text);
    viewer.setCaretPosition(0);
  }
  
  
  public void setViewLocation(final Point offset)
  {
    viewer.setCaretPosition(offset.x);
    try
    {
      Rectangle rect = viewer.modelToView(offset.x);
      viewer.scrollRectToVisible(rect);
    }
    catch (BadLocationException e)
    {
      e.printStackTrace();
    }
    
    highlightText(offset.x, offset.y);
  }
  
  
  private void highlightText(final int start, final int end)
  {
    // Get the highlighter for the text area
    Highlighter hl = viewer.getHighlighter();
    
    // Remove any existing highlights
    hl.removeAllHighlights();
    
    // Create a painter for the highlighted text
    DefaultHighlighter.DefaultHighlightPainter painter = 
        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    
    // Highlight the text from start to end
    try
    {
      hl.addHighlight(start, end, painter);
    }
    catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }
}
