String last_command = "";
unsigned int last_minute = 99;

void notifyBLE() {
  rtc_get_time(&datetime);
  if (datetime.min == last_minute) {
    return;
  }
  last_minute = datetime.min;
  
  // update battery level and temperature
  voltage = getVoltage();
  temperature = getAvgTempareture();

  if (is_active == false && display_always_on == true) {
    drawSmallWatch();
  }
}

void drawSmallWatch() {
  // update time
  oled.setCursor(36, 4);
  printWithZero(datetime.hour);
  oled.print(':');
  printWithZero(datetime.min);
}

void checkBLE() {
  String command = "";
  
  // trash garbage
  delay(50);
  while(Serial1.available() > 0) {
    char c = Serial1.read();
    delay(5);
  }
  
  // send read command and wait
  Serial1.println("SHR,001B");
  Serial1.flush();
  delay(10);

  // read result
  while(Serial1.available() > 0){
    char c = Serial1.read();
    
    if (c == '\n') {
      if (last_command != command && !command.startsWith("AOK") && !command.startsWith("00")) {
        last_command = command;

        // read extra message
        while(Serial1.available() > 0) {
          char c = Serial1.read();
          delay(5);
        }
        Serial1.println("SHR,001D");
        Serial1.flush();
        delay(10);
        while(Serial1.available() > 0){
          char c = Serial1.read();
          if (c != '\r' && c != '\n') {
            command += c;
          }
          delay(5);
        }

        message = decodeValue(command);
#ifdef DEBUG
  Serial.println(command);
  Serial.println(message);
#endif
        has_notification = true;
        beep_flag = true;
        last_millis = millis();

        // wake up
        if (!is_active) {
          wake_flag = true;
        }
      }
    }
    else {
      if (c != '\r') {
        command += c;
      }
    }
    
    delay(5);
  }
}

void checkNotification() {
  if (message.startsWith("DT")) {
    // Time sync command
    short unsigned int year = message.substring(3, 5).toInt() + 2000;
    unsigned char  month = message.substring(6, 8).toInt();
    unsigned char day = message.substring(9, 11).toInt();
    unsigned char week = message.substring(12, 13).toInt();
    unsigned char hour = message.substring(14, 16).toInt();
    unsigned char minute = message.substring(17, 19).toInt();

    datetime = {year, month, day, week, hour, minute, 00};
    rtc_set_time(&datetime);
    
    has_notification = false;

    return;
  }
}

static String decodeValue(String s) {
  String str = "";
  char buf[3];
  buf[2] = '\0';

  for (int i=0; i<s.length(); i=i+2) {
    buf[0] = s.charAt(i);
    buf[1] = s.charAt(i + 1);
    
    char chr = (char)strtol(buf, NULL, 16);
  
    str += chr;
  }
  
  return str;
}

