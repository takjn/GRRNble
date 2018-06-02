String last_command = "";
String command = "";
String last_nofication = "";

void checkBLE() {
  while(Serial1.available() > 0){
    char c = Serial1.read();

    if (c == '\n') {
      if (last_command != command) {
        last_command = command;

        // wake up
        if (!is_active && !command.startsWith("AOK")) {
          wake_flag = true;
        }

#ifdef DEBUG
        Serial.println(command);
        Serial.flush();
#endif

        has_notification = true;
        command = "";
      }
    }
    else {
      command += c;
    }
  }
}

void drawNotification(unsigned char key) {
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
  
  if (last_command.startsWith("WV,001B,")) {
    String message = decodeValue(last_command);
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
    oled.print("Incoming");
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
    Serial.println(last_command);
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

