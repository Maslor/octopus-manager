@echo off

echo Are you sure? Installing will overwrite any existing files. Be careful.

set /p pathW=Copy and paste here the path to your shared folder: 
echo %pathW%

echo f|xcopy resources\shell.db %pathW%\octopus-manager\bin\shell.db /Y
echo f|xcopy resources\wfmgr.jar %pathW%\octopus-manager\bin\wfmgr.jar /Y
echo f|xcopy resources\commands.sql %pathW%\octopus-manager\bin\commands.sql /Y
echo f|xcopy resources\cleandb.bat %pathW%\octopus-manager\bin\cleandb.bat /Y
echo f|xcopy resources\wfmgr.bat %pathW%\octopus-manager\bin\wfmgr.bat /Y
echo f|xcopy resources\shortcut.bat %pathW%\octopus-manager\shortcut.bat /Y
echo f|xcopy resources\wfmgr.ico %pathW%\octopus-manager\wfmgr.ico /Y

cd %pathW%\octopus-manager\bin

powershell "$s=(New-Object -COM WScript.Shell).CreateShortcut('%userprofile%\Desktop\Octopus Manager.lnk');$s.WorkingDirectory ='%pathW%\octopus-manager\bin';$s.TargetPath='%pathW%\octopus-manager\bin\wfmgr.jar';$s.IconLocation ='%pathW%\octopus-manager\wfmgr.ico';$s.Save()"
cleandb.bat /Y


echo Installation Done. You may now delete this folder

pause