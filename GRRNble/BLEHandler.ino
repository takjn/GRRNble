String last_command = "";
String last_nofication = "";

void checkBLE() {
#ifdef DEBUG      
  Serial.println(tick_counter);
  Serial.flush();
#endif

  String command = "";
  
  // trash garbage
  while(Serial1.available() > 0) {
    char c = Serial1.read();
  }
  
  // send read command and wait
  Serial1.println("SHR,001B");
  Serial1.flush();
  delay(10);

  // read result
  while(Serial1.available() > 0){
    delay(5);
    char c = Serial1.read();
    
    if (c == '\n') {
      if (last_command != command && !command.startsWith("AOK")) {
        last_command = command;
        has_notification = true;

        // wake up
        if (!is_active) {
          wake_flag = true;
        }
      }

#ifdef DEBUG
      Serial.println(command);
      Serial.flush();
#endif
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

}

static String decodeValue(String s) {
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

