package io.miti.logasaurus.util;

import io.miti.logasaurus.app.ViewerPanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.TransferHandler;

public class LogFileHandler extends TransferHandler
{
  private static final long serialVersionUID = 1L;

  public LogFileHandler()
  {
    super();
  }
  
  
  @Override
  public boolean canImport(TransferSupport info)
  {
    if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
    {
      return false;
    }
    
    return true;
  }
  
  
  @Override
  @SuppressWarnings("unchecked")
  public boolean importData(TransferSupport info)
  {
    if (!info.isDrop())
    {
      return false;
    }

    // Check for FileList flavor
    if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
    {
      return false;
    }

    // Get the fileList that is being dropped.
    Transferable t = info.getTransferable();
    List<File> data;
    try
    {
      data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
    }
    catch (Exception e)
    {
      return false;
    }
    
    // Load the file
    File file = data.get(0);
    WindowState.getInstance().setLastInputFile(file.getAbsolutePath());
    ViewerPanel.getInstance().resetFilters();
    ViewerPanel.getInstance().loadLogFile();
    
    return true;
  }
}
