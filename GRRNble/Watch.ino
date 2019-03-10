const char *DayShortStr[7] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
const char *MonthShortStr[12] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

int last_second = 0;

void drawSmallWatch() {
  // update time
  oled.setCursor(36, 4);
  printWithZero(datetime.hour);
  oled.print(':');
  printWithZero(datetime.min);
}

void drawWatch(unsigned char key) {
  if (key == KEY_SELECT) {
    mode_current = MODE_MENU;
    return;
  }
  
  if (key == KEY_NEXT && message.length() > 0) {
    message = "";
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
  
  // draw notification
  oled.setCursor(0, 5);
  if (message.length() > 0) {
    char buf[41];
    message.toCharArray(buf, sizeof(buf));
    oled.printMisakiUTF16(buf);
  }
  oled.clearToEOL();
  
  oled.set1X();
  oled.setCursor(0, 7);
  oled.setFont(BM_tube9x8);
  
  oled.print((int)temperature); 
  oled.write(132);
  oled.write('C');
  
  oled.setCursor(97, 7);
  if (is_usb_connected == 1) {
    if (is_charging == 1) {
      oled.print("CHG");
    } else {
      oled.print("USB");
    }
  } else {
    oled.print(voltage);
    oled.write('%');
  }
}

