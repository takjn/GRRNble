int settime_cursol = 0;
int settime_prev_cursol = -1;
boolean settime_setmode = false;

#define SETTIME_CENTER_X 46
#define SETTIME_MARGIN_X 128
#define SETTIME_ANIMATION_STEPS 8

#define SETTIME_COUNT 7
#define SETTIME_TITLE "Date & Time"
const char *SETTIME_SUB_TITLE[SETTIME_COUNT] = {"YEAR", "MONTH", "DAY", "HOUR", "MINUTE", "COMMIT", "CANCEL"};

void drawSetTime(unsigned char key) {
  // return if idle
  if (key == KEY_NONE && settime_cursol == settime_prev_cursol) return;
  
  // key handler
  if (key == KEY_SELECT) {
    if (settime_setmode) {
      settime_setmode = false;
    }
    else {
      if (settime_cursol > 4) {
        if (settime_cursol == 5) {
          datetime.second = 0;
          rtc_set_time(&datetime);
        }
        
        oled.clear();
        settime_prev_cursol = -1;
        mode_current = MODE_MENU;
        return;
      } else {
        settime_setmode = true;
      }
    }
  }
  else if (key == KEY_NEXT) {
    if (settime_setmode) {
      if (settime_cursol == 0) {
        // year
        datetime.year++;
      } else if (settime_cursol == 1) {
        // month
        datetime.mon++;
        if (datetime.mon > 12) datetime.mon = 1;
      } else if (settime_cursol == 2) {
        // day
        datetime.day++;
        if (datetime.day > 31) datetime.day = 1;
      } else if (settime_cursol == 3) {
        // hour
        datetime.hour++;
        if (datetime.hour > 23) datetime.hour = 0;
      } else if (settime_cursol == 4) {
        // minute
        datetime.min++;
        if (datetime.min > 59) datetime.min = 0;
      }
    } else {
      settime_cursol++;
      if (settime_cursol == SETTIME_COUNT) settime_cursol = 0;
    }
  }
  else if (key == KEY_PREV) {
    if (settime_setmode) {
      if (settime_cursol == 0) {
        // year
        if (datetime.year > 0) datetime.year--;
      } else if (settime_cursol == 1) {
        // month
        datetime.mon--;
        if (datetime.mon < 1) datetime.mon = 12;
      } else if (settime_cursol == 2) {
        // day
        datetime.day--;
        if (datetime.day < 1) datetime.day = 31;
      } else if (settime_cursol == 3) {
        // hour
        datetime.hour--;
        if (datetime.hour > 24) datetime.hour = 23;
      } else if (settime_cursol == 4) {
        // minute
        datetime.min--;
        if (datetime.min > 60) datetime.min = 59;
      }      
    } else {
      if (settime_cursol < 1) {
        settime_cursol = SETTIME_COUNT - 1;
      } else {
        settime_cursol--;
      }
    }
  }
  beep();
  
  // draw title and subtitle
  oled.setFont(BM_tube9x8);
  oled.set1X();
  oled.setCursor(0, 0);
  oled.print(SETTIME_TITLE);
  oled.clearToEOL();
  oled.setCursor(0, 7);
  if (settime_setmode) {
    oled.print("SET ");
  }
  oled.print(SETTIME_SUB_TITLE[settime_cursol]);
  oled.clearToEOL();
  
  // draw contents
  oled.set2X();
  if (settime_cursol != settime_prev_cursol) {
    int dx = (settime_cursol - settime_prev_cursol) * SETTIME_MARGIN_X / SETTIME_ANIMATION_STEPS;
    int x = SETTIME_CENTER_X - SETTIME_MARGIN_X * settime_prev_cursol;
    
    for (uint8_t i=0; i<SETTIME_ANIMATION_STEPS; i++) {
      x -= dx;
      drawSetTimeSub(x);
    }
    delay(200);
    settime_prev_cursol = settime_cursol;
  }
  else {
    oled.setCursor(CENTER_X, 3);

    int x = SETTIME_CENTER_X - SETTIME_MARGIN_X * settime_cursol;
    drawSetTimeSub(x);
  }

  oled.setCol(19);
  oled.print("(");
  
  oled.setCol(94);
  oled.print(")");
  
}

void drawSetTimeSub(int x) {
  oled.setCursor(0, 3);
  oled.clearToEOL();
  printValueWithCheckBoundry(x + SETTIME_MARGIN_X * 0, datetime.year);
  printValueWithCheckBoundry(x + SETTIME_MARGIN_X * 1, datetime.mon);
  printValueWithCheckBoundry(x + SETTIME_MARGIN_X * 2, datetime.day);
  printValueWithCheckBoundry(x + SETTIME_MARGIN_X * 3, datetime.hour);
  printValueWithCheckBoundry(x + SETTIME_MARGIN_X * 4, datetime.min);
  printWithCheckBoundry(x + 10 + SETTIME_MARGIN_X * 5, "+");
  printWithCheckBoundry(x + 10 + SETTIME_MARGIN_X * 6, "-");
}

