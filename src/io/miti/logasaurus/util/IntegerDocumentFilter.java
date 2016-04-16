package io.miti.logasaurus.util;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerDocumentFilter extends DocumentFilter
{
  public IntegerDocumentFilter()
  {
    super();
  }
  
  
  @Override
  public void insertString(final FilterBypass fp,
                           final int offset,
                           final String string,
                           final AttributeSet aset)
    throws BadLocationException
  {
    int len = string.length();
    boolean isValidInteger = true;

    for (int i = 0; i < len; i++)
    {
      if (!Character.isDigit(string.charAt(i)))
      {
        isValidInteger = false;
        break;
      }
    }
    
    if (isValidInteger)
    {
      super.insertString(fp, offset, string, aset);
    }
    else
    {
      Toolkit.getDefaultToolkit().beep();
    }
  }
  
  
  @Override
  public void replace(final FilterBypass fp,
                      final int offset,
                      final int length,
                      final String text,
                      final AttributeSet aset)
    throws BadLocationException
  {
    int len = text.length();
    boolean isValidInteger = true;
    
    for (int i = 0; i < len; i++)
    {
      if (!Character.isDigit(text.charAt(i)))
      {
        isValidInteger = false;
        break;
      }
    }
    
    if (isValidInteger)
    {
      super.replace(fp, offset, length, text, aset);
    }
    else
    {
      Toolkit.getDefaultToolkit().beep();
    }
  }
}
