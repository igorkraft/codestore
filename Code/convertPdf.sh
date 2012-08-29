#!/bin/bash

# Einstellungen für den Drucker
# -----------------------------
# + für vierseitige Bilder (nach Scriptkonvertierung)
#   - an Rahmen anpassen individuell testen (prüfen, ob Skalierung Auswirkungen hat)
#   - Normal (schnell)
#   - langsamtrocknend
#   - Graustufen
# + für PDF (vorher vierfach in Datei gedruckt)
#   - Normal (schnell)
#   - langsamtrocknend
#   - Graustufen
#   - Skalierung 104%

convertPdf()
{
  input="./merge.pdf"
  density=368
  margin=2 # Seitenrand zum lochen (Angabe in cm, floats sind erlaubt)
  evenLeft=355 # gibt an, wie viel Rand bei geraden Seiten abgeschnitten werden soll
  unevenLeft=355 # gibt an, wie viel Rand bei ungeraden Seiten abgeschnitten werden soll
  top=146
  width=2488
  height=4000
  # h=(sqrt(2)*$width)/(1-$margin/21)
  borderRight=`genius --exec=round\(\(\(1-$margin/21.0\)*sqrt\(2\)*$height-2*$width\)/2\)`
  if [ $borderRight -lt 0 ]
    then 
      echo "borderRight < 0! (($borderRight))"
      exit
  fi
  borderHole=`genius --exec=round\(\(3*sqrt\(2\)/21.0\)*$height\)`
  mkdir test 2>/dev/null
  count=`identify -format %n "$input"`
  convert -size $borderRight"x"$height xc:white test/borderRight.png
  convert -size $borderHole"x"$height xc:white test/borderHole.png
  page=0
  date > start_date.txt
  while [ $page -lt $count ]
  do
    convert -flatten +matte -density $density "$input"[$page] test/tmp1.png &>/dev/null
    convert -flatten +matte -density $density "$input"[$(($page+1))] test/tmp2.png &>/dev/null
    convert -flatten +matte -density $density "$input"[$(($page+2))] test/tmp3.png &>/dev/null
    convert -flatten +matte -density $density "$input"[$(($page+3))] test/tmp4.png &>/dev/null
    convert test/tmp1.png -crop $width"x"$height"+"$evenLeft"+"$top test/tmp1.png
    convert test/tmp2.png -crop $width"x"$height"+"$unevenLeft"+"$top test/tmp2.png
    convert test/tmp3.png -crop $width"x"$height"+"$evenLeft"+"$top test/tmp3.png
    convert test/tmp4.png -crop $width"x"$height"+"$unevenLeft"+"$top test/tmp4.png
    if [ $((($page/4)%2)) -eq 0 ]
    then
      montage -geometry +0+0 -tile 5x2 \
        test/borderHole.png test/tmp1.png test/borderRight.png test/tmp2.png test/borderRight.png \
        test/borderHole.png test/tmp3.png test/borderRight.png test/tmp4.png test/borderRight.png \
        test/result_`printf "%03d" $(($page/4))`.png
    else
      montage -geometry +0+0 -tile 5x2 \
        test/tmp1.png test/borderRight.png test/tmp2.png test/borderRight.png test/borderHole.png \
        test/tmp3.png test/borderRight.png test/tmp4.png test/borderRight.png test/borderHole.png \
        test/result_`printf "%03d" $(($page/4))`.png
    fi
    echo `printf "%03d" $(($page/4))` von `printf "%03d" $(($count/4))` fertig
    page=$(($page+4))
  done
  rm test/tmp1.png
  rm test/tmp2.png
  rm test/tmp3.png
  rm test/tmp4.png
  rm test/borderHole.png
  rm test/borderRight.png
  date > end_date.txt
}
convertPdf
