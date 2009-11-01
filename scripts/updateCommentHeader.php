<?php

$pfad = "/home/hype/Stuff/RANDI2/workspace/randi2/src/main/java/de/randi2";
$filter = "java";
$header = "/* 
 * (c) 2008-${year} RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */";

function recursive_dirscan($dir,$ext,$text)
{
  $dirlist = opendir($dir);

  $files = array();

  while ($file = readdir ($dirlist))
  {
    if ($file != '.' && $file != '..')
    {
      $newpath = $dir.'/'.$file;
      $level = explode('/',$newpath);
      if (is_dir($newpath))
      {
          recursive_dirscan($newpath,$ext,$text);
      }

      $aExt = end(explode(".",$newpath));
      if ($aExt == $ext) { // find java

        echo $newpath."\r\n";

        $handle = fopen($newpath, 'r');
        $Data = fread($handle, filesize($newpath));
        fclose($handle); 

        $commentStart = substr($text,0,2);
        $commentEnd = substr($text,-2,strlen($text));

        if (substr($Data,0,2)==$commentStart) {

            // Header existiert schon!

            $positionEnde = strpos($Data,$commentEnd)+strlen($commentEnd);

            echo substr($Data,0,2)."-".$commentEnd." Header exists: 0-".$positionEnde."\r\n\r\n";

            // strip header
            $Data_new = substr_replace($Data,$text,0,$positionEnde);

            echo "ALTER HEADER: \r\n"+substr($Data,0,strlen($text)+100)."\r\n-------------\r\n";

        } else {

            // Header existiert noch nicht!
            $Data_new = $text."\r\n".$Data;

        }

            // Aenderungen anzeigen
            echo "NEUER HEADER: \r\n".substr($Data_new,0,strlen($text)+100)."\r\n\r\n\r\n";

            // Aenderungen speichern
            $handle = fopen($newpath, 'w');
            fwrite($handle, $Data_new);
            fclose($handle); 


      }

    }
  }
  closedir($dirlist);
  return true;
}

echo "running..";
recursive_dirscan($pfad,$filter,$header);

?>
