package io.miti.logasaurus.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class LogFilter extends FileFilter
{

  public LogFilter()
  {
    super();
  }
  
  
  @Override
  public boolean accept(final File f)
  {
    if (f.isDirectory())
    {
      return true;
    }
    
    String ext = getExtension(f);
    if ((ext != null) && (ext.equals("log")))
    {
      return true;
    }
    
    return false;
  }
  
  
  @Override
  public String getDescription()
  {
    return "Log files (*.log)";
  }
  
  
  public static String getExtension(File f)
  {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 &&  i < s.length() - 1) {
        ext = s.substring(i+1).toLowerCase();
    }
    
    return ext;
  }
}
