package io.miti.logasaurus.model;

import io.miti.logasaurus.util.Logger;
import io.miti.logasaurus.util.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

/**
 * Class to manage providing content from flat text-files.
 * 
 * @author mwallace
 * @version 1.0
 */
public final class Content
{
  /**
   * Holder for no data to return.
   */
  // private static final List<String> NO_CONTENT = new ArrayList<String>(0);
  
  /** List of codes. */
//  public static final int CODE_LIST = 0;
  
  /** The map of filenames for IDs. */
//  private static HashMap<Integer, String> mapFilenames = null;
  
  /**
   * Static block of code to execute at startup.
   */
//  static
//  {
//    mapFilenames = new HashMap<Integer, String>(20);
//    mapFilenames.put(CODE_LIST, "codes.csv");
//  }
  
  
  /**
   * Default constructor.
   */
  private Content()
  {
    super();
  }
  
  
  /**
   * Return the filename for the specified ID.
   * 
   * @param id the requested type of file
   * @return the corresponding file name
   */
//  public static String getFilename(final int id)
//  {
//    return mapFilenames.get(Integer.valueOf(id));
//  }
  
  
  /**
   * Return the contents of a file as a single string.
   * 
   * @param id the ID of the file to read
   * @return the contents of the file as a string
   */
//  public static String getContentAsString(final int id)
//  {
//    // Get the data and check if it's null or empty
//    final List<String> data = getContent(id);
//    if ((data == null) || (data.size() < 1))
//    {
//      // No strings to join
//      return "";
//    }
//    
//    // Build the string, adding a space after each line
//    StringBuilder sb = new StringBuilder(500);
//    for (String row : data)
//    {
//      sb.append(row).append(' ');
//    }
//    
//    // Empty the list
//    data.clear();
//    
//    // Return the string
//    return sb.toString();
//  }
  
  
  /**
   * Return the contents of the file associated with the specified ID.
   * 
   * @param id a supported data ID
   * @return the contents of the file associated with the ID
   */
//  public static List<String> getContent(final int id)
//  {
//    // Check the parameter
//    final String name = mapFilenames.get(Integer.valueOf(id));
//    if ((name == null) || (name.length() < 1))
//    {
//      // The ID was not found
//      return NO_CONTENT;
//    }
//    
//    // Get the file contents
//    List<String> contents = readTextFromFile(name);
//    
//    // Return the contents of the file
//    return contents;
//  }
  
  
  /**
   * Write out the strings in the list.
   * 
   * @param list the strings to print
   */
  public static void printList(final HashMap<String, List<String>> list)
  {
    // Check the input
    if (list == null)
    {
      Logger.error("Content::printList(): The list is null");
      return;
    }
    
    // Iterate over the list of keys
    for (Entry<String, List<String>> e : list.entrySet())
    {
      // Write out the key
      Logger.info(e.getKey());
      
      // Iterate over the list of strings for this key
      for (String value : e.getValue())
      {
        Logger.info("  " + value);
      }
    }
  }
  
  
  /**
   * Return the path to use for accessing this file.
   * 
   * @param fileName the input file name
   * @return the full path for the file name
   */
  public static String getContentPath(final String fileName)
  {
    // Check how to read the input files
    if (Utility.readFilesAsStream())
    {
      return "/" + fileName;
    }
    
    return "data/" + fileName;
  }
  
  
  /**
   * Get the specified icon.
   * 
   * @param filename the name of the image file
   * @return the generated icon
   */
  public static ImageIcon getIcon(final String filename)
  {
    // Check how to read the input files
	if (Utility.readFilesAsStream())
	{
		return new ImageIcon(Content.class.getResource("/" + filename));
	}
	
	return new ImageIcon("data/" + filename);
  }
  
  
  /**
   * Read the contents of a file and return as a list of strings.
   * 
   * @param fileName the input filename
   * @return list of strings from the file
   */
//  private static List<String> readTextFromFile(final String fileName)
//  {
//    // Declare the list of strings that will get populated
//    // with the file contents
//    List<String> contents = new ArrayList<String>(50);
//    
//    // Check how to read the input file
//    if (Utility.readFilesAsStream())
//    {
//      readFileContentsAsStream(contents, fileName);
//    }
//    else
//    {
//      readFileContents(contents, fileName);
//    }
//    
//    // Return the file contents
//    return contents;
//  }
  
  
  /**
   * Read the file as a stream.
   * 
   * @param contents the list to populate with the file contents
   * @param fileName the input filename
   */
//  private static void readFileContentsAsStream(final List<String> contents,
//                                               final String fileName)
//  {
//    // Declare the reader
//    BufferedReader in = null;
//    
//    try
//    {
//      // Get the name of the input file
//      final String fullPath = getContentPath(fileName);
//      
//      // Get the input stream
//      InputStream is = Content.class.getResourceAsStream(fullPath);
//      if (is == null)
//      {
//        Logger.error("Exception: Stream is null for " + fullPath);
//      }
//      else
//      {
//        // Open the reader
//        in = new BufferedReader(new InputStreamReader(is));
//        
//        // Read the stream and add each row to contents
//        String str;
//        while ((str = in.readLine()) != null)
//        {
//          contents.add(str);
//        }
//        
//        // Close the input reader
//        in.close();
//        in = null;
//      }
//    }
//    catch (IOException e)
//    {
//      Logger.error("Exception: " + e.getMessage());
//      contents.clear();
//    }
//    finally
//    {
//      // Check if the reader was closed
//      if (in != null)
//      {
//        try
//        {
//          in.close();
//        }
//        catch (IOException e)
//        {
//          Logger.error(e);
//        }
//        
//        in = null;
//      }
//    }
//  }
  
  
  /**
   * Read the file as a regular file.
   * 
   * @param contents the list to populate with the file contents
   * @param fileName the input filename
   */
//  private static void readFileContents(final List<String> contents,
//                                       final String fileName)
//  {
//    // Initialize the reader
//    BufferedReader in = null;
//    
//    try
//    {
//      // Get the full path/filename
//      final String fullPath = getContentPath(fileName);
//      if (fullPath == null)
//      {
//        Logger.error("Exception: Null path for " + fileName);
//      }
//      else
//      {
//        // Open the reader
//        FileReader reader = new FileReader(fullPath);
//        
//        // Open the reader
//        in = new BufferedReader(reader);
//        String str;
//        while ((str = in.readLine()) != null)
//        {
//          contents.add(str);
//        }
//        
//        // Close the reader
//        in.close();
//        in = null;
//      }
//    }
//    catch (IOException e)
//    {
//      Logger.error("Exception: " + e.getMessage());
//      contents.clear();
//    }
//    finally
//    {
//      // Check if the reader was closed
//      if (in != null)
//      {
//        try
//        {
//          in.close();
//        }
//        catch (IOException e)
//        {
//          Logger.error(e);
//        }
//        
//        in = null;
//      }
//    }
//  }
  
  
  /**
   * Return the number of leading spaces.
   * 
   * @param input the input string
   * @return the number of leading spaces
   */
  public static int getNumberOfLeadingSpaces(final String input)
  {
    // Check the input
    if ((input == null) || (input.length() < 1))
    {
      return 0;
    }
    
    // Save the string length
    final int len = input.length();
    
    // The current index into the string
    int index = 0;
    
    // Iterate over the string until we hit either the end of the
    // string or a non-space character
    while ((index < len) && (input.charAt(index) == ' '))
    {
      ++index;
    }
    
    // Return the number of leading spaces
    return index;
  }
  
  
  /**
   * Return the contents of a file as a string.
   * 
   * @param file the input file
   * @return the contents of the file
   */
  public static String getFileAsText(final File file)
  {
      // Check the input parameter
      if((file == null) || (!file.exists()) || (file.isDirectory()))
      {
          return "";
      }

      // Get the text of the file
      StringBuilder sb = new StringBuilder(1000);

      // Read the file
      BufferedReader in = null;
      try
      {
          in = new BufferedReader(new FileReader(file));
          String str;
          while((str = in.readLine()) != null)
          {
              sb.append(str).append('\n');
          }

          in.close();
          in = null;
      }
      catch(IOException e)
      {
          e.printStackTrace();
      }
      finally
      {
          if(in != null)
          {
              try
              {
                  in.close();
              }
              catch(IOException e)
              {
                  e.printStackTrace();
              }

              in = null;
          }
      }

      // Return the builder
      return sb.toString();
  }
}
