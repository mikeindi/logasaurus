package io.miti.logasaurus.model;

public final class Code
{
  /** Member fields. */
  public long timestamp = 0L;
  public int offsetStart = 0;
  public int offsetEnd = 0;
  public int threadId = 0;
  public String threadName = null;
  public String msg = null;
  public int level = 0;
  
  
  /**
   * Default constructor.
   */
  public Code()
  {
    super();
  }
  
  
  public Code(long timestamp, int offsetStart, int threadId, String threadName,
      String msg, int level)
  {
    super();
    this.timestamp = timestamp;
    this.offsetStart = offsetStart;
    this.offsetEnd = offsetStart;
    this.threadId = threadId;
    this.threadName = threadName;
    this.msg = msg;
    this.level = level;
  }
}
