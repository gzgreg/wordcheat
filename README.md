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

`sort` sorts the word list. Can be sorted by height, length, width, top, and bottom (use `sort height`).

`how` shows the spots taken for a specific word given in the argument.

`get` takes two numerical arguments (use `get 0 50`), and shows the words in the current sorted order from the first argument to the second argument (the 1st to 51st words in this case).
