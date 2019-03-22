# GRRNble

DIY Smartwatch using Renesas RL78/G13 MCU and RN4020 ble module with Android companion app.

[![DIY Smartwatch](https://img.youtube.com/vi/k7GzKx2PxCo/0.jpg)](https://www.youtube.com/watch?v=k7GzKx2PxCo)

## Specification
### Hardware

- RL78/G13 32MHz (R5F100LJAFB 64pin LFQFP)
- ROM/RAM: 256KB/20KB
- 0.96″ 128x64 OLED display
- RN4020 Bluetooth Low Energy module
- RTC
- Buzzer
- Measuring battery voltage
- Li-Polymer Charging circuit

### Software

- Arduino like [IDE for GR](http://gadget.renesas.com/en/product/ide4gr.html) based smartwatch app
- [Android companion app](https://play.google.com/store/apps/details?id=com.github.takjn.grrnble)

## Hardware
### Schematic diagram
There is a KiCad project file in KiCad folder. Also, you can see the schematic pdf file in pdf folder.

### Parts list
All capacitors and resistors are 0805 (2012) SMD.
| Reference(s) | Value | Qty | Note |
|:---|:---|:---|:---|
|C1|0.47uF|1|I used 1.0uF insted.|
|C2, C11|0.1uF|2||
|C3, C4|15pF|2||
|C5, C6, C9, C10, C15, C16|1.0uF|6||
|C7, C12, C13|4.7uF|3||
|C8|2.2uF|1|I used 4.7uF insted.|
|R1|1k|1||
|R2|12k|1||
|R3|2k|1||
|R4|390k|1||
|R5, R6, R7, R13|10k|4||
|R8|22k|1||
|R9, R10|47k|2||
|R11|100k|1||
|R12, R14|100R|2||
|R15|7K5|1||
|R16|2K7|1||
|D1|CUS10F30|1||
|Q1, Q3|DMG3415U|2|P-ch FET|
|Q2|BSS138|1|N-ch FET|
|SW1, SW3, SW2|SKSCLAE010|3|ALPS Side Push switch|
|U1|R5F100LJAFB|1|RL78/G13 64pin|
|U2|UGCT7525AN4|1|Buzzer|
|U3|SSD1306|1|OLED 128x64 0.96 inch|
|U4|MCP73831-2-OT|1|Li-Polymer Charge Management Controller|
|U5|RN4020|1|Bluetooth Low Energy module|
|U6|MCP1703A-3302|1|LDO Regulator|
|Y1|FC-135|1|EPSON 3.2 x 1.5 x 0.80mm 32.768k crystal|
|BT1|Li-Po battery|1|3.7V/40mAh|
|J1|JST SH/1.0 6pin connector|1|JST 1.0mm pitch connector|
|-|JST SH/1.0 6pin cable|1|JST 1.0mm pitch terminal cable|
|-|FT232R / FT231X USB to serial adapter|1||

## Software

### RN4020 setup app

RN4020 can be setup with a special program and serial monitor.
Open RN4020Setup folder with [IDE for GR](http://gadget.renesas.com/en/product/ide4gr.html).
Connect your board and select GR-Cotton from "Tools" -> "Board" in the menu.
Compile the program and upload it to the board.

After uploading the sketch, select the port from "Tools" -> "Serial Port" in the menu and open Serial monitor.

### Smartwatch app

Open GRRNble folder with [IDE for GR](http://gadget.renesas.com/en/product/ide4gr.html).
Connect your board and select GR-Cotton from "Tools" -> "Board" in the menu.
Compile the program and upload it to the board.

### Android companion app

You need to have a development environment with Android Studio to build the Android App in the Android folder.

If you don't want to build from source files, you can install the compiled app from [Google Play](https://play.google.com/store/apps/details?id=com.github.takjn.grrnble).

## References
### Libraries
- [https://github.com/greiman/SSD1306Ascii](https://github.com/greiman/SSD1306Ascii)
- [https://github.com/Tamakichi/Arduino-misakiUTF16](https://github.com/Tamakichi/Arduino-misakiUTF16)

### Useful information (EN)
- [Gadget Renesas](http://gadget.renesas.com/en/)
- [GR-COTTON schematic diagram](http://gadget.renesas.com/ja/product/documents/gr-cotton_sch.pdf)
- [RN4020 Bluetooth® Low Energy Module User’s Guide](http://ww1.microchip.com/downloads/en/devicedoc/70005191b.pdf)
- [MCP1703A](http://akizukidenshi.com/download/ds/microchip/mcp1703a.pdf)
- [Designing A Li-Ion Battery Charger and Load Sharing System With
Microchip’s Stand-Alone Li-Ion Battery Charge Management Controller](http://ww1.microchip.com/downloads/en/appnotes/01149c.pdf)
- [OLED Display and Arduino With Power-Save Mode](https://bengoncalves.wordpress.com/2015/10/01/oled-display-and-arduino-with-power-save-mode/)

### Useful information (JA)
- [Gadget Renesas](http://gadget.renesas.com/jp/)
- [GR-COTTON回路図](http://gadget.renesas.com/ja/product/documents/gr-cotton_sch.pdf)
- [RN4020 Bluetooth® Low Energy モジュールユーザガイド](http://akizukidenshi.com/download/ds/microchip/70005191A_JP.pdf)
- [MCP1703A](http://akizukidenshi.com/download/ds/microchip/mcp1703a.pdf)
- [マイクロチップのスタンドアロンリチウムイオンバッテリ充電管理コントローラを使用したリチウムイオンバッテリ充電回路および負荷分担システムの設計](http://ww1.microchip.com/downloads/en/AppNotes/DS01149C_JP_battery.pdf)
- [BLE通信ソフトを作る ( Android Studio 2.3.3 + RN4020 )](http://www.hiramine.com/programming/blecommunicator/index.html)
- [KiCadで雑に基板を作る チュートリアル](https://www.slideshare.net/soburi/kicad-53622272)
