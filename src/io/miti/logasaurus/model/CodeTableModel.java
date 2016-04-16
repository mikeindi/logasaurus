package io.miti.logasaurus.model;

import io.miti.logasaurus.app.LogViewer;
import io.miti.logasaurus.util.LogCache;
import io.miti.logasaurus.util.Utility;

import java.awt.Point;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class CodeTableModel extends AbstractTableModel
{
  /** Default ID. */
  private static final long serialVersionUID = 1L;
  
  /** The current subset key. */
  private CodeSubset subset = null;
  
  /**
   * Default constructor.
   */
  public CodeTableModel()
  {
    // Don't create the subset yet, wait until we load a data file
    // subset = new CodeSubset("");
  }
  
  
  public void createSubset()
  {
    subset = new CodeSubset("");
  }
  
  
  /**
   * Update the key used to determine the subset of albums to display.
   * 
   * @param code the code
   * @return whether the table needs to be redrawn
   */
  public boolean setSubsetKey(final String code, final boolean forceUpdate)
  {
    if (subset == null)
    {
      return false;
    }
    
    // Optimize this to check for the same codes
    boolean redrawNeeded = false;
    if (!subset.isBasedOn(code) || forceUpdate)
    {
      // Update the subset using the new key
      subset.clear();
      subset = new CodeSubset(code);
      
      // Show the number of rows in the status bar
      showRowCount();
      
      // The subset changed, so we need to redraw the list
      redrawNeeded = true;
    }
    
    // Return whether the table needs to be redrawn
    return redrawNeeded;
  }
  
  
  /**
   * Show the number of rows in the status bar.
   */
  public void showRowCount()
  {
    // Update the status bar to show the number of matches
    final int totalCount = Codes.getSize();
    final int subCount = subset.getCount();
    
    StringBuilder sb = new StringBuilder(30);
    sb.append(" Showing ").append(Integer.toString(subCount)).append(" row");
    if (subset.getCount() != 1)
    {
      sb.append("s");
    }
    
    if (subCount < totalCount)
    {
      sb.append(" of ").append(Integer.toString(totalCount));
    }
    
    LogViewer.setStatusBarMessage2(sb.toString());
  }
  
  
  @Override
  public Class<?> getColumnClass(final int columnIndex)
  {
    switch (columnIndex)
    {
      case 0: return String.class;
      case 1: return String.class;
      case 2: return String.class;
      default: return String.class;
    }
  }
  
  
  @Override
  public int getColumnCount()
  {
    return 4;
  }
  
  
  @Override
  public String getColumnName(int column)
  {
    switch (column)
    {
      case 0: return "Time";
      case 1: return "Level";
      case 2: return "Thread";
      case 3: return "Message";
      default: return "abcd";
    }
  }
  
  
  @Override
  public int getRowCount()
  {
    if (subset == null)
    {
      return 0;
    }
    
    return subset.getCount();
  }
  
  
  @Override
  public Object getValueAt(int row, int column)
  {
    Code code = subset.getCode(row);
    if (code == null)
    {
      System.err.println("Error: Null code when getting the value at row " +
                         row + ", column " + column);
      return "xyz";
    }
    
    switch (column)
    {
      case 0: return Utility.getDateTimeString(new Date(code.timestamp));
      case 1: return LogCache.getLevelString(code.level);
      case 2: return String.valueOf(code.threadId);
      case 3: return code.msg;
      default: return "abcd";
    }
  }
  
  
  public Point getCodeOffsetStart(final int row)
  {
    Code code = subset.getCode(row);
    return new Point(code.offsetStart, code.offsetEnd);
  }
  
  
  @Override
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }
}
