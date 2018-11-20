@ECHO OFF
ECHO Please keep the all updates file settings in common.properties
ECHO Make sure keep all jar files in lib folder
ECHO ...

Pause...Press Any Key...
SET /P ANTFILE=ENTER FULL PATH FOR ANT FILE (like C:\workspace\Gurukula): 
ECHO ANTFILE=%ANTFILE%
ECHO ...

SET /P LIBFILE=ENTER FULL PATH FOR LIB FOLDER (like C:\workspace\lib): 
ECHO LIBFILE=%LIBFILE%
ECHO ...

SET /P THREADSIZE=ENTER THREADSIZE (like 2): 

SET /P TESTNGXMLFILE=ENTER FULL PATH FOR TESTNGXMLFILES BASED ON THREAD SIZE (like C:\workspace\Gurukula\resources\input\xml\system1.xml C:\workspace\Gurukula\resources\input\xml\system1.xml): 
ECHO TESTNGXMLFILE=%TESTNGXMLFILE%
ECHO ...

C: && cd C:\workspace\Gurukula && ant -buildfile build.xml
c: && cd C:\workspace\Gurukula\lib && java -cp testng-6.8.6beta.jar;test.jar;eclipselink.jar;jxl.jar;log4j-1.2.15.jar;selenium-server-standalone-2.48.2.jar;C:\workspace\Gurukula\conf\; org.testng.TestNG -suitethreadpoolsize 2 C:\workspace\Gurukula\resources\input\xml\system1.xml C:\workspace\Gurukula\resources\input\xml\system2.xml
exit