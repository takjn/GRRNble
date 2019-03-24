// game settings
#define BOARD_COLS 8    // 8 columns
#define BOARD_ROWS 16   // 16 rows
#define DROP_INTERVAL 500  // interval(millisec)

int board[BOARD_ROWS][BOARD_COLS];
int block_shapes[7][16] = {
    { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
};

int block_current[4][4];  // shape of current block
int block_x, block_y;     // position of current block

long timer_lastmills = 0;

boolean gameover;

void startGame(void) {
  // setup
  oled.set1X();
  oled.setFont(BM_tube9x8);
  randomSeed(analogReadWithClockControl(5));

  // start new game
  newGame();

  // game loop
  while (!gameover) {
    gameKeyHandler();

    if (DROP_INTERVAL < (millis() - timer_lastmills)) {
      timer_lastmills = millis();
      tick();
    }

    delay(5); 
  }
}

void newGame() {
  // board init
  for (int y=0;y<BOARD_ROWS;y++) {
    for (int x=0;x<BOARD_COLS;x++) {
      board[y][x] =0;
    }
  }
  
  timer_lastmills = 0;
  gameover = false; 
  
  nextBlock();
  redraw();
}

void nextBlock() {
  int id = random(0,7);
  int i = 0;
  for (int y=0;y<4;y++) {
    for (int x=0;x<4;x++) {
      block_current[y][x] = block_shapes[id][i++];
    }
  }
  
  block_x = 4;
  block_y = 0;
}

void tick() {
  // clear block shape
  drawBlockShape(' ');

  if (valid( 0, 1, block_current )) {
    block_y++;
    drawBlockShape('{');
  }
  else {
    freeze();
    clearLines();
    if (gameover) {
      // newGame();
      return;
    }

    nextBlock();
  }
}

boolean valid(int offsetX, int offsetY, int newCurrent[4][4]) {
  offsetX = block_x + offsetX;
  offsetY = block_y + offsetY;
  for (int y=0; y<4; y++) {
    for (int x=0; x<4; x++) {
      if ( newCurrent[ y ][ x ] ) {
        if (
          x + offsetX < 0 || y+offsetY >= BOARD_ROWS || x+offsetX >= BOARD_COLS ||
          board[y+offsetY][x+offsetX] == 1
        ) {
          if (offsetY == 1 &&  offsetX-block_x == 0 && offsetY-block_y == 1) {
            gameover = true;
          }
          return false;
        }
      }
    }
  }
  return true;
}

void freeze() {
  for (int y=0;y<4;y++) {
    for (int x=0;x<4;x++) {
      if (block_current[y][x] == 1) {
        board[y+block_y][x+block_x] = block_current[y][x];
      }
    }
  }
  redraw();
}

void clearLines() {
  for (int y = BOARD_ROWS-1; y>=0; y--) {
    boolean rowFilled = true;
    for (int x = 0;x<BOARD_COLS;x++) {
      if (board[y][x] == 0) {
        rowFilled = false;
        break;
      }
    }
    
    if (rowFilled) {
      for (int yy=y; yy>0; yy--) {
        for (int x=0; x<BOARD_COLS;x++) {
          board[yy][x] = board[yy-1][x];
        }
      }
      y++;

      beep();
      redraw();
      delay(50);
    }
  }
}

void redraw() { 
  // clear board
  oled.clear();

  // draw board
  for (int x=0;x<BOARD_COLS;x++) {
    for (int y=0;y<BOARD_ROWS;y++) {
      if (board[y][x] == 1) {
            drawBlock(x, y, '{');
      }
    }
  }

  // draw block shape
  drawBlockShape('{');
}

void drawBlock(int x, int y, char ch) {
  oled.setCursor(y*8, x);
  oled.write(ch);
}

void drawBlockShape(char ch) {
  for (int y=0;y<4;y++) {
    for (int x=0;x<4;x++) {
      if (block_current[y][x] == 1) {
        drawBlock(block_x+x, block_y+y, ch);
      }
    }
  }
}

void gameKeyHandler(void) {
  unsigned char key = key_read();
  if (key == KEY_NONE) {
    return;
  }
  
  // clear block shape
  drawBlockShape(' ');

  switch ( key ) {
    case KEY_NEXT:
      if (valid(1,0,block_current)) {
        block_x++;
      }
      break;
    case KEY_PREV:
      if (valid(-1,0,block_current)) {
        block_x--;
      }
      break;
    case KEY_SELECT:
      // TODO:bug
      int rotated[4][4];
      
      for (int y=0;y<4;y++) {
        for (int x=0;x<4;x++) {
          rotated[y][x] = block_current[3-x][y];
        }
      }
      
      if (valid(0, 0, rotated)) {
        for (int y=0;y<4;y++) {
          for (int x=0;x<4;x++) {
            block_current[y][x] = rotated[y][x];
          }
        }
      }
      break;
  }
  drawBlockShape('{');
}