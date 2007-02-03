log4j.rootLogger=OFF

# Allgemeines Debug Log
log4j.logger.de.randi2=ALL, DebugTXT

# TODO: Auf der Konsole werden die folgenden Infos angezeigt
# Jenachdem woran gearbeitet wird, sollten hier die Klassen/
# Packages angepasst werden
log4j.logger.de.randi2.utility=ALL, Konsole, ArbeitDebugTXT

# Anwendungslog
log4j.logger.Randi2=ALL, AnwendungXML, Konsole

# Tomcat
log4j.logger.org.apache=INFO, ServerTXT


######################################################
log4j.appender.AnwendungXML=org.apache.log4j.RollingFileAppender
log4j.appender.AnwendungXML.MaxFileSize=100KB
log4j.appender.AnwendungXML.layout=de.randi2.utility.LogLayout

log4j.appender.Konsole=org.apache.log4j.ConsoleAppender
log4j.appender.Konsole.layout=org.apache.log4j.PatternLayout
log4j.appender.Konsole.layout.ConversionPattern=[%t] %d{ISO8601} [%p] %c:\n  %m%n

log4j.appender.DebugTXT=org.apache.log4j.RollingFileAppender
log4j.appender.DebugTXT.MaxFileSize=100KB
log4j.appender.DebugTXT.layout=org.apache.log4j.PatternLayout
log4j.appender.DebugTXT.layout.ConversionPattern=[%t] %d{ISO8601} [%p] %c:  %m%n

log4j.appender.ArbeitDebugTXT=org.apache.log4j.RollingFileAppender
log4j.appender.ArbeitDebugTXT.MaxFileSize=100KB
log4j.appender.ArbeitDebugTXT.layout=org.apache.log4j.PatternLayout
log4j.appender.ArbeitDebugTXT.layout.ConversionPattern=[%t] %d{ISO8601} [%p] %c:  %m%n

log4j.appender.ServerTXT=org.apache.log4j.RollingFileAppender
log4j.appender.ServerTXT.MaxFileSize=100KB
log4j.appender.ServerTXT.layout=org.apache.log4j.PatternLayout
log4j.appender.ServerTXT.layout.ConversionPattern=[%p] %d{ISO8601}:  %m%n


### TODO: Dateipfade zum Tocat logs Verzeichnis anpassen ##
log4j.appender.AnwendungXML.File=/home/jthoenes/tmp/java/randi2/anwendung_log.xml
log4j.appender.DebugTXT.File=/home/jthoenes/tmp/java/randi2/entwicklung_allgemein.log
log4j.appender.ArbeitDebugTXT.File=/home/jthoenes/tmp/java/randi2/entwicklung_arbeit.log
log4j.appender.ServerTXT.File=/opt/tomcat/logs/server.log
