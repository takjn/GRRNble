# GRRNble

WIP

RN4020を使いマイコンとAndroidの間でBLE通信をするためのAndroidアプリです。

## RN4020の設定

RN4020をシリアルケーブルで接続し、以下のコマンドを実行します。

```
SF,2            // Factory reset
SN,GRRNble      // Set name
SB,0            // Set 2400bps

SS,C0000001     // Enable device information, battery, private service
SR,24002000     // Set the device as periferal (Auto Advertise, No Direct Advertisement)
PZ              // Clear the current private service and characteristics

PS,3B382559223F48CA81B4E151598F661B
// Set private service UUID

PC,DB5445C44A70442287AF81D35456BEB5,12,02
// Add private characteristic to current private service.
// The property of this characteristic is 0x12 (readble and could notify) and has a maximum data size of 2 bytes.

PC,B23324431DD3407BB3E65D349CAF5368,0A,14
// Add private characteristic to current private service.
// The property of this characteristic is 0x0A (readble and writable) and has a maximum data size of 20 bytes.

U               // Unbond to make device discoverable
R,1             // Reboot RN4020 to make the changes effective
+               // Enable echo
LS              // list the services on server side. Private service and characteristics could be found in the list

```

LSコマンドを実行すると、以下のようなサービス一覧が表示されます。

```
180A
  2A25,000B,V
  2A27,000D,V
  2A26,000F,V
  2A28,0011,V
  2A29,0013,V
  2A24,0015,V
180F
  2A19,001E,V
  2A19,001F,C
3B382559223F48CA81B4E151598F661B
  DB5445C44A70442287AF81D35456BEB5,0018,02,02
  DB5445C44A70442287AF81D35456BEB5,0019,10,02
  B23324431DD3407BB3E65D349CAF5368,001B,0A,14
END
```

バッテリーサービスを99%に設定するためには、以下のコマンドを実行します。
```
SHW,001E,63
```

プライベートサービスのうち、0018がRead用、0019がnotify用、001Bが Read & Write用のcharacteristicとなります。
値を書き込むためには、以下のコマンドを実行します。

```
SHW,0018,0000
SHW,001B,616263
```

現在の値を読み込むためには、以下のコマンドを実行します。
```
SHR,0018
SHR,001B
```

なお、クライアント側（スマホ側）からWriteすると、以下のようなコマンドがリアルタイムで送られてきます。
```
WV,001B,E381A3E381A371.
WV,001B,393837363534333231.
```

## ライブラリ
- [https://github.com/greiman/SSD1306Ascii](https://github.com/greiman/SSD1306Ascii)
- [https://github.com/Tamakichi/Arduino-misakiUTF16](https://github.com/Tamakichi/Arduino-misakiUTF16)

## 参考
- [GR-COTTON 回路図](http://gadget.renesas.com/ja/product/documents/gr-cotton_sch.pdf)
- [BLE通信ソフトを作る ( Android Studio 2.3.3 + RN4020 )](http://www.hiramine.com/programming/blecommunicator/index.html)
- [RN4020 Bluetooth® Low Energy モジュールユーザガイド](http://akizukidenshi.com/download/ds/microchip/70005191A_JP.pdf)

