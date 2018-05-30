#include <Arduino.h>
#include <RTC.h>
#include <Wire.h>
#include "SSD1306Ascii.h"
#include "SSD1306AsciiWire.h"

// setting for SSD1306
SSD1306AsciiWire oled;
#define I2C_ADDRESS 0x3C

// settings for I/O pins
#define BUZZER_PIN      10          // pin for buzzer
#define KEY_PREV_PIN    4           // pin for previous button
#define KEY_SELECT_PIN  3           // pin for select button
#define KEY_NEXT_PIN    2           // pin for next button
#define VOLTAGE_OUT_PIN A7          // pin for voltage measurement
#define VOLTAGE_CHK_PIN A0          // pin for voltage measurement

// settings for buzzer
const uint8_t BUZZER_VOLUMES[4] = { 0, 1, 5, 15 };  // 4 steps volume (0=silence)
int buzzer_volume = 3;                              // initial volume index

// settings for display
const int DISPLAY_CONTRASTS[4] = { 0, 50, 128, 255 };  // 4 steps contrast
int display_contrast = 3;

// settings for power saving
const unsigned long DELAY_SLEEPS[4] = {0, 5000, 10000, 15000};  // sleep (millisec, 0=always on)
int delay_sleep = 1;
unsigned long int tick_counter = 0;
boolean display_power = false;

// variables for watch
RTC_TIMETYPE datetime = {15, 12, 31, 2, 23, 59, 30};
float voltage = 0;

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

void sleep() {
  oled.ssd1306WriteCmd(0x0ae); // display off
  display_power = false;
  
  setPowerManagementMode(PM_STOP_MODE);
  delay(0xffffffff); // into stop mode
}

void resume() {
  tick_counter = 0;
  voltage = getVoltage();
}

void tick_handler(unsigned long u32ms)
{
  tick_counter++;
}

void setup() {
//  pinMode(24, OUTPUT); //blue led
//  digitalWrite(24, HIGH);
  
  // initialize RTC
  rtc_init();
  SUBCUD.subcud = 0xB8;  // http://japan.renesasrulz.com/gr_user_forum_japanese/f/gr-kurumi/631/gr-kurumi-rtc
  rtc_set_time(&datetime); // RTCに初期値をセット
  
  // initialize SSD1306
  Wire.begin();     
  oled.begin(&Adafruit128x64, I2C_ADDRESS);
  oled.setContrast(DISPLAY_CONTRASTS[display_contrast]);
  oled.clear();

  // setup for voltage measurement
  analogReference(INTERNAL);
  pinMode(VOLTAGE_OUT_PIN, OUTPUT);
  pinMode(VOLTAGE_CHK_PIN, INPUT);
  
  // setup for the power management
  pinMode(BUZZER_PIN, OUTPUT);
  pinMode(KEY_PREV_PIN, INPUT_PULLUP);
  pinMode(KEY_SELECT_PIN, INPUT_PULLUP);
  pinMode(KEY_NEXT_PIN, INPUT_PULLUP);
  attachInterrupt(0, resume, FALLING);
  attachIntervalTimerHandler(tick_handler);

  sleep();
}

void loop() {
  // turn display on if off
  if (!display_power) {
    oled.ssd1306WriteCmd(0x0af); // display on
    display_power = true;
  }

  // read key
  unsigned char key = key_read();
  if (mode_current == MODE_TIME) {
    drawWatch(key);
  } else if (mode_current == MODE_MENU) {
    drawMenu(key);
  } else if (mode_current == MODE_SETTIME) {
    drawSetTime(key);
  }

  // sleep if idle
  if (delay_sleep > 0 & tick_counter > DELAY_SLEEPS[delay_sleep]) {
    sleep();
  }
  
  delay(50);
}
