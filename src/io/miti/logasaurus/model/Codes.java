/**
 * 
 */
package io.miti.logasaurus.model;

import io.miti.logasaurus.app.FilePanel;
import io.miti.logasaurus.util.LogCache;
import io.miti.logasaurus.util.Utility;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hold the cache of code data.
 * 
 * @author mike
 * @version 1.0
 */
public final class Codes
{
	/**
	 * The master list of all codes.
	 */
	private static List<Code> codes = null;
	
	/** The regex pattern string. */
  private static final String regex = "^\\[#\\|(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2}\\.\\d{3}-\\d{4})\\|([^|]*)\\|([^|]*)\\|([^|]*)\\|_ThreadID=(\\d{1,5});_ThreadName=([^|;]*);\\|(.*)\\|#\\]$";
  
  private static final Pattern pattern;
  
  static
  {
    pattern = Pattern.compile(regex);
  }

	
	/**
	 * Default constructor.
	 */
	public Codes()
	{
		super();
	}
	
	
	/**
	 * Manage the cache of code data.
	 */
	public static void loadData(final String inputFile)
	{
	  parseInputFile(inputFile);
	}
	
	
	private static void parseInputFile(String file)
	{
	  StringBuilder text = new StringBuilder(10000);
	  List<String> remarks = new ArrayList<String>(100);
	  List<Point> remarkOffsets = new ArrayList<Point>(100);
	  
	  BufferedReader br = null;
	  
	  try
	  {
  	  br = new BufferedReader(new FileReader(file));
  	  String line = br.readLine();
  	  int remarkStart = 0;
  	  boolean inRemark = false;
  	  StringBuilder remark = new StringBuilder(100);
  	  while (line != null)
  	  {
  	    // Check if we're in the middle of a remark
  	    if (inRemark)
  	    {
  	      text.append(line).append("\n");
  	      remark.append(line);
  	      inRemark = !line.endsWith("|#]");
  	      if (!inRemark)
  	      {
  	        remarks.add(new String(remark.toString()));
  	        remarkOffsets.add(new Point(remarkStart, text.toString().length()));
  	        remark.setLength(0);
  	      }
  	    }
  	    else
  	    {
  	      // This is the start of a remark
  	      remarkStart = text.toString().length();
  	      text.append(line).append("\n");
  	      inRemark = line.startsWith("[#|");
  	      if (inRemark)
  	      {
  	        remark.append(line);
  	        inRemark = !line.endsWith("|#]");
  	        if (!inRemark)
  	        {
  	          remarks.add(new String(remark.toString()));
  	          remarkOffsets.add(new Point(remarkStart, remarkStart + line.length()));
  	          remark.setLength(0);
  	        }
  	      }
  	    }
  
  	    line = br.readLine();
  	  }
  	  
	    br.close();
	    br = null;
	  }
	  catch (Exception ioe)
	  {
	    System.err.println("Exception reading file: " + ioe.getMessage());
	    ioe.printStackTrace();
	  }

	  // Save the text variable to the 2nd tab
	  FilePanel.getInstance().setText(text.toString());
	  
	  // Iterate over remarks and remarkOffsets, parse and save to the cache
	  Matcher m = null;
	  final int count = remarks.size();
	  codes = new Vector<Code>(count);
	  for (int i = 0; i < count; ++i)
	  {
	    m = pattern.matcher(remarks.get(i));
	    if (m.matches())
	    {
	      final int groupCount = m.groupCount();
	      if (groupCount == 8)
	      {
	        long timestamp = Utility.getTimestamp(m.group(1), m.group(2));
	        final int levelNum = LogCache.getLevelInt(m.group(3)).intValue();
	        Code code = new Code(timestamp, remarkOffsets.get(i).x,
	            Integer.parseInt(m.group(6)), m.group(7), m.group(8),
	            levelNum);
	        code.offsetEnd = remarkOffsets.get(i).y;
	        codes.add(code);
	      }
	    }
	  }
	  
	  return;
	}


  /**
	 * Return the code at a particular index.
	 * 
	 * @param index the index
	 * @return the code with that index
	 */
	public static Code get(final int index)
	{
		return codes.get(index);
	}
	
	
	/**
	 * Return the data.
	 * 
	 * @return the data
	 */
	public static List<Code> getData()
	{
	  return codes;
	}
	
	
	/**
	 * Return an iterator of all codes.
	 * 
	 * @return the code iterator
	 */
	public static Iterator<Code> getIterator()
	{
		return codes.iterator();
	}
	
	
	/**
	 * Return the number of codes.
	 * 
	 * @return the number of codes
	 */
	public static int getSize()
	{
	  if (codes == null)
	  {
	    return 0;
	  }
	  
	  return codes.size();
	}


  public static void removeAllData()
  {
    codes = new Vector<Code>(0);
  }
}
