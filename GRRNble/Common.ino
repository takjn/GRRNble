
unsigned char key_read(void) {
  if (digitalRead(KEY_SELECT_PIN) == 0) {
    digitalWrite(24, LOW);
    while (digitalRead(KEY_SELECT_PIN) == 0);
    digitalWrite(24, HIGH);
    tick_counter = 0;
    return KEY_SELECT;
  } else if (digitalRead(KEY_PREV_PIN) == 0) {
    while (digitalRead(KEY_PREV_PIN) == 0);
    tick_counter = 0;
    return KEY_PREV;
  } else if (digitalRead(KEY_NEXT_PIN) == 0) {
    while (digitalRead(KEY_NEXT_PIN) == 0);
    tick_counter = 0;
    return KEY_NEXT;
  }
  
  return KEY_NONE;
}

void beep(void) {
  if (buzzer_volume != 0) {
    tone(BUZZER_PIN, 1024, BUZZER_VOLUMES[buzzer_volume]);
  }
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

static float getVoltage() {
  float ret = 0.0;

  digitalWrite(VOLTAGE_OUT_PIN, 1);
  
  // 2:1で抵抗分圧した回路を前提に、 RL78/G13の内部基準電圧(1.45V)でA/Dを実施。
  int voltage = analogRead(VOLTAGE_CHK_PIN);
  ret = ((float)voltage/1023)*1.45/0.333333;

  digitalWrite(VOLTAGE_OUT_PIN, 0);

  return ret;
}

