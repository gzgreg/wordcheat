# WordCheat
### Finds long words for WordBase

Run the jarfile in the /bin folder.

###Commands:

`buildDict` builds a dictionary from the provided words.txt file. A custom dictionary can also be provided with a filepath as an argument (must have words separated by newlines).

`import` imports the board at the filepath given in the argument (`import board.txt`). Boards are formatted as follows:
```
abcdefghij
asdblkajsh
awkeuhfiuh
bkhxkjhkjr
fdkbhawbrg
suhiawuhuh
ALKJDHFKJH
...
```
Capitals denote letters under your control.

`analyze` analyzes the current board given, and gives a list of possible words from the position given.

`sort` sorts the word list. Can be sorted by height, length, width, top, bottom, and newletters (use `sort height`).

`how` shows the spots taken for a specific word given in the argument.

`get` takes two numerical arguments (use `get 0 50`), and shows the words in the current sorted order from the first argument to the second argument (the 1st to 51st words in this case).

`win` searches for winning words. Determines which side of the board you are on based on capitalized letters, so input board must be valid for this to work properly.

`winFrom` shows a list of spots that will allow you to win the game on the next move.

`opponentWinFrom` shows a list of spots that will allow the opponent to win in one move.

`add` adds a word to the area you control (equivalent to capitalizing). Allows you to pick a particular configuration of the word. This doesn't re-analyze the board.
