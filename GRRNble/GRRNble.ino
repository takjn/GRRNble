#include <Arduino.h>
#include <RTC.h>
#include <Wire.h>
#include <stdlib.h>

#include "SSD1306Ascii.h"
#include "SSD1306AsciiWire.h"

#define DEBUG 1

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
const unsigned long DELAY_SLEEPS[4] = {0, 7000, 10000, 15000};  // sleep (millisec, 0=always on)
int delay_sleep = 1;
unsigned long int tick_counter = 0;
boolean wake_flag = false;
boolean is_active = true;

// settings for battery service
#define MAX_VOLTAGE 3.3
int voltage = 0;

double temperature = getTemperature(TEMP_MODE_CELSIUS);

// variables for watch
RTC_TIMETYPE datetime = {15, 12, 31, 2, 23, 59, 30};

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

// Notification handler
boolean has_notification = false;

void tick_handler(unsigned long u32ms) {
  tick_counter++;
}

void setup() {
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
  attachIntervalTimerHandler(tick_handler);
  
  // setup for RN4020
#ifdef DEBUG
  Serial.begin(115200);
#endif
  Serial1.begin(2400);

  voltage = getVoltage();
}

int last_tick_counter = 0;
void loop() {
  // get command from BLE module
  if (is_active) {
    int span = tick_counter - last_tick_counter;
    if ( span > 5000 || span < 0) {
      checkBLE();
      last_tick_counter = tick_counter;
    }
  }
  else {
    if (tick_counter > 1000) {
      setOperationClockMode(CLK_HIGH_SPEED_MODE);
      checkBLE();
      tick_counter = 0;
      setOperationClockMode(CLK_LOW_SPEED_MODE);
    }
  }

  // read key
  unsigned char key = key_read();

  // turn display on if the display is off
  if (wake_flag == true) {
    setOperationClockMode(CLK_HIGH_SPEED_MODE);
    oled.ssd1306WriteCmd(0x0af); // display on
    wake_flag = false;
    is_active = true;
    tick_counter = 0;
  }
  
  if (is_active) {
    // draw screen if the display is on
    if (has_notification) {
      drawNotification(key);
    } else if (mode_current == MODE_TIME) {
      drawWatch(key);
    } else if (mode_current == MODE_MENU) {
      drawMenu(key);
    } else if (mode_current == MODE_SETTIME) {
      drawSetTime(key);
    }
    
    // sleep if idle
    if(delay_sleep > 0 && tick_counter > DELAY_SLEEPS[delay_sleep]) {
      oled.ssd1306WriteCmd(0x0ae); // display off
      is_active = false;
      setOperationClockMode(CLK_LOW_SPEED_MODE);
    }
    
    // delay
    for(int i=0;i<100;i++) {
      _HALT();
    }
  }
}
