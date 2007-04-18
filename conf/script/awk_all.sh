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
	WORKSPACE='/home/jthoenes/programming/workspace2/RANDI2'
else
	echo 'Dein Workspace ist nicht im Skript eingetragen. Skript wird beendet!';
	exit 1;
fi;

# Existiert das awk-File
if test -f "$FILE";
then
	echo 'Verarbeite ' $FILE;
else
	echo 'Die angebebene AWK Datei ' $FILE  'existiert nicht. Skript wird beendet!';
	exit 1;
fi;

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
		
		# Java Dateien
		if test -f $1/$f;
		then
			if [[ "$f" =~ '.*\.java' ]];
				then
				# AWK Aufrufen
				do_awk $1/$f;
			fi;
		fi;
	done

}

# Fuehrt das awk Kommando auf die uebergebene Datei aus.
function do_awk {
	if test 1 -eq $RUN; 
	then
		# Ich weiss nicht, woran es liegt, aber der Umweg ist notwendig
		awk -f $FILE $1 > $1.tmp; 	
		cp $1.tmp $1;
		rm $1.tmp;
	else
		# Aenderungen an der Datei anzeigen
		echo $1 ":"
		awk -f $FILE $1 > $1.tmp;
		diff $1 $1.tmp;
		rm $1.tmp;
		echo "\n ---- \n"
	fi;
}

run_awk $WORKSPACE/src;
