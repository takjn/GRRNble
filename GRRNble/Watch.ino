const char *DayShortStr[7] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
const char *MonthShortStr[12] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

double temperature = getTemperature(TEMP_MODE_CELSIUS);

void drawWatch(unsigned char key) {
  if (key == KEY_SELECT) {
    mode_current = MODE_MENU;
    return;
  }
  
  rtc_get_time(&datetime);

  // draw date strings
  oled.setFont(BM_tube9x8);
  oled.set1X();
  oled.setCursor(0, 0);
  oled.print(DayShortStr[datetime.weekday]);
  oled.setCursor(33, 0);
  printWithZero(datetime.day);
  oled.setCursor(56, 0);
  oled.print(MonthShortStr[datetime.mon - 1]);
  oled.setCursor(89, 0);
  oled.print((datetime.year+2000));
 
  // draw time strings
  oled.setCursor(2, 3);
  oled.set2X();
  printWithZero(datetime.hour);
  oled.print(':');
  printWithZero(datetime.min);

  oled.set1X();
  oled.setCursor(107, 4);
  printWithZero(datetime.second);
  
  oled.set1X();
  oled.setCursor(0, 7);
  oled.setFont(BM_tube9x8);
  
  temperature = temperature * 0.95 + getTemperature(TEMP_MODE_CELSIUS) * 0.05;
  oled.print((int)temperature); 
  oled.write(132);
  oled.write('C');
  
  oled.setCursor(79, 7);
  oled.print(voltage);
  oled.write('V');
}

