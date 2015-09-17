@echo off

echo f|xcopy shell.db shell_%date%.db /Y
sqlite3 shell.db < commands.sql
echo f|xcopy auditx.log audit_%date%.log /Y
if exist auditx.log (del auditx.log)
if exist audit.log (del audit.log)
if exist shell.txt (del shell.txt)


