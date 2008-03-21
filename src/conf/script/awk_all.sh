#! /bin/bash
# Dieses Skript fuer das AWK-Kommando aus der uebergebenen Konfigurationsdatei
# auf alle *.java Dateien im Projekt aus. Achtung, bitte nur auf UNIX-Systemen
# ausfuehren und auch nur dann, wenn ihr genau wisst, was ihr tut!
# set -x

# Eingabe
FILE=$1 # NAME des AWK FILEs
let RUN=$2+0 # wenn der PARAMETER 0 oder leer ist, wird ein DRY-Run durchgefuert.

# Erstmal feststellen auf welchem PC wir sind, damit wir den Workspace 
# Festlegen koennen.
HOST=$(uname -n)
if test $HOST == 'victoria-i'; then
	WORKSPACE='/home/jthoenes/programming/java_wpc/RANDI2'
else
	echo 'Dein Workspace ist nicht im Skript eingetragen. Skript wird beendet!';
	exit 1;
fi;

# Existiert das awk-File
#if test -f "$FILE";
#then
#	echo 'Verarbeite ' $FILE;
#else
#	echo 'Die angebebene AWK Datei ' $FILE  'existiert nicht. Skript wird beendet!';
#	exit 1;
#fi;

# Sucht alle *.java Dateien im $1 uebergebenen Verzeichnis
# unter Unterverzeichnissen und fuehrt das akw Kommando aus.
function run_awk {
	for f in $(ls $1); do
		# Ordner
		if test -d $1/$f;
		then
			# Rekursion
			run_awk $1/$f;
		fi;
		
		
		
	done

}

# Fuehrt das awk Kommando auf die uebergebene Datei aus.
function do_awk {
	#if test 1 -eq $RUN; 
	cat /home/jthoenes/programming/java_wpc/RANDI2/gpl.java $1 > $1.tmp;
}

run_awk $WORKSPACE/src/de;
