#! /bin/bash
SITE=`wget -qO- "http://www.wolframalpha.com/input/?i=time"`
RESULT_POS=`awk -v a="$SITE" -v b="Result:" 'BEGIN{print index(a,b)}' 2> /dev/null`
RESULT=`expr substr "${SITE}" $RESULT_POS 100000`
ALT_POS=`awk -v a="$RESULT" -v b="alt=" 'BEGIN{print index(a,b)}' 2> /dev/null`
ALT=`expr substr "${RESULT}" $ALT_POS 100`
TIME_END_POS=`awk -v a="$ALT" -v b="m " 'BEGIN{print index(a,b)}' 2> /dev/null`
TIME_END_POS=$(($TIME_END_POS-5))
TIME=`expr substr "${ALT}" 6 $TIME_END_POS`
#TIME=`awk -v a="$TIME" 'BEGIN{gsub(" ","",a);print a}'`
date +%T\ %P -s "$TIME"
