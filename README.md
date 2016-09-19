# xmlparser

#### This project is intended to multithread processing on xml files of certain structure.

The input format of xml file is:

&lt;Entry&gt;

&lt;content&gt;some content up to 1024 characters&lt;/content&gt;

&lt;creationDate&gt;2014-01-01 00:00:00&lt;/creationDate&gt;

&lt;/Entry&gt;

Steps of processing files:

1. Reading xml files from configurable directory
2. Mapping files to Java objects. Broken files are moved to separate output directory
3. Persistence of Java entities in postgresql database. Scripts to create db and table are included
4. Move processed files to output directory

Pay attention that processing of files are multithreaded and is scheduled with time interval.

## CONFIGURATION
1. Downloads the repository
2. Open it in IDE
3. Config hibernate.cfg.xml file. Project is using PostgreSQL database 9.4
 - specify: password - password for db
 - specify: url - full url to connect to db i.e. //jdbc:postgresql://localhost:5432/xmlparser
 - specify: username

4. Run create_entry_table_script.sql to create db and table using posgresql console.
5. In MultiThreadXMLProcessingService set the THREADS_NUMBER parameter to process the files
5. Create 3 directories for processing files (they have to be the same as in config.properties file)
 - copy test_bad_file.xml and test_good_file.xml to input directory
6. Set up config.properties   
 - check the directories set-up
 - set up the monitoring interval parameter (only integer values in minutes)
 7. Make type in console:
  - gradle clean
  - gradle xmlParserJar
  - gradle copyTask
 8. Starting the app.
  - navigate to build/libs/ directory
  - com.parser-all-1.0-all.jar is a jar with all dependencies
  - startXmlParserLinux.sh (sudo ./startXmlParserLinux.sh) - starts the app in terminal
  - startXmlParserWin.bat - start in Win
  - bat and sh files must be in the same directory as jar.
  - result files of running program is in output directories
  - to close the app simply close the terminal
  - logs are in the log directory.
  9. Have fun! :)
  
