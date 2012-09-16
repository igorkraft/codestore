sed -e "s/ü/\&uuml;/g" /home/user/Arbeitsfläche/test.md | \
sed -e "s/ä/\&auml;/g"  | \
sed -e "s/ö/\&ouml;/g"  | \
sed -e "s/Ä/\&Auml;/g"  | \
sed -e "s/Ö/\&Ouml;/g"  | \
sed -e "s/Ü/\&Uuml;/g"  | \
sed -e "s/ß/\&szlig;/g" | \
markdown > /home/user/Arbeitsfläche/test.html

