const char *DayShortStr[7] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
const char *MonthShortStr[12] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

int last_second = 0;

void drawWatch(unsigned char key) {
  if (key == KEY_SELECT) {
    mode_current = MODE_MENU;
    return;
  }
  
  rtc_get_time(&datetime);
  if (datetime.second == last_second) {
    return;
  }
  last_second = datetime.second;

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
  oled.setCursor(0, 3);
  oled.set2X();
  printWithZero(datetime.hour);
  oled.print(':');
  printWithZero(datetime.min);

  oled.set1X();
  oled.setCursor(107, 4);
  printWithZero(datetime.second);
  
// TODO:ここに通知を表示するようにする
//  oled.setCursor(0, 5);
//  oled.printMisakiUTF16("新着メールが0件あります ");
  
  oled.set1X();
  oled.setCursor(0, 7);
  oled.setFont(BM_tube9x8);
  
  oled.print((int)temperature); 
  oled.write(132);
  oled.write('C');
  
  oled.setCursor(97, 7);
  oled.print(voltage);
  oled.write('%');
}

