package de.at.home.uploads;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juuri on 27.04.16.
 */
@Service
public class StatusService
{
    private Map<String, UploadStatus> uploads = Collections.synchronizedMap(new HashMap<String, UploadStatus>());

    public boolean addUploadWhenNotExistent(String fileName, int contentLength)
    {
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setStartTime(System.currentTimeMillis());
        uploadStatus.setContentLength(contentLength);
        uploadStatus.isRunning(true);

        synchronized(this.uploads)
        {
            if (this.uploads.containsKey(fileName)) return false;
            this.uploads.put(fileName, uploadStatus);
        }

        return true;
    }

    public Map<String, UploadStatus> getUploads()
    {
        return this.uploads;
    }

    public void reset()
    {
        this.uploads = Collections.synchronizedMap(new HashMap<String, UploadStatus>());
    }
}
