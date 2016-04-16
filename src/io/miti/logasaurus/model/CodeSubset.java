/**
 * 
 */
package io.miti.logasaurus.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Define a subset of codes, based on search criteria.
 * 
 * @author mike
 * @version 1.0
 */
public final class CodeSubset
{
	/** The code key for this subset. */
	private CodeKey key = null;
	
	/** The list of indexes into the codes data, for this subset. */
	private List<Integer> lookup = new ArrayList<Integer>(50);
	
	/**
	 * Default constructor.
	 */
	public CodeSubset()
	{
		super();
	}
	
	
	/**
	 * Constructor taking a code.
	 * 
	 * @param code the code
	 */
	public CodeSubset(final String code)
	{
		buildKey(code);
		loadSubset();
	}
	
	
	/**
	 * Build the key using the code.
	 * 
	 * @param code the code
	 */
	private void buildKey(final String code)
	{
		key = new CodeKey(code);
	}
	
	
	/**
	 * Load the subset.
	 */
	private void loadSubset()
	{
		// Iterate over the list
		int index = 0;
		Iterator<Code> iter = Codes.getIterator();
		while (iter.hasNext())
		{
			// Get the next code in the list
			Code a = iter.next();
			
			// Check for a match
			if (key.codeMatches(a))
			{
				lookup.add(Integer.valueOf(index));
			}
			
			++index;
		}
	}
	
	
	/**
	 * Return the size of the subset.
	 * 
	 * @return the size of the subset
	 */
	public int getCount()
	{
		return lookup.size();
	}

	
	/**
	 * Return the key data.
	 * 
	 * @return the key data
	 */
	public CodeKey getKey()
	{
		return key;
	}
	
	
	/**
	 * Return the code at an index.
	 * 
	 * @param index the index of the code to return
	 * @return the code at the index
	 */
	public Code getCode(final int index)
	{
	  Code c = null;
	  
	  if (index >= lookup.size())
	  {
	    System.err.println("Error: Index " + index +
	                       " is outside the code subset range (size = "
	                       + lookup.size() + ")");
	    return null;
	  }
	  
	  try
	  {
	    // TODO This line can cause an IndexOutOfBounds exception
      final int match = lookup.get(index);
		  c = Codes.get(match);
	  }
	  catch (IndexOutOfBoundsException ex)
	  {
	    System.err.println("Index out of bounds exception: " + ex.getMessage());
	    ex.printStackTrace();
	  }
	  
		return c;
	}
	
	
	/**
	 * Return whether the key is empty.
	 * 
	 * @return if the key is empty
	 */
	public boolean isKeyEmpty()
	{
		return key.isEmpty();
	}
	
	
	/**
	 * Return whether the key is based on the supplied code.
	 * 
	 * @param code the code
	 * @return whether the key is based on the code
	 */
	public boolean isBasedOn(final String code)
	{
		return key.isBasedOn(code);
	}
	
	
	/**
	 * Empty the list.
	 */
	public void clear()
	{
		lookup.clear();
		lookup = null;
	}
}
