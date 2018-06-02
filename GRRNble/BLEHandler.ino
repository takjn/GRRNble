String last_nofication = "";

void drawNotification(unsigned char key, String value) {
  if (key == KEY_SELECT && is_active) {
    has_notification = false;
    last_nofication = "";
    oled.clear();

    return;
  }
  
  // draw current time
  rtc_get_time(&datetime);
  oled.set1X();
  oled.setCursor(0, 7);
  printWithZero(datetime.hour);
  oled.print(':');
  printWithZero(datetime.min);
  oled.setCursor(56, 7);
  printWithZero(datetime.second);
  oled.clearToEOL();
  
  if (value.startsWith("WV,001B,")) {
    String message = decodeValue(value);
    String title = "";
    String body = "";
    int sp = message.indexOf(",");
    if (sp > 0) {
      title = message.substring(0, sp);
      body = message.substring(sp+1);
    }
    else {
      title = message;
    }

    oled.set1X();
    oled.setCursor(0, 0);
    oled.print("Notification");
    oled.clearToEOL();
  
    // draw notification
    oled.set2X();
    oled.setCursor(0, 3);
    oled.print(title);
    oled.clearToEOL();
    oled.set1X();
    oled.setCursor(0, 5);
    oled.print(body);
    oled.clearToEOL();

    if (last_nofication != message) {
      beep();
      delay(100);
      beep();
      delay(100);
      beep();

      last_nofication = message;
    }

#ifdef DEBUG
    Serial.println(value);
#endif
  }
}


static String decodeValue(String value) {
  String s = value.substring(8, value.length() - 2);
  
  String str = "";
  for (int i=0; i<s.length(); i=i+2) {
    String tmp = "0x";
    tmp += s.charAt(i);
    tmp += s.charAt(i + 1);
    
    char buf[5];
    tmp.toCharArray(buf, 5);
  
    char chr = (char)strtol(buf, NULL, 16);
  
    str += chr;
  }
  
  return str;
}

