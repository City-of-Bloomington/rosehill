@echo off
if ""%1""=="""" goto usage
copy .\build\WEB-INF\classes\%1\*.class .\WEB-INf\classes\%1\.
goto done
:usage 
echo " need to pass an argument to cpc "
:done
