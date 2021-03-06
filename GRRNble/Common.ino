
unsigned char key_read(void) {
  if (digitalRead(KEY_SELECT_PIN) == 0) {
    while (digitalRead(KEY_SELECT_PIN) == 0);
    return KEY_SELECT;
  } else if (digitalRead(KEY_PREV_PIN) == 0) {
    while (digitalRead(KEY_PREV_PIN) == 0);
    return KEY_PREV;
  } else if (digitalRead(KEY_NEXT_PIN) == 0) {
    while (digitalRead(KEY_NEXT_PIN) == 0);
    return KEY_NEXT;
  }
  
  return KEY_NONE;
}

void beep(void) {
  if (buzzer_volume != 0) {
    tone(BUZZER_PIN, 1024, BUZZER_VOLUMES[buzzer_volume]);
  }
}

void alert(void) {
  beep();
  delay(100);
  beep();
  delay(100);
  beep();
}

void printWithCheckBoundry(int x, String str) {
  if (x>=0 && x < oled.displayWidth()) {
    oled.setCol(x);
    oled.print(str);
  }
}

void printValueWithCheckBoundry(int x, int val) {
  if (x>=0 && x < oled.displayWidth()) {
    oled.setCol(x);
    printWithZero(val);
  }
}

void printWithZero(int v) {
  if (v<10) {
    oled.print("0");
  }
  oled.print(v);
}

static int getVoltage() {
  float v = 0.0;
  int ret = 0;

  is_usb_connected = digitalRead(USB_STAT_PIN);
  if (is_usb_connected == 1) {
    is_charging = digitalRead(CHG_STAT_PIN);
    ret = 99;
  } else {
    digitalWrite(VOLTAGE_OUT_PIN, 1);
    
    // 7K5:2K7で抵抗分圧した回路を前提にA/Dを実施。
    int voltage = analogReadWithClockControl(VOLTAGE_CHK_PIN);

    digitalWrite(VOLTAGE_OUT_PIN, 0);

    v = ((float)voltage/1023) * 3.3 / 0.27;
    ret = 100 - ( (MAX_VOLTAGE - v) / MAX_VOLTAGE_DROP * 100);
    
    if (ret > 99) {
      ret = 99;
    } else if (ret < 0) {
      ret = 0;
    }
    
#ifdef DEBUG
    Serial.println(v);
#endif
  }
 
  String s = "SHW,0022," + String(ret, HEX); 
  sendToRN4020(s);

  return ret;
}

int analogReadWithClockControl(uint8_t pin) {
  SBI2(SFR2_PER0, SFR2_BIT_ADCEN);  // enable A/D converter
  int result = analogRead(pin);
  CBI2(SFR2_PER0, SFR2_BIT_ADCEN);  // disable A/D converter

  return result;
}

int getTemperatureWithClockControl(uint8_t u8Mode) {
  SBI2(SFR2_PER0, SFR2_BIT_ADCEN);  // enable A/D converter
  int result = getTemperature(u8Mode);
  CBI2(SFR2_PER0, SFR2_BIT_ADCEN);  // disable A/D converter

  return result;
}

static double getAvgTempareture() {
  double ret = temperature * 0.95 + getTemperatureWithClockControl(TEMP_MODE_CELSIUS) * 0.05;
  int t = (int)ret;
  
  String s = "SHW,0018," + String(t, HEX); 
  sendToRN4020(s);

  return ret;  
}
