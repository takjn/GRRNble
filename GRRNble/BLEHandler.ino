void notifyBLE() {
  // update battery level and temperature
  Serial1.begin(2400);
  voltage = getVoltage();
  temperature = getAvgTempareture();
  Serial1.end();
}

void sendToRN4020(String command) {
  Serial1.println(command);
  Serial1.flush();
  delay(10);
#ifdef DEBUG
  while(Serial1.available() > 0) {
    char c = Serial1.read();
    Serial.write(c);
    delay(5);
  }
#endif
}

boolean checkBLE() {
  String command = "";
  boolean has_notification = false;

  Serial1.begin(2400);

  sendToRN4020("WP");             // Pause RN4020 script
  sendToRN4020("SHW,001F,00");    // Reset characteristic value
  sendToRN4020("|O,01,00");       // Reset pio0 value

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
      if (!command.startsWith("AOK") && !command.startsWith("00")) {
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

        // wake up
        wakeupInterrupt();
      }
    }
    else {
      if (c != '\r') {
        command += c;
      }
    }
    
    delay(5);
  }

  // check notification message
  if (has_notification) {
    has_notification = checkNotification();
  }

  sendToRN4020("WR");             // Run RN4020 script

  Serial1.end();

  return has_notification;
}

boolean checkNotification() {
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
    
    message = "";
    return false;
  }

  return true;
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

