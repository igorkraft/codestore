sed -e "s/ä/\&auml;/g" $1 | \
sed -e "s/ö/\&ouml;/g"  | \
sed -e "s/ü/\&uuml;/g"  | \
sed -e "s/Ä/\&Auml;/g"  | \
sed -e "s/Ö/\&Ouml;/g"  | \
sed -e "s/Ü/\&Uuml;/g"  | \
sed -e "s/ß/\&szlig;/g"   \
 > "$1_umlaute.html"
