# xmlparser

#### This project is intended to multithread processing on xml files of certaing structure.

The input format of xml file is:

&lt;Entry&gt;

&lt;content&gt;some content up to 1024 characters&lt;/content&gt;

&lt;creationDate&gt;2014-01-01 00:00:00&lt;/creationDate&gt;

&lt;/Entry&gt;

Steps of processing files:

1. Reading xml files from configurable directory
2. Mapping files to Java objects. Broken files are moved to separate output directory
3. Persistance of Java enteties in postgresql database. Scripts to create db and table are included
4. Move processed files to output directory

Pay attention that processing of files are multithreaded and is sceduled with time interval.
