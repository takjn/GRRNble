#include <Arduino.h>
#include <RTC.h>
#include <Wire.h>
#include <stdlib.h>

#include "SSD1306Ascii.h"
#include "SSD1306AsciiWire.h"

//#define DEBUG 1

// setting for SSD1306
SSD1306AsciiWire oled;
#define I2C_ADDRESS 0x3C

// settings for I/O pins
#define BUZZER_PIN      10          // pin for buzzer
#define KEY_PREV_PIN    9           // pin for previous button
#define KEY_SELECT_PIN  3           // pin for select button
#define KEY_NEXT_PIN    5           // pin for next button
#define VOLTAGE_OUT_PIN A1          // pin for voltage measurement
#define VOLTAGE_CHK_PIN A0          // pin for voltage measurement
#define WAKEHW_PIN  11              // pin for RN4020 WAKEHW
#define WAKESW_PIN  12              // pin for RN4020 WAKESW
#define PIO1_PIN 2                  // pin for RN4020 PIO1

// settings for buzzer
const uint8_t BUZZER_VOLUMES[4] = { 0, 1, 5, 15 };  // 4 steps volume (0=silence)
int buzzer_volume = 3;                              // initial volume index

// settings for display
const int DISPLAY_CONTRASTS[4] = { 0, 50, 128, 255 };  // 4 steps contrast
int display_contrast = 3;
boolean display_always_on = true;

// settings for power saving
const unsigned long DELAY_SLEEPS[4] = {0, 20000, 10000, 5000};  // sleep (millisec, 0=always on)
int delay_sleep = 3;
boolean is_active = true;

// settings for battery service
#define MAX_VOLTAGE 3.7
#define MAX_VOLTAGE_DROP 0.5
int voltage = 0;

// settings for temperature service
double temperature = getTemperature(TEMP_MODE_CELSIUS);

// variables for watch
RTC_TIMETYPE datetime = {15, 12, 31, 2, 23, 59, 30};

// variables for notification
String message = "";
bool has_notice = false;

// variables for power saving
unsigned long last_millis = 0;

// mode
#define MODE_TIME 0        // Watch
#define MODE_MENU 1        // Menu
#define MODE_SETTIME 2     // Set Date & Time
uint8_t mode_current = MODE_TIME;

// key code
#define KEY_NONE 0
#define KEY_PREV 1
#define KEY_NEXT 2
#define KEY_SELECT 3

void wakeupInterrupt() {
  is_active = true;
}

void notificationInterrupt() {
  if (has_notice == false) has_notice = true;
  wakeupInterrupt();
}

void setup() {
  // initialize RTC
  rtc_init();
  SUBCUD.subcud = 0xB8;  // http://japan.renesasrulz.com/gr_user_forum_japanese/f/gr-kurumi/631/gr-kurumi-rtc
  
  // initialize SSD1306
  Wire.begin();     
  oled.begin(&Adafruit128x64, I2C_ADDRESS);
  oled.setContrast(DISPLAY_CONTRASTS[display_contrast]);
  oled.clear();

  // setup voltage measurement
  pinMode(VOLTAGE_OUT_PIN, OUTPUT);
  pinMode(VOLTAGE_CHK_PIN, INPUT);
  
  // setup pins
  pinMode(BUZZER_PIN, OUTPUT);
  pinMode(KEY_PREV_PIN, INPUT_PULLUP);
  pinMode(KEY_SELECT_PIN, INPUT_PULLUP);
  pinMode(KEY_NEXT_PIN, INPUT_PULLUP);
  pinMode(PIO1_PIN, INPUT);
  pinMode(WAKESW_PIN, OUTPUT);
  pinMode(WAKEHW_PIN, OUTPUT);

  // setup for RN4020
  digitalWrite(WAKESW_PIN, LOW);
  digitalWrite(WAKEHW_PIN, LOW);
#ifdef DEBUG
  Serial.begin(115200);
#endif
  Serial1.begin(2400);

  voltage = getVoltage();
  last_millis = millis();
  
  // Set Pre-charge Period
  oled.ssd1306WriteCmd(SSD1306_SETPRECHARGE);
  oled.ssd1306WriteCmd(0x00);  // 0xF1(default)
  oled.ssd1306WriteCmd(SSD1306_SETVCOMDETECT);
  oled.ssd1306WriteCmd(0x00);  // 0x00, 0x20, 0x30, 0x40 (default)

  // wakeup interrupt
  attachInterrupt(0, notificationInterrupt, RISING);
  attachInterrupt(1, wakeupInterrupt, CHANGE);

  // setup power management
  setPowerManagementMode(PM_STOP_MODE);
}

void sleep() {
  oled.set1X();
  oled.clear();
  if (display_always_on == true) {
    drawSmallWatch();
  } else {
    // Disable charge pump
    oled.ssd1306WriteCmd(SSD1306_CHARGEPUMP);
    oled.ssd1306WriteCmd(0x10);
    // Set Display off
    oled.ssd1306WriteCmd(0x0ae);
  }

  is_active = false;
}

void wakeup() {
  if (display_always_on == false) {
    // Enable charge pump
    oled.ssd1306WriteCmd(SSD1306_CHARGEPUMP);
    oled.ssd1306WriteCmd(0x14);
    // Set Display on
    oled.ssd1306WriteCmd(0x0af);
  }
  oled.clear();
  
  is_active = true;
  last_millis = millis();
  mode_current = MODE_TIME;
}

void loop() {
  // unsigned int span;
  unsigned char key;
  
  // stanby loop
  if (is_active == false) {
    while(is_active == false) {
      notifyBLE();
      delay(500);
    }
    wakeup();
  }

  // check buttons
  key = key_read();
  if (key != KEY_NONE) {
    last_millis = millis();
  }

  // check BLE
  if (has_notice == true) {
    setPowerManagementMode(PM_NORMAL_MODE);
    boolean result = checkBLE();
    if (result == true) {
      alert();
    }
    setPowerManagementMode(PM_STOP_MODE);
    has_notice = false;
    last_millis = millis();
  }

  // notify sensors status
  notifyBLE();

  // draw screen
  if (mode_current == MODE_TIME) {
    drawWatch(key);
  } else if (mode_current == MODE_MENU) {
    drawMenu(key);
  } else if (mode_current == MODE_SETTIME) {
    drawSetTime(key);
  }

  // delay  
  delay(50);
  
  // sleep if idle
  if(delay_sleep > 0 && (millis() - last_millis) > DELAY_SLEEPS[delay_sleep]) {
    sleep();
  }
}
