# octopus-manager :octopus::necktie: [![Build Status](https://travis-ci.org/Maslor/octopus-manager.svg?branch=master)](https://travis-ci.org/Maslor/octopus-manager) [![Codacy Badge](https://api.codacy.com/project/badge/1a7058ed6c0244109384abae75396aa0)](https://www.codacy.com/app/gabriel-freire/octopus-manager)
Tracks tasks' progression and generates an audit trail of employees' actions. :eyes:

## Windows 

### SetUp and Run
Steps:

1.  Clone or download this project .

2.  Open the `SetUp` folder.

3.  Installing
    1. First time setup:
      1. Run `install.bat` and select the shared folder where you want to install this application. Notice that a Desktop shortcut is created. 
      
    2. Setting it up in other computers connected to the network:
      1. Go to the folder where you installed octopus-manager and run `shortcut.bat`. This will create a shortcut on the current computer's desktop that connects to the previously installed octopus-manager.
      
4.  Double click the shortcut to run the program.

## Mac

*Ongoing - I still have to translate all the .bat windows script to .sh*

1.  Clone or download this project .
2.  Copy contents of the `SetUp` folder to the selected shared folder.
3.  Run the wfmgr.jar on the terminal with the command `java -jar wfmgr.jar`
