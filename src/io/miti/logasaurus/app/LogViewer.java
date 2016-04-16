package io.miti.logasaurus.app;

import io.miti.logasaurus.model.Content;
import io.miti.logasaurus.util.LogFileHandler;
import io.miti.logasaurus.util.StatusBar;
import io.miti.logasaurus.util.Utility;
import io.miti.logasaurus.util.WindowState;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LogViewer
{
  /** The name of the properties file. */
  public static final String PROPS_FILE_NAME = "log.prop";
  
  /** The one instance of the application. */
  private static LogViewer app = null;
  
  /** The application frame. */
  private JFrame frame = null;
  
  /** The status bar. */
  private StatusBar statusBar = null;
  
  /** The window state (position and size). */
  private WindowState windowState = null;
  
  /**
   * Default constructor.
   */
  private LogViewer()
  {
    super();
  }
  
  
  /**
   * Create the application's GUI.
   */
  private void createGUI()
  {
    // Determine where the input file lives
    checkInputFileSource();
  
    // Load the properties file
    windowState = WindowState.getInstance();
    
    // Set up the frame
    setupFrame();
    
    // Create the empty middle window
    initScreen();
    
    // Set up the status bar
    initStatusBar();
    
    // Set the frame icon
    frame.setIconImage(Content.getIcon("mainicon.png").getImage());
    
    // Mark this as working as a drop target
    frame.setTransferHandler(new LogFileHandler());
    
    // Display the window.
    frame.pack();
    frame.setVisible(true);
    // setStatusBarMessage("Ready");
    ViewerPanel.getInstance().takeFocus();
  }


  /**
   * Check how the application is run and save information
   * about the input file.
   */
  private void checkInputFileSource()
  {
    // See if we can find the input file at the root. If the URL is not null,
    // we're in a jar file.  If it's null, we're in an IDE.
    final URL url = getClass().getResource("/mainicon.png");
    if (url != null)
    {
      // We're running in a jar file
    // System.out.println("We're in a jar!");
      Utility.readFilesAsStream(true);
    }
    else
    {
      // We're not running in a jar file
    // System.out.println("We're NOT in a jar!");
      Utility.readFilesAsStream(false);
    }
  }
  
  
  /**
   * Set up the application frame.
   */
  private void setupFrame()
  {
    // Create and set up the window.
    frame = new JFrame(Utility.getAppName());
    
    // Have the frame call exitApp() whenever it closes
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(final WindowEvent e)
      {
        exitApp();
      }
    });
    
    // Set up the size of the frame
    frame.setPreferredSize(windowState.getSize());
    frame.setSize(windowState.getSize());
    
    // Set the position
    if (windowState.shouldCenter())
    {
      frame.setLocationRelativeTo(null);
    }
    else
    {
      frame.setLocation(windowState.getPosition());
    }
  }
  
  
  public static JFrame getFrame()
  {
    return app.frame;
  }
  
  
  /**
   * Initialize the main screen (middle window).
   */
  private void initScreen()
  {
    // Set up the tabs
    final JTabbedPane tabbed = new JTabbedPane();
    tabbed.addChangeListener(new ChangeListener()
    {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        final int sel = tabbed.getSelectedIndex();
        if (sel == 1)
        {
          // User selected the second tab, so sync up
          ViewerPanel.getInstance().setFilePanelLocation();
        }
      }
    });
    
    // Set up the tabs
    tabbed.addTab("Search", ViewerPanel.getInstance());
    tabbed.addTab("View", FilePanel.getInstance());
    tabbed.setToolTipTextAt(0, "Search the log file");
    tabbed.setToolTipTextAt(1, "View the log file");
    tabbed.setMnemonicAt(0, KeyEvent.VK_S);
    tabbed.setMnemonicAt(1, KeyEvent.VK_V);
    frame.getContentPane().add(tabbed, BorderLayout.CENTER);
  }
  
  
  /**
   * Initialize the status bar.
   */
  private void initStatusBar()
  {
    // Instantiate the status bar
    statusBar = new StatusBar();
    
    // Set the color and border
//    statusBar.setForeground(Color.black);
//    statusBar.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2),
//                              new SoftBevelBorder(SoftBevelBorder.LOWERED)));
    
    // Add to the content pane
    frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }
  
  
  /**
   * Exit the application.
   */
  private void exitApp()
  {
    // Store the window state in the properties file
    windowState.update(frame.getBounds());
    windowState.saveToFile(PROPS_FILE_NAME);
    
    // Close the application by disposing of the frame
    frame.dispose();
  }
  
  
  /**
   * Set the status bar message.
   *
   * @param msg the string to show in the status bar
   */
  public static void setStatusBarMessage1(final String msg)
  {
    // Update the status bar from the Swing EDT
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        app.statusBar.setText(msg);
      }
    });
  }
  
  
  /**
   * Set the status bar message.
   *
   * @param msg the string to show in the status bar
   */
  public static void setStatusBarMessage2(final String msg)
  {
    // Update the status bar from the Swing EDT
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        app.statusBar.setText2(msg);
      }
    });
  }
  
  
  public static void setStatusBarMessage1(final long datetime)
  {
    setStatusBarMessage1(" Last modified: " + Utility.getDateTimeString(new Date(datetime)));
  }
  
  
  /**
   * Entry point to the application.
   * 
   * @param args arguments passed to the application
   */
  public static void main(final String[] args)
  {
    // Make the application Mac-compatible
    Utility.makeMacCompatible();
    
    // Load the properties file data
    WindowState.load(PROPS_FILE_NAME);
    
    // Initialize the look and feel to the default for this OS
    Utility.initLookAndFeel();
    
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        // Run the application
        app = new LogViewer();
        app.createGUI();
      }
    });
  }
}
