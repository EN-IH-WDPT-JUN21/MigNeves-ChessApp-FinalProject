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

## Available 
## Chess App Look
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/home-page.png">
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/settings.png">
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/game-list.png">
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/MigNeves-ChessApp-FinalProject/blob/main/game.png">
