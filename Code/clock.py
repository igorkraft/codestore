#!/usr/bin/python
#coding=UTF-8

import sys
import time
import calendar
import subprocess
import os
import math

imgLoc="/media/Daten/Bilder/"
oldHour = 0.0
year    = 0
month   = 1
day     = 2
hour    = 3
minute  = 4

# description of dim array:
# 0 vertikaler Abstand vom oberen Rand des Ziffernblattes zum Drehpunkt
# 1 horizontaler Abstand vom linken Rand des Ziffernblattes zum Drehpunkt
# 2 vertikaler Abstand vom oberen Rand der Zeigergrafik zum Zeigerdrehpunkt
# 3 horizontaler Abstand vom linken Rand der Zeigergrafik zum Zeigerdrehpunkt
# 4 Hoehe der Zeigergrafik
# 5 Breite der Zeigergrafik
def setPointer(pointer,bg,dest,alpha,dim):
  sinAlpha = math.sin(alpha)
  cosAlpha = math.cos(alpha)
  if (alpha <= 0.5 * math.pi):
    left = int(round(dim[1]-dim[3]*cosAlpha-(dim[4]-dim[2])*sinAlpha))
    top  = int(round(dim[0]-dim[2]*cosAlpha-dim[3]*sinAlpha))
  if (alpha > 0.5 * math.pi and alpha <= math.pi):
    left = int(round(dim[1]+(dim[5]-dim[3])*cosAlpha-(dim[4]-dim[2])*sinAlpha))
    top  = int(round(dim[0]+(dim[4]-dim[2])*cosAlpha-dim[3]*sinAlpha))
  if (alpha > math.pi and alpha <= 1.5 * math.pi):
    left = int(round(dim[1]+(dim[5]-dim[3])*cosAlpha+dim[2]*sinAlpha))
    top  = int(round(dim[0]+(dim[4]-dim[2])*cosAlpha+(dim[5]-dim[3])*sinAlpha))
  if (alpha > 1.5 * math.pi):
    left = int(round(dim[1]-dim[3]*cosAlpha+dim[2]*sinAlpha))
    top  = int(round(dim[0]-dim[2]*cosAlpha+(dim[5]-dim[3])*sinAlpha))
  subprocess.call(["convert",pointer,"-virtual-pixel","Transparent","-affine",str(cosAlpha)+","+str(sinAlpha)+","+str(-sinAlpha)+","+str(cosAlpha)+",0,0","-transform","/media/clock/pointer_tmp.png"])
  subprocess.call(["composite","-geometry","+"+str(left)+"+"+str(top)+"","/media/clock/pointer_tmp.png",bg,dest])
  return

while True:
  curTime = time.localtime(time.time())
  curHour = ((curTime[hour] % 12) + curTime[minute] / 60.0)
  if (curHour != oldHour):
    oldHour = curHour
    weekday = calendar.weekday(curTime[year],curTime[month],curTime[day])
    setPointer(imgLoc+"minute.png",imgLoc+"clock.png","/media/clock/clock_1.png",2 * math.pi * curTime[minute] / 60.0,[65.0,65.0,40.0,7.0,61.0,12.0])
    setPointer(imgLoc+"hour.png","/media/clock/clock_1.png","/media/clock/clock_2.png",2 * math.pi * curHour / 12.0,[65.0,65.0,30.0,7.0,39.0,12.0])
    subprocess.call(["composite","-geometry","+1150+30","/media/clock/clock_2.png",imgLoc+"Bender.png","/media/clock/bg.png"])
  time.sleep(10)
