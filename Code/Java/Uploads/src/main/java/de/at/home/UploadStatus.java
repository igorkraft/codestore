package de.at.home;

public class UploadStatus
{
    private volatile long count;
    private volatile long startTime;
    private volatile int contentLength;
    private volatile boolean runningState;

    public void setCountOfWrittenBytes(long count)
    {
        this.count = count;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public void setContentLength(int contentLength)
    {
        this.contentLength = contentLength;
    }

    public void isRunning(boolean runningState)
    {
        this.runningState = runningState;
    }

    @Override
    public String toString()
    {
        String result = "{";
        result = result + "\"count\":\"" + this.count + "\",";
        result = result + "\"startTime\":\"" + this.startTime + "\",";
        result = result + "\"contentLength\":\"" + this.contentLength + "\",";
        result = result + "\"runningState\":\"" + this.runningState + "\"";
        return result + "}";
    }
}
