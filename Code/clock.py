#!/usr/bin/python
#coding=UTF-8

import sys
import time
import calendar
import subprocess
import os
import math

imgLoc="/media/Daten/Bilder/"

def setPointer(pointerId,alpha,dimensions):
  sx = math.cos(alpha)
  rx = math.sin(alpha)
  ry = -math.sin(alpha)
  sy = math.cos(alpha)
  subprocess.call(["convert",imgLoc+"minute.png","-virtual-pixel","Transparent","-affine",str(sx)+","+str(rx)+","+str(ry)+","+str(sy)+",0,0","-transform","/tmp/minute.png"])
  subprocess.call(["composite","-geometry","+1130+10","/tmp/minute.png",imgLoc+"Bender.png",imgLoc+"bg.png"])
  return

alpha=1.25*math.pi
setPointer("",alpha,"")


sys.exit("Ende")

while True:
  time.sleep(10)

year      = time.localtime(time.time())[0]
month     = time.localtime(time.time())[1]
day       = time.localtime(time.time())[2]
weekday   = calendar.weekday(year,month,day)
dayMon    = str(day) + "." + str(month)
date      = dayMon + "." + str(year)
output    = ""


if (output != ""):
  subprocess.call(["zenity","--info","--text",output])

