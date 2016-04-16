package io.miti.logasaurus.util;

import java.util.HashMap;
import java.util.Map;

public class LogCache
{
  private static final Map<String, Integer> mapStrToInt;
  private static final Map<Integer, String> mapIntToStr;
  
  static
  {
    mapStrToInt = new HashMap<String, Integer>(10);
    mapIntToStr = new HashMap<Integer, String>(10);
    init();
  }
  
  /** Default constructor. */
  private LogCache()
  {
    super();
  }
  
  
  private static void init()
  {
    if (mapStrToInt.size() > 0)
    {
      return;
    }
    
    mapStrToInt.put("SEVERE", Integer.valueOf(60000));
    mapStrToInt.put("FATAL", Integer.valueOf(50000));
    mapStrToInt.put("ERROR", Integer.valueOf(40000));
    mapStrToInt.put("WARNING", Integer.valueOf(30000));
    mapStrToInt.put("INFO", Integer.valueOf(20000));
    mapStrToInt.put("CONFIG", Integer.valueOf(15000));
    mapStrToInt.put("DEBUG", Integer.valueOf(10000));
    mapStrToInt.put("TRACE", Integer.valueOf(5000));
    mapStrToInt.put("FINE", Integer.valueOf(4000));
    mapStrToInt.put("FINER", Integer.valueOf(3000));
    mapStrToInt.put("FINEST", Integer.valueOf(2000));
    
    mapIntToStr.put(Integer.valueOf(60000), "Severe");
    mapIntToStr.put(Integer.valueOf(50000), "Fatal");
    mapIntToStr.put(Integer.valueOf(40000), "Error");
    mapIntToStr.put(Integer.valueOf(30000), "Warn");
    mapIntToStr.put(Integer.valueOf(20000), "Info");
    mapIntToStr.put(Integer.valueOf(15000), "Config");
    mapIntToStr.put(Integer.valueOf(10000), "Debug");
    mapIntToStr.put(Integer.valueOf(5000), "Trace");
    mapIntToStr.put(Integer.valueOf(4000), "Fine");
    mapIntToStr.put(Integer.valueOf(3000), "Finer");
    mapIntToStr.put(Integer.valueOf(2000), "Finest");
  }
  
  
  public static String getLevelString(final int level)
  {
    return mapIntToStr.get(Integer.valueOf(level));
  }
  
  
  public static Integer getLevelInt(final String level)
  {
    return mapStrToInt.get(level);
  }
}
