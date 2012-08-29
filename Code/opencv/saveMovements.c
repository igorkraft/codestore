/**
 * http://events.ccc.de/congress/2006/Fahrplan/events/1604.en.html
 * http://opencv.willowgarage.com/documentation/genindex.html
 * http://www.aishack.in/2010/07/drawing-histograms-in-opencv/
**/

#include "cv.h"
#include "highgui.h"
#include <stdio.h>
#include <ctype.h>
#include <time.h>
#include <string.h>

bool movement(IplImage* frame,IplImage* lastFrame,IplImage* diff,IplImage* grey,CvHistogram* hist)
{
  cvAbsDiff( frame, lastFrame, diff );
  cvCvtColor( diff, grey, CV_BGR2GRAY );
  cvCalcHist(&grey, hist, 0, 0);
  float threshold = 20;
  float sum = 0.0;
  for (int i = threshold ; i < 255 ; i++)
  {
    float bin_val = cvQueryHistValue_1D(hist, i);
    sum = sum + bin_val;
  }
  cvClearHist(hist);
  cvCopy( frame, lastFrame );
  return sum > 50.0 ? true : false;
}

void getVideoFileLocation(time_t now, char* videoLocation)
{
  time(&now);
  char* destPath = "/home/user/";
  char* extension = ".avi";
  char* curTime = ctime(&now);
  int newLen = strlen(destPath) + strlen (curTime) + strlen(extension) + 1;
  videoLocation[newLen-1] = '\0';
  int curIndex = 0;
  for (int i = 0 ; i < strlen(destPath) ; i++)
  {
    videoLocation[curIndex] = destPath[i];
    curIndex = curIndex + 1;
  }
  for (int i = 0 ; i < strlen(curTime) ; i++)
  {
    videoLocation[curIndex] = curTime[i];
    if (videoLocation[curIndex] == ':') videoLocation[curIndex] = '-';
    if (videoLocation[curIndex] == '\n') videoLocation[curIndex] = ' ';
    curIndex = curIndex + 1;
  }
  for (int i = 0 ; i < strlen(extension) ; i++)
  {
    videoLocation[curIndex] = extension[i];
    curIndex = curIndex + 1;
  }
}

void debug(char* message,bool enabled)
{
  if (enabled) printf("%s\n",message);
}

void debug(char* message,int value,bool enabled)
{
  if (enabled) printf("%s%i\n",message,value);
}

int main( int argc, char** argv )
{
	CvCapture			 *capture                    = cvCaptureFromCAM( 0 );
	IplImage*      frame                       = cvQueryFrame(capture);
	IplImage*      lastFrame                   = cvCreateImage( cvGetSize(frame), 8, 3 );
	IplImage*      diff                        = cvCreateImage( cvGetSize(frame), 8, 3 );
	IplImage*      grey                        = cvCreateImage( cvGetSize(frame), 8, 1 );
	int 					 fps                         = 15;
	int						 length                      = 120;
  int            numBins                     = 256;
  float          range[]                     = {0, 255};
  float*         ranges[]                    = { range };
  CvHistogram*   hist                        = cvCreateHist(1, &numBins, CV_HIST_ARRAY, ranges, 1);
  int            startBufferLen              = 1*fps; // zu puffernde Sekunden, die im Falle von Bewegung vorn an das Video gehängt werden
  int            startBufferIndex            = 0;
  int            comparisonStep              = 5; // legt die Entfernung der Frames fest, mit denen die Bewegungserkennung durchgeführt wird
  int            curComparison               = 1;
  bool           isRecordActive              = false;
  int            recordStopAfterLastMovement = 10*fps; //sollte nicht zu kurz sein, damit nicht bei jeder Bewegungslosigkeit ein neues Video angefangen wird (Angabe in Sekunden)
  int            curTimeAfterLastMovement    = recordStopAfterLastMovement;
  bool           logEnabled                  = false;
  CvVideoWriter* writer;
  time_t         now;
  IplImage*      startBuffer[startBufferLen];
  char           videoLocation[300];
  cvNamedWindow( "Capture", CV_WINDOW_AUTOSIZE );
  cvMoveWindow( "Capture", 0, 0 );
  cvCopy( frame, lastFrame ); // verhindert, dass gleich beim Programmstart eine Aufzeichnung gestartet wird
  
  for (int i = 0 ; i < startBufferLen ; i++)
  {
    startBuffer[i] = cvCreateImage( cvGetSize(frame), 8, 3 );
  }
  
  debug("Initialisierung beendet",logEnabled);
  
	for(int i = 0 ; i < length*fps ; i++)
	{  
		frame = cvQueryFrame(capture);
		debug("Frame erfolgreich geholt",logEnabled);
		debug("startBufferIndex: ",startBufferIndex,logEnabled);
		cvCopy( frame, startBuffer[startBufferIndex] );
		debug("Frame in Startpuffer kopiert",logEnabled);
		startBufferIndex = startBufferIndex == startBufferLen - 1 ? 0 : startBufferIndex + 1;
	  if (curComparison == comparisonStep && movement(frame,lastFrame,diff,grey,hist))
	  {
	    debug("es wurde Bewegung erkannt",logEnabled);
	    curTimeAfterLastMovement = 1;
	    if (!isRecordActive)
	    {
	      // neue Aufzeichnung beginnen
	      getVideoFileLocation(now,videoLocation);
        writer = cvCreateVideoWriter(videoLocation,CV_FOURCC('x','v','i','d'),fps,cvGetSize(frame));
        int bufferTempIndex = startBufferIndex;
        do
        {
          cvWriteFrame( writer, startBuffer[bufferTempIndex] );
          bufferTempIndex = bufferTempIndex == startBufferLen - 1 ? 0 : bufferTempIndex + 1;
        }
        while(bufferTempIndex != startBufferIndex);
	      isRecordActive = true;
	    }
	  }
	  debug("vor isRecordActive",logEnabled);
	  if (isRecordActive)
	  {
	    if (curTimeAfterLastMovement < recordStopAfterLastMovement)
	    {
	      curTimeAfterLastMovement = curTimeAfterLastMovement + 1;
	    }
	    else
	    {
	      // Aufzeichnung stoppen
	      isRecordActive = false;
	      printf("Aufzeichnung beenden: %s\n", videoLocation);
      	cvReleaseVideoWriter(&writer);
	    }
	  }
    curComparison = curComparison == comparisonStep ? 1 : curComparison + 1;
		if (isRecordActive) cvWriteFrame( writer, frame );
		cvShowImage( "Capture", frame );
		if ( cvWaitKey( 2 ) == 27 ) break;
		debug("Durchlauf beendet",logEnabled);
	}
  cvReleaseImage( &lastFrame );
  cvReleaseImage( &frame );
  cvReleaseImage( &grey );
  cvReleaseImage( &diff );
  for (int i = 0 ; i < startBufferLen ; i++)
  {
    cvReleaseImage( &startBuffer[i] );
  }
  cvClearHist(hist);
  cvReleaseHist(&hist);
	if (isRecordActive) cvReleaseVideoWriter(&writer);
	cvReleaseCapture(&capture);
	cvDestroyWindow( "Capture" );
	return 0;
}
