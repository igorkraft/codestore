# ausführen mit
# date; while true; do sh Desktop/sound_test.sh; sleep 10; done > /dev/null

# Zeiten in Minuten
# 17; 5; 20; 4; 1; 2; 12; 

speaker-test -c 2 -r 48000 -l 1 -D hw:0,3 ; \
speaker-test -c 2 -r 48000 -l 1 -D hw:0,3 ; \
speaker-test -c 2 -r 48000 -l 1 -D hw:0,3 ; \
speaker-test -c 2 -r 48000 -l 1 -D hw:0,7 ; \
speaker-test -c 2 -r 48000 -l 1 -D hw:0,8 ; \
speaker-test -c 2 -r 48000 -l 1 -D hw:0,9 ; \
speaker-test -c 2 -r 48000 -l 1 -D hw:0,10
