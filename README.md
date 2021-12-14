# En Passant :chess_pawn:

## Set Up
To be able to use this chess app you just need to follow a couple of single steps.
First start by downloading this repository!

### MySQL Database
For the backend to properly work, please run in your MySQL database the following script:
```
CREATE DATABASE chess;
USE chess;
CREATE USER 'ironhacker'@'localhost' IDENTIFIED BY '1r0nh4ck3r';
GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
FLUSH PRIVILEGES;
```

### Back End
To start the backend please run the following services:
- gateway
- discovery
- edge-service
- game-service
- move-service
- draw-service

### Front Ent
Almost done setting up everything. To start the frontend in a terminal change the current directory to the folder ``` frontend/chess-frontend ``` and run the following lines:
```
npm i
ng serve
```

## Project Architecture
<img alt="diagram" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/diagram.svg">

## Chess App Look
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/home-page.png">
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/game.png">
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/game-list.png">
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/settings.png">

## Available options
### Create Game
Any player can create a game through the **Create Game** button and has the option to select which color to play with (or a random color).
The player who created the game now has a password to provide to a friend so that he can join the game.

### Join Game
A player can join a game by pressing the **Join Game** button and providing a valid password. 
When testing please use 2 different browsers or one of the browsers in incognito mode as some information is stored in the local storage, which prevents it to work when playing in the same browser.

### View Currently Active Games
A player can view a list of the currently open games and press one to rejoin through the **Games Started** button. Also, if the player created the game he has the option to delete this game.
At any moment in time a player can only have 10 active games (this includes games joined and created).

### View Finished Games
A player can view a list of all finished games through the button **Finished Games**. When pressed the player can review the game.

### Settings
A player can change the board color and the set of pieces in the settings page.
