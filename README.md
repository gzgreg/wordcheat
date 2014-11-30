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

`analyze` analyzes the current board given.

`sort` sorts the dictionary. Can be sorted by height, length, and width (use `sort height`).

`how` shows the spots taken for a specific word given in the argument.
