/**
  GRRNble - RN4020 setup program

  Copyright (c) 2019 Jun Takeda

  This software is released under the MIT License.
  http://opensource.org/licenses/mit-license.php
*/

#include <Arduino.h>

// pin assignments
#define KEY_PREV_PIN    10          // pin for previous button
#define KEY_SELECT_PIN  3           // pin for select button
#define KEY_NEXT_PIN    5           // pin for next button
#define WAKEHW_PIN  11              // pin for RN4020 WAKEHW
#define WAKESW_PIN  12              // pin for RN4020 WAKESW
#define PIO1_PIN 2                  // pin for RN4020 PIO1

// key code
#define KEY_NONE 0
#define KEY_PREV 1
#define KEY_NEXT 2
#define KEY_SELECT 3


int waitKeyIn() {
  while (digitalRead(KEY_SELECT_PIN) != 0 && digitalRead(KEY_PREV_PIN) != 0 && digitalRead(KEY_NEXT_PIN) != 0);

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

void readFromRN4020() {
  while (Serial1.available()) {
    int inByte = Serial1.read();
    Serial.write(inByte); 
    delay(5);
  }
}

void sendToRN4020(String command) {
  Serial1.println(command);
  Serial1.flush();
  delay(500);
  readFromRN4020();
}

void setupRN4020() {
  sendToRN4020("SF,2");            // Factory reset
  sendToRN4020("SN,GRRNble");      // Set name
  sendToRN4020("SB,0");            // Set 2400bps

  sendToRN4020("SS,C0000001");     // Enable device information, battery, private service
  sendToRN4020("SR,25002000");     // Set the device as periferal (Auto Advertise, No Direct Advertisement, Run script after power on)
  sendToRN4020("ST,0010,0004,0258");    // Set interval, latency and timeout
  sendToRN4020("PZ");              // Clear the current private service and characteristics

  // Set private service UUID
  sendToRN4020("PS,3B382559223F48CA81B4E151598F661B");

  // Add private characteristic to current private service.
  // The property of this characteristic is 0x12 (readble and could notify) and has a maximum data size of 2 bytes.
  sendToRN4020("PC,DB5445C44A70442287AF81D35456BEB5,12,02");

  // The property of this characteristic is 0x0A (readble and writable) and has a maximum data size of 20 bytes.
  sendToRN4020("PC,B23324431DD3407BB3E65D349CAF5368,0A,14");
  sendToRN4020("PC,E2B8D854BFA64E798A5326B07604597D,0A,14");

  // The property of this characteristic is 0x04 (writable) and has a maximum data size of 1 bytes.
  sendToRN4020("PC,4344EEE2C4674318BFAB7564815A5DB8,04,01");

  sendToRN4020("WC");              // Clear script
  sendToRN4020("WW");              // Enter script input mode

  // write RN4020 script
  sendToRN4020("@PW_ON");          // handle power on event
  sendToRN4020("|O,01,%001F");     // associate handle 0x001F to PIO1

  // send the “ESC” key to exit from script input mode.
  Serial1.write(0x1b);
  Serial1.flush();

  sendToRN4020("R,1");             // Reboot RN4020 to make the changes effective

  Serial1.begin(2400);
  delay(1000);
  sendToRN4020("WP");              // Pause script

  sendToRN4020("D");               // Dump configuration
  sendToRN4020("LS");              // list the services on server side. Private service and characteristics could be found in the list
  sendToRN4020("LW");              // Show script
}

void showMenu() {
  Serial.println("--------------------------------------------------------------------------------");
  Serial.println("GRRNble RN4020 setup program");
  Serial.println("--------------------------------------------------------------------------------");
  Serial.println("To connect with RN4020 at 115200 bps(default), push the top button.");
  Serial.println("To connect with RN4020 at 2400 bps, push the bottom button.");

  int key = waitKeyIn();
  if (key == KEY_NEXT) {
    Serial1.begin(2400);
    Serial.println("2400 bps is selected.");
  } else {
    Serial.println("115200 bps is selected.");
  }
  Serial.println("");
  sendToRN4020("WP");
  sendToRN4020("D");
  Serial.println("");

  Serial.println("To setup, push the top button.");
  Serial.println("To communicate with RN4020, push the bottom button.");
  Serial.println("To restart, push the center button.");

  key = waitKeyIn();
  if (key == KEY_PREV) {
    Serial.println("Starting setup.");
    setupRN4020();
  }
  if (key == KEY_SELECT) {
    softwareReset();
  } 

  Serial.println("");
}

bool interrupted = false;
void debugInterrupt() {
  interrupted = true;
}

void setup() {
  pinMode(KEY_PREV_PIN, INPUT_PULLUP);
  pinMode(KEY_SELECT_PIN, INPUT_PULLUP);
  pinMode(KEY_NEXT_PIN, INPUT_PULLUP);

  // set RN4020 power status
  pinMode(PIO1_PIN, INPUT);
  pinMode(WAKEHW_PIN, OUTPUT);
  pinMode(WAKESW_PIN, OUTPUT);
  digitalWrite(WAKEHW_PIN, LOW);
  digitalWrite(WAKESW_PIN, HIGH);

  Serial.begin(115200);
  Serial1.begin(115200);

  showMenu();

  attachInterrupt(0, debugInterrupt, RISING);
  Serial.println("Done.You can communicate with RN4020.");
}

void loop() {
  // read from port 1, send to port 0:
  if (Serial1.available()) {
    int inByte = Serial1.read();
    Serial.write(inByte); 
  }
  
  // read from port 0, send to port 1:
  if (Serial.available()) {
    int inByte = Serial.read();
    Serial1.write(inByte); 
  }

  if (interrupted) {
    interrupted = false;
    Serial.println("triggered");
    sendToRN4020("WP");             // Pause script
    sendToRN4020("SHW,001F,00");    // Reset characteristic value
    sendToRN4020("|O,01,00");       // Reset pio0 value
    sendToRN4020("SHR,001B");       // Read characteristic value
    sendToRN4020("WR");             // Run script
  }
}