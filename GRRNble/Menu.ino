int cursol = 0;
int prev_cursol = -1;
boolean changed = false;

#define CENTER_X 54
#define MARGIN_X 54
#define ANIMATION_STEPS 9

#define MENU_COUNT 5
#define MENU_TITLE "Setup"
const char *MENU_SUB_TITLE[MENU_COUNT] = {"BACK", "CONTRAST", "SOUND", "POWER SAVING", "DATE & TIME"};

void drawMenu(unsigned char key) {
  // return if idle
  if (key == KEY_NONE && cursol == prev_cursol) return;
  
  // key handler
  if (key == KEY_SELECT) {
    changed = true;
    if (cursol == 0) {
      // Time
      prev_cursol = -1;
      oled.clear();
      mode_current = MODE_TIME;
      return;
    } else if (cursol == 1) {
      // Contrast
      display_contrast++;
      if (display_contrast>3) display_contrast=0;
      oled.setContrast(DISPLAY_CONTRASTS[display_contrast]);
    } else if (cursol == 2) {
      // Sound
      buzzer_volume++;
      if (buzzer_volume>3) buzzer_volume=0;
    } else if (cursol == 3) {
      // Power Saving
      delay_sleep++;
      if (delay_sleep>3) delay_sleep = 0;
    } else if (cursol == 4) {
      // Date & Time
      prev_cursol = -1;
      oled.clear();
      mode_current = MODE_SETTIME;
      rtc_get_time(&datetime);
      return;
    }
  }
  else if (key == KEY_NEXT) {
    cursol++;
    if (cursol == MENU_COUNT) cursol = 0;
  }
  else if (key == KEY_PREV) {
    if (cursol < 1) {
      cursol = MENU_COUNT - 1;
    } else {
      cursol--;
    }
  }
  beep();
  
  // draw title and subtitle
  oled.setFont(BM_tube9x8);
  oled.set1X();
  oled.setCursor(0, 0);
  oled.print(MENU_TITLE);
  oled.clearToEOL();
  oled.setCursor(0, 7);
  oled.print(MENU_SUB_TITLE[cursol]);
  oled.clearToEOL();
  
  // draw contents
  oled.set2X();
  if (cursol != prev_cursol) {
    int dx = (cursol - prev_cursol) * MARGIN_X / ANIMATION_STEPS;
    int x = CENTER_X - MARGIN_X * prev_cursol;
    
    for (uint8_t i=0; i<ANIMATION_STEPS; i++) {
      x -= dx;
      drawMenuSub(x);
    }
    delay(180);
    prev_cursol = cursol;
  }
  else {
    if (changed) {
      oled.setCursor(CENTER_X, 3);
      if (cursol == 1) {
        // Contrast
        oled.write(128 + display_contrast);
      } else if (cursol == 2) {
        // Sound
        oled.write(128 + buzzer_volume);
      } else if (cursol == 3) {
        // Power Saving
        oled.write(128 + delay_sleep);
      }
      changed=false;
    }
  }

  oled.setCol(34);
  oled.print("(");
  
  oled.setCol(74);
  oled.print(")");
  
}

void drawMenuSub(int x) {
  oled.setCursor(0, 3);
  oled.clearToEOL();
  
  printWithCheckBoundry(x + MARGIN_X * 0, "{");
  printWithCheckBoundry(x + MARGIN_X * 1, "|");
  printWithCheckBoundry(x + MARGIN_X * 2, "}");
  printWithCheckBoundry(x + MARGIN_X * 3, "~");
  printWithCheckBoundry(x + MARGIN_X * 4, "@");

}


