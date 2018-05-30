EESchema Schematic File Version 2
LIBS:power
LIBS:device
LIBS:switches
LIBS:relays
LIBS:motors
LIBS:transistors
LIBS:conn
LIBS:linear
LIBS:regul
LIBS:74xx
LIBS:cmos4000
LIBS:adc-dac
LIBS:memory
LIBS:microcontrollers
LIBS:dsp
LIBS:analog_switches
LIBS:motorola
LIBS:audio
LIBS:interface
LIBS:digital-audio
LIBS:philips
LIBS:display
LIBS:cypress
LIBS:siliconi
LIBS:opto
LIBS:atmel
LIBS:contrib
LIBS:valves
LIBS:renesas
LIBS:JST-SH
LIBS:SSD1306
LIBS:misc
LIBS:GRRNble-cache
EELAYER 25 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L R R2
U 1 1 5AE66229
P 1550 2800
F 0 "R2" V 1630 2800 50  0000 C CNN
F 1 "12k" V 1550 2800 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 1480 2800 50  0001 C CNN
F 3 "" H 1550 2800 50  0001 C CNN
	1    1550 2800
	1    0    0    -1
$EndComp
$Comp
L R R3
U 1 1 5AE6627F
P 7700 4300
F 0 "R3" V 7780 4300 50  0000 C CNN
F 1 "2k" V 7700 4300 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 7630 4300 50  0001 C CNN
F 3 "" H 7700 4300 50  0001 C CNN
	1    7700 4300
	1    0    0    -1
$EndComp
$Comp
L R R1
U 1 1 5AE66307
P 1350 2800
F 0 "R1" V 1430 2800 50  0000 C CNN
F 1 "1k" V 1350 2800 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 1280 2800 50  0001 C CNN
F 3 "" H 1350 2800 50  0001 C CNN
	1    1350 2800
	1    0    0    -1
$EndComp
$Comp
L C C1
U 1 1 5AE66347
P 1350 4750
F 0 "C1" H 1375 4850 50  0000 L CNN
F 1 "0.47uF" H 1375 4650 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 1388 4600 50  0001 C CNN
F 3 "" H 1350 4750 50  0001 C CNN
	1    1350 4750
	1    0    0    -1
$EndComp
$Comp
L C C2
U 1 1 5AE66379
P 1700 4750
F 0 "C2" H 1725 4850 50  0000 L CNN
F 1 "0.1uF" H 1725 4650 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 1738 4600 50  0001 C CNN
F 3 "" H 1700 4750 50  0001 C CNN
	1    1700 4750
	1    0    0    -1
$EndComp
$Comp
L C C3
U 1 1 5AE663A9
P 2400 4850
F 0 "C3" H 2425 4950 50  0000 L CNN
F 1 "15pF" H 2425 4750 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 2438 4700 50  0001 C CNN
F 3 "" H 2400 4850 50  0001 C CNN
	1    2400 4850
	1    0    0    -1
$EndComp
$Comp
L C C4
U 1 1 5AE663FF
P 2700 4850
F 0 "C4" H 2725 4950 50  0000 L CNN
F 1 "15pF" H 2725 4750 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 2738 4700 50  0001 C CNN
F 3 "" H 2700 4850 50  0001 C CNN
	1    2700 4850
	1    0    0    -1
$EndComp
$Comp
L Crystal Y1
U 1 1 5AE6645B
P 2550 4450
F 0 "Y1" H 2550 4600 50  0000 C CNN
F 1 "32.768k" H 2550 4300 50  0000 C CNN
F 2 "Crystals:Crystal_SMD_G8-2pin_3.2x1.5mm_HandSoldering" H 2550 4450 50  0001 C CNN
F 3 "" H 2550 4450 50  0001 C CNN
	1    2550 4450
	1    0    0    -1
$EndComp
$Comp
L R5F100_REAL U1
U 1 1 5AE6A60B
P 5500 4400
F 0 "U1" H 7100 6350 60  0000 C CNN
F 1 "R5F100LJAFB" H 4100 2450 60  0000 C CNN
F 2 "Housings_QFP:LQFP-64_10x10mm_Pitch0.5mm" H 7100 6350 60  0001 C CNN
F 3 "" H 7100 6350 60  0000 C CNN
	1    5500 4400
	1    0    0    -1
$EndComp
$Comp
L JST_SH_06 J1
U 1 1 5AE6AB8D
P 1000 1300
F 0 "J1" V 950 1300 60  0000 C CNN
F 1 "JST_SH_06" V 1050 1300 60  0000 C CNN
F 2 "JST-SH:JST-SM-06B-SRSS-TB" H 1000 1300 60  0001 C CNN
F 3 "" H 1000 1300 60  0001 C CNN
	1    1000 1300
	-1   0    0    1
$EndComp
Text GLabel 1500 1450 2    60   Input ~ 0
RXD
Text GLabel 1500 1550 2    60   Input ~ 0
DTR
Text GLabel 1500 1350 2    60   Input ~ 0
TXD
Text GLabel 1500 1150 2    60   Input ~ 0
CTS
NoConn ~ 3600 2800
NoConn ~ 3600 2700
NoConn ~ 3600 2900
NoConn ~ 3600 3000
NoConn ~ 3600 4800
NoConn ~ 3600 4900
NoConn ~ 3600 5100
NoConn ~ 3600 5200
NoConn ~ 3600 5300
NoConn ~ 3600 5400
NoConn ~ 3600 5500
NoConn ~ 3600 5600
NoConn ~ 3600 5700
NoConn ~ 3600 5800
NoConn ~ 3600 5900
NoConn ~ 3600 6000
NoConn ~ 7400 6100
NoConn ~ 7400 6000
NoConn ~ 7400 5900
NoConn ~ 7400 5800
NoConn ~ 7400 5700
NoConn ~ 7400 5500
NoConn ~ 7400 5600
NoConn ~ 7400 5300
NoConn ~ 7400 5100
NoConn ~ 7400 4800
NoConn ~ 7400 4700
NoConn ~ 7400 4600
NoConn ~ 7400 4200
NoConn ~ 7400 4100
NoConn ~ 7400 4000
NoConn ~ 7400 3900
NoConn ~ 7400 3400
NoConn ~ 7400 3300
NoConn ~ 7400 3000
NoConn ~ 7400 2900
NoConn ~ 7400 2800
NoConn ~ 7400 2700
$Comp
L +3.3V #PWR01
U 1 1 5AE6C308
P 1850 850
F 0 "#PWR01" H 1850 700 50  0001 C CNN
F 1 "+3.3V" H 1850 990 50  0000 C CNN
F 2 "" H 1850 850 50  0001 C CNN
F 3 "" H 1850 850 50  0001 C CNN
	1    1850 850
	1    0    0    -1
$EndComp
$Comp
L GND #PWR02
U 1 1 5AE6C36A
P 1950 1700
F 0 "#PWR02" H 1950 1450 50  0001 C CNN
F 1 "GND" H 1950 1550 50  0000 C CNN
F 2 "" H 1950 1700 50  0001 C CNN
F 3 "" H 1950 1700 50  0001 C CNN
	1    1950 1700
	1    0    0    -1
$EndComp
Text GLabel 3400 4600 0    60   Input ~ 0
SCL
Text GLabel 3400 4750 0    60   Input ~ 0
SDA
$Comp
L GND #PWR03
U 1 1 5AE6CC2F
P 2400 5100
F 0 "#PWR03" H 2400 4850 50  0001 C CNN
F 1 "GND" H 2400 4950 50  0000 C CNN
F 2 "" H 2400 5100 50  0001 C CNN
F 3 "" H 2400 5100 50  0001 C CNN
	1    2400 5100
	1    0    0    -1
$EndComp
$Comp
L GND #PWR04
U 1 1 5AE6CC57
P 2700 5100
F 0 "#PWR04" H 2700 4850 50  0001 C CNN
F 1 "GND" H 2700 4950 50  0000 C CNN
F 2 "" H 2700 5100 50  0001 C CNN
F 3 "" H 2700 5100 50  0001 C CNN
	1    2700 5100
	1    0    0    -1
$EndComp
$Comp
L GND #PWR05
U 1 1 5AE6CD36
P 2050 5100
F 0 "#PWR05" H 2050 4850 50  0001 C CNN
F 1 "GND" H 2050 4950 50  0000 C CNN
F 2 "" H 2050 5100 50  0001 C CNN
F 3 "" H 2050 5100 50  0001 C CNN
	1    2050 5100
	1    0    0    -1
$EndComp
$Comp
L GND #PWR06
U 1 1 5AE6D1AD
P 1700 5100
F 0 "#PWR06" H 1700 4850 50  0001 C CNN
F 1 "GND" H 1700 4950 50  0000 C CNN
F 2 "" H 1700 5100 50  0001 C CNN
F 3 "" H 1700 5100 50  0001 C CNN
	1    1700 5100
	1    0    0    -1
$EndComp
$Comp
L GND #PWR07
U 1 1 5AE6D1D5
P 1350 5100
F 0 "#PWR07" H 1350 4850 50  0001 C CNN
F 1 "GND" H 1350 4950 50  0000 C CNN
F 2 "" H 1350 5100 50  0001 C CNN
F 3 "" H 1350 5100 50  0001 C CNN
	1    1350 5100
	1    0    0    -1
$EndComp
Text GLabel 1000 3100 0    60   Input ~ 0
CTS
Text GLabel 1000 3200 0    60   Input ~ 0
DTR
$Comp
L +3.3V #PWR08
U 1 1 5AE6F171
P 1700 2500
F 0 "#PWR08" H 1700 2350 50  0001 C CNN
F 1 "+3.3V" H 1700 2640 50  0000 C CNN
F 2 "" H 1700 2500 50  0001 C CNN
F 3 "" H 1700 2500 50  0001 C CNN
	1    1700 2500
	1    0    0    -1
$EndComp
Text GLabel 7900 4900 2    60   Input ~ 0
TXD
Text GLabel 7900 5000 2    60   Input ~ 0
RXD
$Comp
L +3.3V #PWR09
U 1 1 5AE70977
P 7900 3450
F 0 "#PWR09" H 7900 3300 50  0001 C CNN
F 1 "+3.3V" H 7900 3590 50  0000 C CNN
F 2 "" H 7900 3450 50  0001 C CNN
F 3 "" H 7900 3450 50  0001 C CNN
	1    7900 3450
	1    0    0    -1
$EndComp
$Comp
L GND #PWR010
U 1 1 5AE7099F
P 7900 3650
F 0 "#PWR010" H 7900 3400 50  0001 C CNN
F 1 "GND" H 7900 3500 50  0000 C CNN
F 2 "" H 7900 3650 50  0001 C CNN
F 3 "" H 7900 3650 50  0001 C CNN
	1    7900 3650
	1    0    0    -1
$EndComp
$Comp
L SSD1306 U3
U 1 1 5AE71A97
P 10450 2100
F 0 "U3" H 10700 2400 60  0000 C CNN
F 1 "SSD1306" H 10700 2100 60  0000 C CNN
F 2 "SSD1306:SSD1306" H 10350 2100 60  0001 C CNN
F 3 "" H 10350 2100 60  0000 C CNN
	1    10450 2100
	1    0    0    -1
$EndComp
$Comp
L C C5
U 1 1 5AE71FE2
P 9500 900
F 0 "C5" H 9525 1000 50  0000 L CNN
F 1 "1.0uF" H 9525 800 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 9538 750 50  0001 C CNN
F 3 "" H 9500 900 50  0001 C CNN
	1    9500 900
	0    1    1    0
$EndComp
$Comp
L C C6
U 1 1 5AE7209E
P 9500 1100
F 0 "C6" H 9525 1200 50  0000 L CNN
F 1 "1.0uF" H 9525 1000 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 9538 950 50  0001 C CNN
F 3 "" H 9500 1100 50  0001 C CNN
	1    9500 1100
	0    1    1    0
$EndComp
$Comp
L GND #PWR011
U 1 1 5AE72689
P 9300 3950
F 0 "#PWR011" H 9300 3700 50  0001 C CNN
F 1 "GND" H 9300 3800 50  0000 C CNN
F 2 "" H 9300 3950 50  0001 C CNN
F 3 "" H 9300 3950 50  0001 C CNN
	1    9300 3950
	1    0    0    -1
$EndComp
$Comp
L +3.3V #PWR012
U 1 1 5AE726B7
P 9100 800
F 0 "#PWR012" H 9100 650 50  0001 C CNN
F 1 "+3.3V" H 9100 940 50  0000 C CNN
F 2 "" H 9100 800 50  0001 C CNN
F 3 "" H 9100 800 50  0001 C CNN
	1    9100 800
	1    0    0    -1
$EndComp
Text GLabel 8250 2500 0    60   Input ~ 0
SCL
Text GLabel 8250 2600 0    60   Input ~ 0
SDA
$Comp
L R R4
U 1 1 5AE73585
P 9550 3300
F 0 "R4" V 9630 3300 50  0000 C CNN
F 1 "390k" V 9550 3300 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 9480 3300 50  0001 C CNN
F 3 "" H 9550 3300 50  0001 C CNN
	1    9550 3300
	0    1    1    0
$EndComp
$Comp
L C C7
U 1 1 5AE737CF
P 9500 3500
F 0 "C7" H 9525 3600 50  0000 L CNN
F 1 "4.7uF" H 9525 3400 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 9538 3350 50  0001 C CNN
F 3 "" H 9500 3500 50  0001 C CNN
	1    9500 3500
	0    1    1    0
$EndComp
$Comp
L C C8
U 1 1 5AE738C3
P 9500 3750
F 0 "C8" H 9525 3850 50  0000 L CNN
F 1 "2.2uF" H 9525 3650 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 9538 3600 50  0001 C CNN
F 3 "" H 9500 3750 50  0001 C CNN
	1    9500 3750
	0    1    1    0
$EndComp
NoConn ~ 950  850
NoConn ~ 950  1750
$Comp
L GND #PWR013
U 1 1 5AE77B71
P 10600 5100
F 0 "#PWR013" H 10600 4850 50  0001 C CNN
F 1 "GND" H 10600 4950 50  0000 C CNN
F 2 "" H 10600 5100 50  0001 C CNN
F 3 "" H 10600 5100 50  0001 C CNN
	1    10600 5100
	1    0    0    -1
$EndComp
Text GLabel 9850 4500 0    60   Input ~ 0
SW1
Text GLabel 9850 4750 0    60   Input ~ 0
SW2
Text GLabel 9850 5000 0    60   Input ~ 0
SW3
Text GLabel 3400 6100 0    60   Input ~ 0
SW1
Text GLabel 7900 5400 2    60   Input ~ 0
SW2
Text GLabel 3400 5000 0    60   Input ~ 0
SW3
$Comp
L PWR_FLAG #FLG014
U 1 1 5AE7CD8E
P 2250 850
F 0 "#FLG014" H 2250 925 50  0001 C CNN
F 1 "PWR_FLAG" H 2250 1000 50  0000 C CNN
F 2 "" H 2250 850 50  0001 C CNN
F 3 "" H 2250 850 50  0001 C CNN
	1    2250 850
	1    0    0    -1
$EndComp
$Comp
L PWR_FLAG #FLG015
U 1 1 5AE7DBFC
P 2250 1400
F 0 "#FLG015" H 2250 1475 50  0001 C CNN
F 1 "PWR_FLAG" H 2250 1550 50  0000 C CNN
F 2 "" H 2250 1400 50  0001 C CNN
F 3 "" H 2250 1400 50  0001 C CNN
	1    2250 1400
	1    0    0    -1
$EndComp
$Comp
L Battery_Cell BT1
U 1 1 5AE83402
P 2700 1300
F 0 "BT1" H 2800 1400 50  0000 L CNN
F 1 "Battery_Cell" H 2800 1300 50  0000 L CNN
F 2 "Battery_Holders:Keystone_3034_1x20mm-CoinCell" V 2700 1360 50  0001 C CNN
F 3 "" V 2700 1360 50  0001 C CNN
	1    2700 1300
	1    0    0    -1
$EndComp
$Comp
L GND #PWR016
U 1 1 5AE9D4C6
P 9450 5500
F 0 "#PWR016" H 9450 5250 50  0001 C CNN
F 1 "GND" H 9450 5350 50  0000 C CNN
F 2 "" H 9450 5500 50  0001 C CNN
F 3 "" H 9450 5500 50  0001 C CNN
	1    9450 5500
	1    0    0    -1
$EndComp
$Comp
L SW_Push SW1
U 1 1 5AE9D89F
P 10200 4500
F 0 "SW1" H 10250 4600 50  0000 L CNN
F 1 "SW_Push" H 10200 4440 50  0000 C CNN
F 2 "misc:SKSC" H 10200 4700 50  0001 C CNN
F 3 "" H 10200 4700 50  0001 C CNN
	1    10200 4500
	1    0    0    -1
$EndComp
$Comp
L SW_Push SW2
U 1 1 5AE9D8EA
P 10200 4750
F 0 "SW2" H 10250 4850 50  0000 L CNN
F 1 "SW_Push" H 10200 4690 50  0000 C CNN
F 2 "misc:SKSC" H 10200 4950 50  0001 C CNN
F 3 "" H 10200 4950 50  0001 C CNN
	1    10200 4750
	1    0    0    -1
$EndComp
$Comp
L SW_Push SW3
U 1 1 5AE9D929
P 10200 5000
F 0 "SW3" H 10250 5100 50  0000 L CNN
F 1 "SW_Push" H 10200 4940 50  0000 C CNN
F 2 "misc:SKSC" H 10200 5200 50  0001 C CNN
F 3 "" H 10200 5200 50  0001 C CNN
	1    10200 5000
	1    0    0    -1
$EndComp
$Comp
L BUZZER U2
U 1 1 5AE9F4E8
P 8900 5300
F 0 "U2" H 8900 5400 60  0000 C CNN
F 1 "BUZZER" H 8900 5300 60  0000 C CNN
F 2 "misc:BZ7025_3pin" H 8900 5300 60  0001 C CNN
F 3 "" H 8900 5300 60  0000 C CNN
	1    8900 5300
	1    0    0    -1
$EndComp
$Comp
L JST_SH_04 J2
U 1 1 5AEA9282
P 7050 1150
F 0 "J2" V 7050 1150 60  0000 C CNN
F 1 "JST_SH_04" V 7250 1150 60  0000 C CNN
F 2 "JST-SH:JST-SM-04B-SRSS-TB" H 7050 1150 60  0001 C CNN
F 3 "" H 7050 1150 60  0001 C CNN
	1    7050 1150
	1    0    0    -1
$EndComp
Text GLabel 6500 1300 0    60   Input ~ 0
D7
Text GLabel 6500 1200 0    60   Input ~ 0
D8
Text GLabel 6500 1100 0    60   Input ~ 0
A0
Text GLabel 6500 1000 0    60   Input ~ 0
A1
Text GLabel 7500 3100 2    60   Input ~ 0
D7
Text GLabel 7500 3200 2    60   Input ~ 0
D8
Text GLabel 7500 3700 2    60   Input ~ 0
A0
Text GLabel 7500 3800 2    60   Input ~ 0
A1
NoConn ~ 7100 800
NoConn ~ 7100 1500
$Comp
L GND #PWR017
U 1 1 5AFF0309
P 9650 650
F 0 "#PWR017" H 9650 400 50  0001 C CNN
F 1 "GND" H 9650 500 50  0000 C CNN
F 2 "" H 9650 650 50  0001 C CNN
F 3 "" H 9650 650 50  0001 C CNN
	1    9650 650
	1    0    0    -1
$EndComp
$Comp
L C C10
U 1 1 5AFF0928
P 8050 1300
F 0 "C10" H 8075 1400 50  0000 L CNN
F 1 "1.0uF" H 8075 1200 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 8088 1150 50  0001 C CNN
F 3 "" H 8050 1300 50  0001 C CNN
	1    8050 1300
	0    1    1    0
$EndComp
$Comp
L GND #PWR018
U 1 1 5AFF0C22
P 7800 1650
F 0 "#PWR018" H 7800 1400 50  0001 C CNN
F 1 "GND" H 7800 1500 50  0000 C CNN
F 2 "" H 7800 1650 50  0001 C CNN
F 3 "" H 7800 1650 50  0001 C CNN
	1    7800 1650
	1    0    0    -1
$EndComp
$Comp
L R R7
U 1 1 5AFF0FC7
P 8900 1600
F 0 "R7" V 8980 1600 50  0000 C CNN
F 1 "10k" V 8900 1600 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 8830 1600 50  0001 C CNN
F 3 "" H 8900 1600 50  0001 C CNN
	1    8900 1600
	-1   0    0    1
$EndComp
$Comp
L C C11
U 1 1 5AFF220C
P 8900 2050
F 0 "C11" H 8925 2150 50  0000 L CNN
F 1 "0.1uF" H 8925 1950 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 8938 1900 50  0001 C CNN
F 3 "" H 8900 2050 50  0001 C CNN
	1    8900 2050
	1    0    0    -1
$EndComp
$Comp
L GND #PWR019
U 1 1 5AFF2325
P 8900 2300
F 0 "#PWR019" H 8900 2050 50  0001 C CNN
F 1 "GND" H 8900 2150 50  0000 C CNN
F 2 "" H 8900 2300 50  0001 C CNN
F 3 "" H 8900 2300 50  0001 C CNN
	1    8900 2300
	1    0    0    -1
$EndComp
$Comp
L R R6
U 1 1 5AFF27C0
P 8600 1600
F 0 "R6" V 8680 1600 50  0000 C CNN
F 1 "10k" V 8600 1600 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 8530 1600 50  0001 C CNN
F 3 "" H 8600 1600 50  0001 C CNN
	1    8600 1600
	1    0    0    -1
$EndComp
$Comp
L R R5
U 1 1 5AFF2811
P 8350 1600
F 0 "R5" V 8430 1600 50  0000 C CNN
F 1 "10k" V 8350 1600 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 8280 1600 50  0001 C CNN
F 3 "" H 8350 1600 50  0001 C CNN
	1    8350 1600
	1    0    0    -1
$EndComp
Wire Wire Line
	1500 1150 1350 1150
Wire Wire Line
	1500 1350 1350 1350
Wire Wire Line
	1500 1450 1350 1450
Wire Wire Line
	1500 1550 1350 1550
Wire Wire Line
	3400 4600 3600 4600
Wire Wire Line
	3600 4700 3500 4700
Wire Wire Line
	3500 4700 3500 4750
Wire Wire Line
	3500 4750 3400 4750
Wire Wire Line
	2700 3400 2700 4700
Wire Wire Line
	2400 3300 2400 4700
Wire Wire Line
	2700 5100 2700 5000
Wire Wire Line
	2400 5100 2400 5000
Wire Wire Line
	3600 3500 2050 3500
Wire Wire Line
	2050 3500 2050 5100
Wire Wire Line
	3600 3600 2050 3600
Connection ~ 2050 3600
Wire Wire Line
	3600 3700 2050 3700
Connection ~ 2050 3700
Wire Wire Line
	2050 3900 3600 3900
Connection ~ 2050 3900
Wire Wire Line
	3600 4000 3500 4000
Wire Wire Line
	3500 4000 3500 3900
Connection ~ 3500 3900
Wire Wire Line
	1350 5100 1350 4900
Wire Wire Line
	1350 4600 1350 3800
Wire Wire Line
	1350 3800 3600 3800
Wire Wire Line
	1700 5100 1700 4900
Wire Wire Line
	1700 4100 3600 4100
Wire Wire Line
	1700 2500 1700 4600
Wire Wire Line
	3600 4200 3500 4200
Wire Wire Line
	3500 4200 3500 4100
Connection ~ 3500 4100
Wire Wire Line
	3600 3300 2400 3300
Connection ~ 2400 4450
Wire Wire Line
	3600 3400 2700 3400
Connection ~ 2700 4450
Wire Wire Line
	1000 3100 3600 3100
Wire Wire Line
	1000 3200 3600 3200
Connection ~ 1700 4100
Wire Wire Line
	1550 2650 1550 2600
Wire Wire Line
	1350 2600 1700 2600
Connection ~ 1700 2600
Wire Wire Line
	1350 2650 1350 2600
Connection ~ 1550 2600
Wire Wire Line
	1550 2950 1550 3100
Connection ~ 1550 3100
Wire Wire Line
	1350 2950 1350 3200
Connection ~ 1350 3200
Wire Wire Line
	7400 4900 7900 4900
Wire Wire Line
	7900 5000 7400 5000
Wire Wire Line
	1150 3100 1150 2200
Wire Wire Line
	1150 2200 7700 2200
Wire Wire Line
	7700 2200 7700 4150
Connection ~ 1150 3100
Wire Wire Line
	7400 3600 7900 3600
Wire Wire Line
	7700 4900 7700 4450
Connection ~ 7700 4900
Wire Wire Line
	9800 900  9650 900
Wire Wire Line
	9800 1000 9300 1000
Wire Wire Line
	9300 1000 9300 900
Wire Wire Line
	9300 900  9350 900
Wire Wire Line
	9800 1100 9650 1100
Wire Wire Line
	9800 1200 9300 1200
Wire Wire Line
	9300 1200 9300 1100
Wire Wire Line
	9300 1100 9350 1100
Wire Wire Line
	9800 1500 9300 1500
Wire Wire Line
	9300 1500 9300 3950
Wire Wire Line
	9800 1700 9300 1700
Connection ~ 9300 1700
Wire Wire Line
	9800 1900 9300 1900
Connection ~ 9300 1900
Wire Wire Line
	9800 2000 9300 2000
Connection ~ 9300 2000
Wire Wire Line
	9800 2200 9300 2200
Connection ~ 9300 2200
Wire Wire Line
	9800 2300 9300 2300
Connection ~ 9300 2300
Wire Wire Line
	9800 2400 9300 2400
Connection ~ 9300 2400
Wire Wire Line
	8250 2500 9800 2500
Wire Wire Line
	8250 2600 9800 2600
Wire Wire Line
	9800 2700 9700 2700
Wire Wire Line
	9700 2700 9700 2600
Connection ~ 9700 2600
Wire Wire Line
	9800 2800 9300 2800
Connection ~ 9300 2800
Wire Wire Line
	9800 2900 9300 2900
Connection ~ 9300 2900
Wire Wire Line
	9800 3000 9300 3000
Connection ~ 9300 3000
Wire Wire Line
	9800 3100 9300 3100
Connection ~ 9300 3100
Wire Wire Line
	9800 3200 9300 3200
Connection ~ 9300 3200
Wire Wire Line
	9700 3300 9800 3300
Wire Wire Line
	9400 3300 9300 3300
Connection ~ 9300 3300
Wire Wire Line
	9650 3400 9800 3400
Wire Wire Line
	9350 3750 9300 3750
Connection ~ 9300 3750
Wire Wire Line
	9650 3750 9700 3750
Wire Wire Line
	9700 3750 9700 3500
Wire Wire Line
	9700 3500 9800 3500
Wire Wire Line
	9300 3900 9800 3900
Wire Wire Line
	9750 3900 9750 3600
Wire Wire Line
	9750 3600 9800 3600
Connection ~ 9300 3900
Wire Wire Line
	3400 6100 3600 6100
Wire Wire Line
	7900 5400 7400 5400
Wire Wire Line
	3400 5000 3600 5000
Wire Wire Line
	7900 3450 7900 3500
Wire Wire Line
	7900 3500 7400 3500
Wire Wire Line
	1850 1250 1350 1250
Wire Wire Line
	7900 3600 7900 3650
Wire Wire Line
	1850 850  1850 1250
Wire Wire Line
	1350 1050 1950 1050
Wire Wire Line
	1950 1050 1950 1700
Wire Wire Line
	2250 850  2250 950
Connection ~ 1850 950
Connection ~ 1950 1550
Wire Wire Line
	2250 1550 2250 1400
Connection ~ 2250 1550
Connection ~ 2250 950
Wire Wire Line
	1850 950  2700 950
Wire Wire Line
	2700 950  2700 1100
Wire Wire Line
	1950 1550 2700 1550
Wire Wire Line
	2700 1550 2700 1400
Wire Wire Line
	9850 4500 10000 4500
Wire Wire Line
	9850 4750 10000 4750
Wire Wire Line
	9850 5000 10000 5000
Wire Wire Line
	10600 5000 10400 5000
Wire Wire Line
	10600 4500 10600 5100
Wire Wire Line
	10400 4750 10600 4750
Connection ~ 10600 5000
Wire Wire Line
	10400 4500 10600 4500
Connection ~ 10600 4750
Wire Wire Line
	7400 5200 8500 5200
Wire Wire Line
	9300 5200 9450 5200
Wire Wire Line
	9450 5200 9450 5500
Wire Wire Line
	7400 3100 7500 3100
Wire Wire Line
	7500 3200 7400 3200
Wire Wire Line
	7400 3700 7500 3700
Wire Wire Line
	7500 3800 7400 3800
Wire Wire Line
	6500 1000 6700 1000
Wire Wire Line
	6700 1100 6500 1100
Wire Wire Line
	6700 1200 6500 1200
Wire Wire Line
	6700 1300 6500 1300
Wire Wire Line
	9800 800  9800 650
Wire Wire Line
	9800 650  9650 650
Wire Wire Line
	9800 3900 9800 3700
Connection ~ 9750 3900
Wire Wire Line
	9100 2100 9800 2100
Wire Wire Line
	8200 1300 9800 1300
Connection ~ 9100 1300
Connection ~ 9100 1600
Wire Wire Line
	9800 1600 9100 1600
Wire Wire Line
	9100 1600 9100 800
Connection ~ 8900 1300
Wire Wire Line
	8900 1450 8900 1300
Wire Wire Line
	8900 1750 8900 1900
Wire Wire Line
	8900 1800 9800 1800
Wire Wire Line
	9100 2100 9100 1800
Connection ~ 9100 1800
Connection ~ 8900 1800
Wire Wire Line
	8900 2200 8900 2300
Wire Wire Line
	7800 1000 7800 1650
Wire Wire Line
	7800 1300 7900 1300
Wire Wire Line
	8600 1450 8600 1300
Connection ~ 8600 1300
Wire Wire Line
	8350 1450 8350 1300
Connection ~ 8350 1300
Wire Wire Line
	8600 1750 8600 2500
Connection ~ 8600 2500
Wire Wire Line
	8350 1750 8350 2600
Connection ~ 8350 2600
Wire Wire Line
	9300 3500 9350 3500
Connection ~ 9300 3500
Wire Wire Line
	9650 3500 9650 3400
$Comp
L C C9
U 1 1 5B001BB7
P 8050 1000
F 0 "C9" H 8075 1100 50  0000 L CNN
F 1 "1.0uF" H 8075 900 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 8088 850 50  0001 C CNN
F 3 "" H 8050 1000 50  0001 C CNN
	1    8050 1000
	0    1    1    0
$EndComp
Wire Wire Line
	7800 1000 7900 1000
Connection ~ 7800 1300
Wire Wire Line
	8200 1000 9100 1000
Connection ~ 9100 1000
$EndSCHEMATC
