@echo off

echo Are you sure? Installing will overwrite any existing files. Be careful.

set /p pathW=Copy and paste here the path to your shared folder: 
echo %pathW%

echo f|xcopy resources\shell.db %pathW%\wfmgr\bin\shell.db /Y
echo f|xcopy resources\wfmgr.jar %pathW%\wfmgr\bin\wfmgr.jar /Y
echo f|xcopy resources\commands.sql %pathW%\wfmgr\bin\commands.sql /Y
echo f|xcopy resources\cleandb.bat %pathW%\wfmgr\bin\cleandb.bat /Y
echo f|xcopy resources\wfmgr.bat %pathW%\wfmgr\bin\wfmgr.bat /Y
echo f|xcopy resources\shortcut.bat %pathW%\wfmgr\shortcut.bat /Y
echo f|xcopy resources\wfmgr.ico %pathW%\wfmgr\wfmgr.ico /Y

cd %pathW%\wfmgr\bin

powershell "$s=(New-Object -COM WScript.Shell).CreateShortcut('%userprofile%\Desktop\WorkFlow Manager.lnk');$s.WorkingDirectory ='%pathW%\wfmgr\bin';$s.TargetPath='%pathW%\wfmgr\bin\wfmgr.jar';$s.IconLocation ='%pathW%\wfmgr\wfmgr.ico';$s.Save()"
cleandb.bat /Y


echo Installation Done. You may now delete this folder

pause