The chess pieces are in their Unicode format for a more immersive viewing. To be able to correctly view Unicode characters, you may need to change the code page to appear in UTF-8 encoded form, to do so:

1. In the VSCode integrated terminal, enter the command: chcp 65001

Then run the program again, it should show the symbols correctly.

2. (Windows) To set the codepage to appear in UTF-8 encoded form by default, you must:

Start -> Run -> regedit

At [HKEY_LOCAL_MACHINE\Software\Microsoft\Command Processor\Autorun]

Change the value to @chcp 65001>nul

If Autorun is not present, you can add a New > String Value

With that, the next time you run the program, it should already show the chessboard with the correctly configured pieces. Enjoy!