String last_command = "";

void checkBLE() {
  String command = "";
  
  voltage = getVoltage();
  temperature = getAvgTempareture();
  
  // trash garbage
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
    delay(5);
    char c = Serial1.read();
    
    if (c == '\n') {
      if (last_command != command && !command.startsWith("AOK") && !command.startsWith("00")) {
        last_command = command;
        has_notification = true;
        beep_flag = true;

        // wake up
        if (!is_active) {
          wake_flag = true;
        }
      }
    }
    else {
      command += c;
    }
  }
}

void checkNotification() {
  message = decodeValue(last_command);
  
  if (message.startsWith("DT")) {
    // Time sync command
    int year = message.substring(3, 5).toInt() + 2000;
    int month = message.substring(6, 8).toInt();
    int day = message.substring(9, 11).toInt();
    int week = message.substring(12, 13).toInt();
    int hour = message.substring(14, 16).toInt();
    int minute = message.substring(17, 19).toInt();

    datetime = {year, month, day, week, hour, minute, 00};
    rtc_set_time(&datetime);
    
    has_notification = false;
    return;
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

