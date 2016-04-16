package io.miti.logasaurus.app;

import io.miti.logasaurus.model.CodeTableModel;
import io.miti.logasaurus.model.Codes;
import io.miti.logasaurus.util.LogFilter;
import io.miti.logasaurus.util.Utility;
import io.miti.logasaurus.util.WindowState;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public final class ViewerPanel extends JPanel
{
  /** Default serial ID. */
  private static final long serialVersionUID = 1L;
  
  /** The label showing the open file name. */
  private JLabel lblFile = null;

  /** The data table. */
  private JTable table = null;
  
  /** The refresh button. */
  private JButton btnRefresh = null;
  
  /** The search filter. */
  private JTextField tfFilter = null;
  
  private static ViewerPanel appPanel = null;
  
  /** Whether to sync the two views. */
  private boolean shouldSync = true;
  
  private ViewerPanel()
  {
    super();
  }
  
  
  private ViewerPanel(final LayoutManager layout)
  {
    super(layout);
  }


  public static ViewerPanel getInstance()
  {
    if (appPanel == null)
    {
      appPanel = new ViewerPanel(new BorderLayout());
      final int size = 20;
      appPanel.setBorder(BorderFactory.createEmptyBorder(5, size, 5, size));
      appPanel.init();
    }
    
    return appPanel;
  }
  
  
  private void init()
  {
    // Load the table data
    CodeTableModel ctm = new CodeTableModel();
    table = new JTable(ctm);
    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).
      setHorizontalAlignment(JLabel.CENTER);
    JScrollPane scroll = new JScrollPane(table);
    
    // Set the table column widths
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    table.getColumnModel().getColumn(0).setMinWidth(120);
    table.getColumnModel().getColumn(0).setPreferredWidth(120);
    table.getColumnModel().getColumn(1).setMinWidth(60);
    table.getColumnModel().getColumn(1).setPreferredWidth(60);
    table.getColumnModel().getColumn(2).setMinWidth(60);
    table.getColumnModel().getColumn(2).setPreferredWidth(60);
    table.getColumnModel().getColumn(3).setPreferredWidth(5000);
    
    
    JPanel topPanel = getTopPanel();
    JPanel bottomPanel = getBottomPanel();
    
    // Build the middle panel
    // appPanel = new ViewerPanel(new BorderLayout());
    // appPanel.setBackground(Color.WHITE);
    appPanel.add(topPanel, BorderLayout.NORTH);
    appPanel.add(scroll, BorderLayout.CENTER);
    appPanel.add(bottomPanel, BorderLayout.SOUTH);
  }
  
  
  private JPanel getTopPanel()
  {
    // The panel containing the whole section above the table
    JPanel panel = new JPanel(new BorderLayout(0, 2));
    
    // One panel per row of controls
    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    lblFile = new JLabel("                  ");
    lblFile.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
        BorderFactory.createEmptyBorder(2, 2, 2, 2)));
    Dimension prefSize = lblFile.getPreferredSize();
    prefSize.width = 100;
    lblFile.setMinimumSize(prefSize);
    lblFile.setPreferredSize(prefSize);
    JButton btnLoad = new JButton("...");
    btnLoad.setMnemonic(KeyEvent.VK_F);
    btnLoad.setToolTipText("Open a log file");
    btnLoad.setMargin(new Insets(2, 2, 2, 2));
    btnLoad.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        boolean result = openLogFile();
        if (result)
        {
          loadLogFile();
        }
      }
    });
    
    btnRefresh = new JButton("Refresh");
    btnRefresh.setMnemonic(KeyEvent.VK_R);
    btnRefresh.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        refreshLogFile();
      }
    });
    btnRefresh.setToolTipText("Reload the last opened file");
    btnRefresh.setEnabled(isValidInputFile());
    
    // TODO Add mnemonic for Refresh and X
    final JLabel lblSelect = new JLabel("File:  ");
    lblSelect.setDisplayedMnemonic(KeyEvent.VK_F);
    panel1.add(lblSelect);
    panel1.add(lblFile);
    panel1.add(Box.createHorizontalStrut(6));
    panel1.add(btnLoad);
    panel1.add(Box.createHorizontalStrut(6));
    panel1.add(btnRefresh);
    
    // Set up a text field at the top, for filtering
    JPanel panel2 = new JPanel(new BorderLayout());
    JButton btnClear = new JButton("X");
    btnClear.setMnemonic(KeyEvent.VK_X);
    btnClear.setToolTipText("Clear the filter");
    JPanel midPanel = new JPanel(new BorderLayout());
    tfFilter = new JTextField();
    tfFilter.setToolTipText("Find matching words in the log file");
    midPanel.add(tfFilter, BorderLayout.CENTER);
    midPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    
    final JLabel lblSearch = new JLabel("Search: ");
    lblSearch.setLabelFor(tfFilter);
    lblSearch.setDisplayedMnemonic(KeyEvent.VK_E);
    panel2.add(lblSearch, BorderLayout.WEST);
    panel2.add(midPanel, BorderLayout.CENTER);
    panel2.add(btnClear, BorderLayout.EAST);
    btnClear.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        tfFilter.setText("");
        keyChanged("");
        tfFilter.requestFocusInWindow();
      }
    });
    
    tfFilter.addKeyListener(new KeyListener()
    {
      @Override
      public void keyPressed(final KeyEvent arg0)
      {
      }

      @Override
      public void keyReleased(final KeyEvent arg0)
      {
        keyChanged(tfFilter.getText());
      }

      @Override
      public void keyTyped(final KeyEvent arg0)
      {
      }
    });
    
    panel.add(panel1, BorderLayout.NORTH);
    panel.add(panel2, BorderLayout.SOUTH);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    return panel;
  }
  
  
  private JPanel getBottomPanel()
  {
    JPanel panel = new JPanel(new FlowLayout(0, 0, FlowLayout.LEFT));
    JCheckBox cbSync = new JCheckBox("Synchronize with file viewer");
    cbSync.setMnemonic(KeyEvent.VK_C);
    cbSync.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e) 
      {
        syncWithViewer(e);
      }
    });
    cbSync.setSelected(true);
    
    panel.add(cbSync);
    return panel;
  }
  
  
  private void syncWithViewer(final ActionEvent e)
  {
    JCheckBox source = (JCheckBox) e.getSource();
    shouldSync = source.isSelected();
  }
  
  
  /**
   * Handle a change to the search filter.
   * 
   * @param text the new search filter
   */
  private void keyChanged(final String text)
  {
    keyChanged(text, false);
  }
  
  
  /**
   * Handle a change to the search filter.
   * 
   * @param text the new search filter
   */
  private void keyChanged(final String text, final boolean forceUpdate)
  {
    // Execute the search
    if (((CodeTableModel) table.getModel()).setSubsetKey(text.toLowerCase(), forceUpdate))
    {
      // Save the cursor and then change it to a wait cursor
      final Cursor currCursor = LogViewer.getFrame().getCursor();
      LogViewer.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      
      // Notify the table of the data change
      ((CodeTableModel) table.getModel()).fireTableDataChanged();
      
      // Restore the previous cursor
      LogViewer.getFrame().setCursor(currCursor);
    }
  }


  public void takeFocus()
  {
    tfFilter.requestFocusInWindow();
  }
  
  
  private void refreshLogFile()
  {
    Codes.removeAllData();
	keyChanged("");
    ((CodeTableModel) table.getModel()).fireTableDataChanged();
    loadLogFile();
    keyChanged(tfFilter.getText(), true);
  }
  
  
  private boolean openLogFile()
  {
    // Get the input file directory, if a file was previously opened
    final File inputDir = getFileInputDir();
    JFileChooser fc = new JFileChooser(inputDir);
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fc.addChoosableFileFilter(new LogFilter());
    int rc = fc.showOpenDialog(LogViewer.getFrame());
    if (rc == JFileChooser.APPROVE_OPTION)
    {
      // Get the name of the selected file
      File file = fc.getSelectedFile();
      if (!file.exists() || !file.isFile())
      {
        return false;
      }
      
      // System.out.println("opening " + file.getAbsolutePath());
      WindowState.getInstance().setLastInputFile(file.getAbsolutePath());
      return true;
    }
    
    return false;
  }


  private File getFileInputDir()
  {
    String file = WindowState.getInstance().getLastInputFile();
    if (file == null) // || (file.length() == 0))
    {
      return new File(".");
    }
    
    File f = new File(file);
    if ((f.exists()) && (f.isFile()))
    {
      return f;
    }
    
    return new File(".");
  }
  
  
  private boolean isValidInputFile()
  {
    File file = getFileInputDir();
    return (file.exists() && file.isFile());
  }
  
  
  public void loadLogFile()
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        File file = getFileInputDir();
        if ((file != null) && (file.exists() && (file.isFile())))
        {
          String fname = file.getName();
          lblFile.setText(fname);
          lblFile.repaint();
          
          // Save the cursor and then change it to a wait cursor
          final Cursor currCursor = LogViewer.getFrame().getCursor();
          LogViewer.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          
          // Load the data file
          Codes.loadData(file.getAbsolutePath());
          LogViewer.setStatusBarMessage1(file.lastModified());
          LogViewer.getFrame().setTitle(Utility.getAppName() + " - " + file.getParent());
          ((CodeTableModel) table.getModel()).createSubset();
          ((CodeTableModel) table.getModel()).fireTableDataChanged();
          ((CodeTableModel) table.getModel()).showRowCount();
          
          btnRefresh.setEnabled(isValidInputFile());
          takeFocus();
          LogViewer.getFrame().setCursor(currCursor);
        }
        else
        {
          // Show an error message
          showFileMissingErrorMessage(file);
          
          // Reset the UI
          LogViewer.setStatusBarMessage1("Ready");
          LogViewer.getFrame().setTitle(Utility.getAppName());
          
          // Remove the data and clear the table
          Codes.removeAllData();
          ((CodeTableModel) table.getModel()).fireTableDataChanged();
          
          // Give the focus to the search box
          btnRefresh.setEnabled(isValidInputFile());
          takeFocus();
        }
      }
    });
  }
  
  
  protected void showFileMissingErrorMessage(File file)
  {
    String msg = null;
    if (file == null)
    {
      msg = "No file was found";
    }
    else if (!file.exists())
    {
      msg = "The file was not found:\n\n  " + file.getAbsolutePath();
    }
    else if (!file.isFile())
    {
      msg = "The input is not a file:\n\n  " + file.getAbsolutePath();
    }
    
    JOptionPane.showMessageDialog(LogViewer.getFrame(), msg,
        "File Not Found", JOptionPane.ERROR_MESSAGE);
    System.err.println("The file doesn't exist");
  }
  
  
  public void setFilePanelLocation()
  {
    // Get the selected row index; if we're syncing, we need to move
    // the file viewer cursor to the right location
    final int selRow = table.getSelectedRow();
    if (shouldSync && (selRow >= 0))
    {
      // Get the offset
      final Point offset = ((CodeTableModel) table.getModel()).getCodeOffsetStart(selRow);
      FilePanel.getInstance().setViewLocation(offset);
    }
  }
  
  
  public void resetFilters()
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        // Reset the filter
        tfFilter.setText("");
      }
    });
  }
}
