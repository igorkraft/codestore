#!/bin/bash

# Einstellungen für den Drucker
# -----------------------------
# + für vierseitige Bilder (nach Scriptkonvertierung)
# - an Rahmen anpassen individuell testen (prüfen, ob Skalierung Auswirkungen hat)
# - Normal (schnell)
# - langsamtrocknend
# - Graustufen
# + für PDF (vorher vierfach in Datei gedruckt)
# - Normal (schnell)
# - langsamtrocknend
# - Graustufen
# - Skalierung 104%

convertPdf()
{
  input="./Modul.pdf"
  TMP="tmp"
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
    return 1
  fi
  borderHole=`genius --exec=round\(\(3*sqrt\(2\)/21.0\)*$height\)`
  mkdir $TMP 2>/dev/null
  count=`identify -format %n "$input"`
  convert -size $borderRight"x"$height xc:white $TMP/borderRight.png
  convert -size $borderHole"x"$height xc:white $TMP/borderHole.png
  page=0
  date
  while [ $page -lt $count ]
  do
    convert -flatten +matte -density $density "$input"[$page] $TMP/tmp1.png &>/dev/null
    convert -flatten +matte -density $density "$input"[$(($page+1))] $TMP/tmp2.png &>/dev/null
    convert $TMP/tmp2.png -stroke black -strokewidth 4 -draw "line 0,0 9000,0" $TMP/tmp2.png
    convert -flatten +matte -density $density "$input"[$(($page+2))] $TMP/tmp3.png &>/dev/null
    convert -flatten +matte -density $density "$input"[$(($page+3))] $TMP/tmp4.png &>/dev/null
    convert $TMP/tmp4.png -stroke black -strokewidth 4 -draw "line 0,0 9000,0" $TMP/tmp4.png
    convert $TMP/tmp1.png -crop $width"x"$height"+"$evenLeft"+"$top $TMP/tmp1.png
    convert $TMP/tmp2.png -crop $width"x"$height"+"$unevenLeft"+"$top $TMP/tmp2.png
    convert $TMP/tmp3.png -crop $width"x"$height"+"$evenLeft"+"$top $TMP/tmp3.png
    convert $TMP/tmp4.png -crop $width"x"$height"+"$unevenLeft"+"$top $TMP/tmp4.png
    if [ $((($page/4)%2)) -eq 0 ]
    then
        montage -geometry +0+0 -tile 5x2 \
        $TMP/borderHole.png $TMP/tmp1.png $TMP/borderRight.png $TMP/tmp2.png $TMP/borderRight.png \
        $TMP/borderHole.png $TMP/tmp3.png $TMP/borderRight.png $TMP/tmp4.png $TMP/borderRight.png \
        $TMP/result_`printf "%03d" $(($page/4))`.png
    else
        montage -geometry +0+0 -tile 5x2 \
        $TMP/tmp1.png $TMP/borderRight.png $TMP/tmp2.png $TMP/borderRight.png $TMP/borderHole.png \
        $TMP/tmp3.png $TMP/borderRight.png $TMP/tmp4.png $TMP/borderRight.png $TMP/borderHole.png \
        $TMP/result_`printf "%03d" $(($page/4))`.png
    fi
    echo `printf "%03d" $(($page/4))` von `printf "%03d" $(($count/4))` fertig
    page=$(($page+4))
  done
  rm $TMP/tmp1.png
  rm $TMP/tmp2.png
  rm $TMP/tmp3.png
  rm $TMP/tmp4.png
  rm $TMP/borderHole.png
  rm $TMP/borderRight.png
}
date
convertPdf
