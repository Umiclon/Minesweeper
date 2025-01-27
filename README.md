# Minesweeper Game

## Project Proposal

- What will the application do?
  - *Minesweeper is a popular puzzle solving game from the 1960s. It involves a board with hidden mines. The goal is to 
  clear all the boxes without uncovering any mines. Clicking on the boxes on the board will show the number of mines 
  in the surrounding 8 boxes.*
  
- Who will use it?
  - *This game is for anyone who wants to play a casual game.*
  
- Why is this project of interest to you?
  - *In secondary school, a couple of my friends played minesweeper, so I also played with them. Personally, I really 
  enjoy these types of simple puzzle games that I can never get bored with. Whenever I'm sitting on the bus, I would 
  play a game or two of minesweeper.*

## User Stories

*As a user, I want to be able to...*

  - *reveal a box on the board*
  - *flag and un-flag a box on the board*
  - *choose the size of the board*
  - *add a score to the scoreboard*
  - *have the option to load when starting the game*   
  - *save the game when quitting*
       
## Instructions for Grader
 
  - *You can scroll to view the whole ScoreBoard*
  - *You can click on boxes, and your score will be added to ScoreBoard when you win or lose*
  - *You can trigger my visual component by right clicking on a covered box or left clicking on a hidden mine*
  - *You can save the state of the game by clicking on the save button*
  - *You can load the state of the game by clicking on the load button*
  - *You can load a new game by clicking on the reset button*
  
# Phase 4: Task 2
  
  - *In the persistence package, the classes FileLoader and FileSaver are robust*
  - *In the model package, the subclasses Block and Mines extend the superclass Box*
  
## Phase 4: Task 3
    
  - *Improved coupling in the GamePanel class by...*
     - *creating a new method endGame which is called by both winGame and gameOver*
     - *creating a new method updateButton which is called by updateButtons, reset, and printBoardSolution*
     - *combined similar methods (flagMine + flagBlock and unFlagMine + unFlagBlock)*
     - *using an int size as the length of the Box array and Container grid*
  