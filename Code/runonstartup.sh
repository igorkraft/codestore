
cmdline=$(dmesg | grep 'Kernel command line:')

#expr index "$cmdline" 'runon'
zenity --info --text $cmdline
