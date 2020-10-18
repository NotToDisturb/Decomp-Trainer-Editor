# Decomp Trainer Editor
DTE allows you to modify every trainer's properties (and there are around 850 of them), as well as their Pokémon parties dynamically. What does this mean? You can add new items, Pokémon or moves to your repo and the tool shouldn't have any problem loading them, as long as you keep the few things that the tool uses as a base for loading everything. This will be further explained later.

## Functionalities
![Trainer list](https://i.imgur.com/mg6ORE4.png)

Trainer searching: Shown trainers are filtered from text in the field below the list

![General trainer editing](https://i.imgur.com/EAP3Hfe.png)![enter image description here](https://i.imgur.com/bJQDbaP.png)

Trainer editing:
 - Names: Named displayed ingame (no internal name editing as of yet)
 - Trainer class: Changes the class text shown ingame and victory money gains
 - Toggle double battles
 - Items
 - Trainer pic: Changes the image displayed ingame
 - Gender
 - Music
 - AI:  Changes the trainer's behaviour. Includes some not targeted towards trainers.

![enter image description here](https://i.imgur.com/fJ4bhnJ.png)

Party editing:
 - Addition and deletion of party members: Up to a maximum of `PARTY_MAX` and a minimum of 1
 - Party member order
 - General IV count
 - Level
 - Species
 - Held item
 - Moves: If one of the Pokémon in the party has custom moves, it is required that the rest have them too or otherwise their moveset will be empty


## Named constants and limitations
Some constant values have been made into named constants and placed in `MainActivity.java` to allow for an easier modification when needed. These are:
 - `ITEMS_MAX`: Maximum amount of items owned by a trainer. Changes the amount of comboboxes shown for items.
 - `PARTY_MAX`: Maximum number of party members. Changes the size of the party members list.
 - `NAME_MAX`: Maximum length of a trainer name. Allows for more longer text in the name field.
 - `MOVES_MAX` Maximum number of moves a party member can have. Changes the amount of comboboxes shown for moves.

Even though the editor is dynamic, it's only so to an extent. Here's what the editor will recognize:
 - Addition or removal of Pokémon (works with Egg's branches)
 - Addition or removal of items (works with Egg's branches)
 - Addition or removal of moves (works with Egg's branches)
 - Addition or removal of trainer pics
 - Addition or removal of trainer music
 - Addition or removal of trainer classes
 - Addition or removal of AI flags

The problem comes when we look at trainer and party structures, which are hard coded as of now and will only recognise the following fields (use CTRL + F to find if a field is recognised):
 - Trainers: "partyFlags", "trainerClass", "encounterMusic_gender", "trainerPic", "trainerName",  
  "items", "doubleBattle", "aiFlags", "partySize", "party"
  - Parties: "iv", "lvl", "species", "heldItem", "moves"

Adding new fields won't result in an error, but saving will ignore them and will only store the default values. 