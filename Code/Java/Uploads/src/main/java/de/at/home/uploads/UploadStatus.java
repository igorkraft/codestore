package de.at.home.uploads;

public class UploadStatus
{
    // TODO Lombok einsetzen
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
}
