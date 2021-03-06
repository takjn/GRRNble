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
LIBS:RN4020
LIBS:open-project
LIBS:GRRNble-cache
EELAYER 25 0
EELAYER END
$Descr A3 16535 11693
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
L GND #PWR01
U 1 1 5AE6C36A
P 1950 1700
F 0 "#PWR01" H 1950 1450 50  0001 C CNN
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
L GND #PWR02
U 1 1 5AE6CC2F
P 2400 5100
F 0 "#PWR02" H 2400 4850 50  0001 C CNN
F 1 "GND" H 2400 4950 50  0000 C CNN
F 2 "" H 2400 5100 50  0001 C CNN
F 3 "" H 2400 5100 50  0001 C CNN
	1    2400 5100
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR03
U 1 1 5AE6CC57
P 2700 5100
F 0 "#PWR03" H 2700 4850 50  0001 C CNN
F 1 "GND" H 2700 4950 50  0000 C CNN
F 2 "" H 2700 5100 50  0001 C CNN
F 3 "" H 2700 5100 50  0001 C CNN
	1    2700 5100
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR04
U 1 1 5AE6CD36
P 2050 5100
F 0 "#PWR04" H 2050 4850 50  0001 C CNN
F 1 "GND" H 2050 4950 50  0000 C CNN
F 2 "" H 2050 5100 50  0001 C CNN
F 3 "" H 2050 5100 50  0001 C CNN
	1    2050 5100
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR05
U 1 1 5AE6D1AD
P 1700 5100
F 0 "#PWR05" H 1700 4850 50  0001 C CNN
F 1 "GND" H 1700 4950 50  0000 C CNN
F 2 "" H 1700 5100 50  0001 C CNN
F 3 "" H 1700 5100 50  0001 C CNN
	1    1700 5100
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR06
U 1 1 5AE6D1D5
P 1350 5100
F 0 "#PWR06" H 1350 4850 50  0001 C CNN
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
L +3.3V #PWR07
U 1 1 5AE6F171
P 1700 2500
F 0 "#PWR07" H 1700 2350 50  0001 C CNN
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
L +3.3V #PWR08
U 1 1 5AE70977
P 7900 3450
F 0 "#PWR08" H 7900 3300 50  0001 C CNN
F 1 "+3.3V" H 7900 3590 50  0000 C CNN
F 2 "" H 7900 3450 50  0001 C CNN
F 3 "" H 7900 3450 50  0001 C CNN
	1    7900 3450
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR09
U 1 1 5AE7099F
P 7900 3650
F 0 "#PWR09" H 7900 3400 50  0001 C CNN
F 1 "GND" H 7900 3500 50  0000 C CNN
F 2 "" H 7900 3650 50  0001 C CNN
F 3 "" H 7900 3650 50  0001 C CNN
	1    7900 3650
	1    0    0    -1  
$EndComp
$Comp
L SSD1306 U3
U 1 1 5AE71A97
P 15300 2200
F 0 "U3" H 15550 2500 60  0000 C CNN
F 1 "SSD1306" H 15550 2200 60  0000 C CNN
F 2 "SSD1306:SSD1306" H 15200 2200 60  0001 C CNN
F 3 "" H 15200 2200 60  0000 C CNN
	1    15300 2200
	1    0    0    -1  
$EndComp
$Comp
L C C5
U 1 1 5AE71FE2
P 14350 1000
F 0 "C5" H 14375 1100 50  0000 L CNN
F 1 "1.0uF" H 14375 900 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 14388 850 50  0001 C CNN
F 3 "" H 14350 1000 50  0001 C CNN
	1    14350 1000
	0    1    1    0   
$EndComp
$Comp
L C C6
U 1 1 5AE7209E
P 14350 1200
F 0 "C6" H 14375 1300 50  0000 L CNN
F 1 "1.0uF" H 14375 1100 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 14388 1050 50  0001 C CNN
F 3 "" H 14350 1200 50  0001 C CNN
	1    14350 1200
	0    1    1    0   
$EndComp
$Comp
L GND #PWR010
U 1 1 5AE72689
P 14150 4050
F 0 "#PWR010" H 14150 3800 50  0001 C CNN
F 1 "GND" H 14150 3900 50  0000 C CNN
F 2 "" H 14150 4050 50  0001 C CNN
F 3 "" H 14150 4050 50  0001 C CNN
	1    14150 4050
	1    0    0    -1  
$EndComp
$Comp
L +3.3V #PWR011
U 1 1 5AE726B7
P 13950 900
F 0 "#PWR011" H 13950 750 50  0001 C CNN
F 1 "+3.3V" H 13950 1040 50  0000 C CNN
F 2 "" H 13950 900 50  0001 C CNN
F 3 "" H 13950 900 50  0001 C CNN
	1    13950 900 
	1    0    0    -1  
$EndComp
Text GLabel 13100 2600 0    60   Input ~ 0
SCL
Text GLabel 13100 2700 0    60   Input ~ 0
SDA
$Comp
L R R4
U 1 1 5AE73585
P 14400 3400
F 0 "R4" V 14480 3400 50  0000 C CNN
F 1 "390k" V 14400 3400 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 14330 3400 50  0001 C CNN
F 3 "" H 14400 3400 50  0001 C CNN
	1    14400 3400
	0    1    1    0   
$EndComp
$Comp
L C C7
U 1 1 5AE737CF
P 14350 3600
F 0 "C7" H 14375 3700 50  0000 L CNN
F 1 "4.7uF" H 14375 3500 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 14388 3450 50  0001 C CNN
F 3 "" H 14350 3600 50  0001 C CNN
	1    14350 3600
	0    1    1    0   
$EndComp
$Comp
L C C8
U 1 1 5AE738C3
P 14350 3850
F 0 "C8" H 14375 3950 50  0000 L CNN
F 1 "2.2uF" H 14375 3750 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 14388 3700 50  0001 C CNN
F 3 "" H 14350 3850 50  0001 C CNN
	1    14350 3850
	0    1    1    0   
$EndComp
NoConn ~ 950  850 
NoConn ~ 950  1750
$Comp
L GND #PWR012
U 1 1 5AE77B71
P 15050 5800
F 0 "#PWR012" H 15050 5550 50  0001 C CNN
F 1 "GND" H 15050 5650 50  0000 C CNN
F 2 "" H 15050 5800 50  0001 C CNN
F 3 "" H 15050 5800 50  0001 C CNN
	1    15050 5800
	1    0    0    -1  
$EndComp
Text GLabel 14300 5200 0    60   Input ~ 0
SW1
Text GLabel 14300 5450 0    60   Input ~ 0
SW2
Text GLabel 14300 5700 0    60   Input ~ 0
SW3
Text GLabel 3400 6100 0    60   Input ~ 0
SW1
Text GLabel 7900 5400 2    60   Input ~ 0
SW2
Text GLabel 3400 5000 0    60   Input ~ 0
SW3
$Comp
L PWR_FLAG #FLG013
U 1 1 5AE7CD8E
P 7150 1050
F 0 "#FLG013" H 7150 1125 50  0001 C CNN
F 1 "PWR_FLAG" H 7150 1200 50  0000 C CNN
F 2 "" H 7150 1050 50  0001 C CNN
F 3 "" H 7150 1050 50  0001 C CNN
	1    7150 1050
	1    0    0    -1  
$EndComp
$Comp
L PWR_FLAG #FLG014
U 1 1 5AE7DBFC
P 2250 1400
F 0 "#FLG014" H 2250 1475 50  0001 C CNN
F 1 "PWR_FLAG" H 2250 1550 50  0000 C CNN
F 2 "" H 2250 1400 50  0001 C CNN
F 3 "" H 2250 1400 50  0001 C CNN
	1    2250 1400
	1    0    0    -1  
$EndComp
$Comp
L Battery_Cell BT1
U 1 1 5AE83402
P 5200 1300
F 0 "BT1" H 5300 1400 50  0000 L CNN
F 1 "Battery_Cell" H 5300 1300 50  0000 L CNN
F 2 "Connectors_Mini-Universal:MiniUniversalMate-N-LokSocket_2PinHorizontal" V 5200 1360 50  0001 C CNN
F 3 "" V 5200 1360 50  0001 C CNN
	1    5200 1300
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR015
U 1 1 5AE9D4C6
P 9450 5500
F 0 "#PWR015" H 9450 5250 50  0001 C CNN
F 1 "GND" H 9450 5350 50  0000 C CNN
F 2 "" H 9450 5500 50  0001 C CNN
F 3 "" H 9450 5500 50  0001 C CNN
	1    9450 5500
	1    0    0    -1  
$EndComp
$Comp
L SW_Push SW1
U 1 1 5AE9D89F
P 14650 5200
F 0 "SW1" H 14700 5300 50  0000 L CNN
F 1 "SW_Push" H 14650 5140 50  0000 C CNN
F 2 "misc:SKSC" H 14650 5400 50  0001 C CNN
F 3 "" H 14650 5400 50  0001 C CNN
	1    14650 5200
	1    0    0    -1  
$EndComp
$Comp
L SW_Push SW2
U 1 1 5AE9D8EA
P 14650 5450
F 0 "SW2" H 14700 5550 50  0000 L CNN
F 1 "SW_Push" H 14650 5390 50  0000 C CNN
F 2 "misc:SKSC" H 14650 5650 50  0001 C CNN
F 3 "" H 14650 5650 50  0001 C CNN
	1    14650 5450
	1    0    0    -1  
$EndComp
$Comp
L SW_Push SW3
U 1 1 5AE9D929
P 14650 5700
F 0 "SW3" H 14700 5800 50  0000 L CNN
F 1 "SW_Push" H 14650 5640 50  0000 C CNN
F 2 "misc:SKSC" H 14650 5900 50  0001 C CNN
F 3 "" H 14650 5900 50  0001 C CNN
	1    14650 5700
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
Text GLabel 8100 3100 2    60   Input ~ 0
D7/TXD
Text GLabel 8100 3200 2    60   Input ~ 0
D8/RXD
Text GLabel 8100 3700 2    60   Input ~ 0
BATT_LEVEL
Text GLabel 8100 3800 2    60   Input ~ 0
BATT_ENABLE
$Comp
L GND #PWR016
U 1 1 5AFF0309
P 14500 750
F 0 "#PWR016" H 14500 500 50  0001 C CNN
F 1 "GND" H 14500 600 50  0000 C CNN
F 2 "" H 14500 750 50  0001 C CNN
F 3 "" H 14500 750 50  0001 C CNN
	1    14500 750 
	1    0    0    -1  
$EndComp
$Comp
L C C10
U 1 1 5AFF0928
P 12900 1400
F 0 "C10" H 12925 1500 50  0000 L CNN
F 1 "1.0uF" H 12925 1300 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 12938 1250 50  0001 C CNN
F 3 "" H 12900 1400 50  0001 C CNN
	1    12900 1400
	0    1    1    0   
$EndComp
$Comp
L GND #PWR017
U 1 1 5AFF0C22
P 12650 1750
F 0 "#PWR017" H 12650 1500 50  0001 C CNN
F 1 "GND" H 12650 1600 50  0000 C CNN
F 2 "" H 12650 1750 50  0001 C CNN
F 3 "" H 12650 1750 50  0001 C CNN
	1    12650 1750
	1    0    0    -1  
$EndComp
$Comp
L R R7
U 1 1 5AFF0FC7
P 13750 1700
F 0 "R7" V 13830 1700 50  0000 C CNN
F 1 "10k" V 13750 1700 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 13680 1700 50  0001 C CNN
F 3 "" H 13750 1700 50  0001 C CNN
	1    13750 1700
	-1   0    0    1   
$EndComp
$Comp
L C C11
U 1 1 5AFF220C
P 13750 2150
F 0 "C11" H 13775 2250 50  0000 L CNN
F 1 "0.1uF" H 13775 2050 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 13788 2000 50  0001 C CNN
F 3 "" H 13750 2150 50  0001 C CNN
	1    13750 2150
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR018
U 1 1 5AFF2325
P 13750 2400
F 0 "#PWR018" H 13750 2150 50  0001 C CNN
F 1 "GND" H 13750 2250 50  0000 C CNN
F 2 "" H 13750 2400 50  0001 C CNN
F 3 "" H 13750 2400 50  0001 C CNN
	1    13750 2400
	1    0    0    -1  
$EndComp
$Comp
L R R6
U 1 1 5AFF27C0
P 13450 1700
F 0 "R6" V 13530 1700 50  0000 C CNN
F 1 "10k" V 13450 1700 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 13380 1700 50  0001 C CNN
F 3 "" H 13450 1700 50  0001 C CNN
	1    13450 1700
	1    0    0    -1  
$EndComp
$Comp
L R R5
U 1 1 5AFF2811
P 13200 1700
F 0 "R5" V 13280 1700 50  0000 C CNN
F 1 "10k" V 13200 1700 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 13130 1700 50  0001 C CNN
F 3 "" H 13200 1700 50  0001 C CNN
	1    13200 1700
	1    0    0    -1  
$EndComp
$Comp
L C C9
U 1 1 5B001BB7
P 12900 1100
F 0 "C9" H 12925 1200 50  0000 L CNN
F 1 "1.0uF" H 12925 1000 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 12938 950 50  0001 C CNN
F 3 "" H 12900 1100 50  0001 C CNN
	1    12900 1100
	0    1    1    0   
$EndComp
$Comp
L RN4020 U5
U 1 1 5B1FDACC
P 4350 8500
F 0 "U5" H 3850 9950 60  0000 L CNN
F 1 "RN4020" H 3850 7250 60  0000 L CNN
F 2 "library:RN4020" H 4150 8500 60  0001 C CNN
F 3 "" H 4150 8500 60  0000 C CNN
	1    4350 8500
	1    0    0    -1  
$EndComp
Text GLabel 5500 7200 2    60   Input ~ 0
D8/RXD
Text GLabel 5500 7300 2    60   Input ~ 0
D7/TXD
NoConn ~ 5150 7400
NoConn ~ 5150 7500
NoConn ~ 5150 7700
$Comp
L GND #PWR019
U 1 1 5B1FF555
P 5550 8100
F 0 "#PWR019" H 5550 7850 50  0001 C CNN
F 1 "GND" H 5550 7950 50  0000 C CNN
F 2 "" H 5550 8100 50  0001 C CNN
F 3 "" H 5550 8100 50  0001 C CNN
	1    5550 8100
	1    0    0    -1  
$EndComp
NoConn ~ 5150 8200
NoConn ~ 5150 8300
NoConn ~ 5150 8400
NoConn ~ 5150 8600
NoConn ~ 5150 8700
NoConn ~ 5150 8800
NoConn ~ 5150 8900
NoConn ~ 5150 9000
NoConn ~ 5150 9200
NoConn ~ 5150 9300
NoConn ~ 5150 9400
$Comp
L GND #PWR020
U 1 1 5B1FF969
P 3450 9700
F 0 "#PWR020" H 3450 9450 50  0001 C CNN
F 1 "GND" H 3450 9550 50  0000 C CNN
F 2 "" H 3450 9700 50  0001 C CNN
F 3 "" H 3450 9700 50  0001 C CNN
	1    3450 9700
	1    0    0    -1  
$EndComp
$Comp
L +3.3V #PWR021
U 1 1 5B20001C
P 3450 7100
F 0 "#PWR021" H 3450 6950 50  0001 C CNN
F 1 "+3.3V" H 3450 7240 50  0000 C CNN
F 2 "" H 3450 7100 50  0001 C CNN
F 3 "" H 3450 7100 50  0001 C CNN
	1    3450 7100
	1    0    0    -1  
$EndComp
$Comp
L MCP73831 U4
U 1 1 5B202F9B
P 3900 1050
F 0 "U4" H 3900 800 50  0000 C CNN
F 1 "MCP73831" H 3900 1250 50  0000 C CNN
F 2 "TO_SOT_Packages_SMD:SOT-23-5_HandSoldering" H 3900 1050 60  0001 C CNN
F 3 "" H 3900 1050 60  0000 C CNN
	1    3900 1050
	1    0    0    -1  
$EndComp
$Comp
L R R8
U 1 1 5B20300A
P 4550 1250
F 0 "R8" V 4630 1250 50  0000 C CNN
F 1 "22k" V 4550 1250 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 4480 1250 50  0001 C CNN
F 3 "" H 4550 1250 50  0001 C CNN
	1    4550 1250
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR022
U 1 1 5B203148
P 4350 1700
F 0 "#PWR022" H 4350 1450 50  0001 C CNN
F 1 "GND" H 4350 1550 50  0000 C CNN
F 2 "" H 4350 1700 50  0001 C CNN
F 3 "" H 4350 1700 50  0001 C CNN
	1    4350 1700
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR023
U 1 1 5B203472
P 4550 1700
F 0 "#PWR023" H 4550 1450 50  0001 C CNN
F 1 "GND" H 4550 1550 50  0000 C CNN
F 2 "" H 4550 1700 50  0001 C CNN
F 3 "" H 4550 1700 50  0001 C CNN
	1    4550 1700
	1    0    0    -1  
$EndComp
$Comp
L C C12
U 1 1 5B20530C
P 2700 1250
F 0 "C12" H 2725 1350 50  0000 L CNN
F 1 "4.7uF" H 2725 1150 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 2738 1100 50  0001 C CNN
F 3 "" H 2700 1250 50  0001 C CNN
	1    2700 1250
	1    0    0    -1  
$EndComp
$Comp
L C C13
U 1 1 5B205E97
P 4850 1250
F 0 "C13" H 4875 1350 50  0000 L CNN
F 1 "4.7uF" H 4875 1150 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 4888 1100 50  0001 C CNN
F 3 "" H 4850 1250 50  0001 C CNN
	1    4850 1250
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR024
U 1 1 5B2060A7
P 4850 1700
F 0 "#PWR024" H 4850 1450 50  0001 C CNN
F 1 "GND" H 4850 1550 50  0000 C CNN
F 2 "" H 4850 1700 50  0001 C CNN
F 3 "" H 4850 1700 50  0001 C CNN
	1    4850 1700
	1    0    0    -1  
$EndComp
$Comp
L Q_PMOS_GSD Q1
U 1 1 5B206668
P 6200 1100
F 0 "Q1" H 6400 1150 50  0000 L CNN
F 1 "DMG3415U" H 6400 1050 50  0000 L CNN
F 2 "TO_SOT_Packages_SMD:SOT-23" H 6400 1200 50  0001 C CNN
F 3 "" H 6200 1100 50  0001 C CNN
	1    6200 1100
	0    -1   1    0   
$EndComp
$Comp
L GND #PWR025
U 1 1 5B206A7A
P 5200 1700
F 0 "#PWR025" H 5200 1450 50  0001 C CNN
F 1 "GND" H 5200 1550 50  0000 C CNN
F 2 "" H 5200 1700 50  0001 C CNN
F 3 "" H 5200 1700 50  0001 C CNN
	1    5200 1700
	1    0    0    -1  
$EndComp
$Comp
L D_Schottky D1
U 1 1 5B207F34
P 6700 900
F 0 "D1" H 6700 1000 50  0000 C CNN
F 1 "CUS10F30" H 6700 800 50  0000 C CNN
F 2 "Diodes_SMD:D_SOD-323_HandSoldering" H 6700 900 50  0001 C CNN
F 3 "" H 6700 900 50  0001 C CNN
	1    6700 900 
	0    -1   -1   0   
$EndComp
$Comp
L R R10
U 1 1 5B2085F3
P 8050 900
F 0 "R10" V 8130 900 50  0000 C CNN
F 1 "47k" V 8050 900 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 7980 900 50  0001 C CNN
F 3 "" H 8050 900 50  0001 C CNN
	1    8050 900 
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR026
U 1 1 5B2088B2
P 8050 1700
F 0 "#PWR026" H 8050 1450 50  0001 C CNN
F 1 "GND" H 8050 1550 50  0000 C CNN
F 2 "" H 8050 1700 50  0001 C CNN
F 3 "" H 8050 1700 50  0001 C CNN
	1    8050 1700
	1    0    0    -1  
$EndComp
$Comp
L C C14
U 1 1 5B209940
P 6700 1450
F 0 "C14" H 6725 1550 50  0000 L CNN
F 1 "1.0uF" H 6725 1350 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 6738 1300 50  0001 C CNN
F 3 "" H 6700 1450 50  0001 C CNN
	1    6700 1450
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR027
U 1 1 5B209AFE
P 6700 1700
F 0 "#PWR027" H 6700 1450 50  0001 C CNN
F 1 "GND" H 6700 1550 50  0000 C CNN
F 2 "" H 6700 1700 50  0001 C CNN
F 3 "" H 6700 1700 50  0001 C CNN
	1    6700 1700
	1    0    0    -1  
$EndComp
$Comp
L +3.3V #PWR028
U 1 1 5B209E7F
P 11800 1050
F 0 "#PWR028" H 11800 900 50  0001 C CNN
F 1 "+3.3V" H 11800 1190 50  0000 C CNN
F 2 "" H 11800 1050 50  0001 C CNN
F 3 "" H 11800 1050 50  0001 C CNN
	1    11800 1050
	1    0    0    -1  
$EndComp
Text Notes 12550 10150 2    60   ~ 0
TBD: 3.3v LDO
$Comp
L NJM2865F33 U6
U 1 1 5B20FC5D
P 10600 1350
F 0 "U6" H 10600 1350 60  0000 C CNN
F 1 "NJM2865F33" H 10600 1650 60  0000 C CNN
F 2 "TO_SOT_Packages_SMD:SOT-23-5_HandSoldering" H 10600 1350 60  0001 C CNN
F 3 "" H 10600 1350 60  0001 C CNN
	1    10600 1350
	1    0    0    -1  
$EndComp
$Comp
L C C15
U 1 1 5B210186
P 9400 1350
F 0 "C15" H 9425 1450 50  0000 L CNN
F 1 "0.1uF" H 9425 1250 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 9438 1200 50  0001 C CNN
F 3 "" H 9400 1350 50  0001 C CNN
	1    9400 1350
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
	14650 1000 14500 1000
Wire Wire Line
	14650 1100 14150 1100
Wire Wire Line
	14150 1100 14150 1000
Wire Wire Line
	14150 1000 14200 1000
Wire Wire Line
	14650 1200 14500 1200
Wire Wire Line
	14650 1300 14150 1300
Wire Wire Line
	14150 1300 14150 1200
Wire Wire Line
	14150 1200 14200 1200
Wire Wire Line
	14650 1600 14150 1600
Wire Wire Line
	14150 1600 14150 4050
Wire Wire Line
	14650 1800 14150 1800
Connection ~ 14150 1800
Wire Wire Line
	14650 2000 14150 2000
Connection ~ 14150 2000
Wire Wire Line
	14650 2100 14150 2100
Connection ~ 14150 2100
Wire Wire Line
	14650 2300 14150 2300
Connection ~ 14150 2300
Wire Wire Line
	14650 2400 14150 2400
Connection ~ 14150 2400
Wire Wire Line
	14650 2500 14150 2500
Connection ~ 14150 2500
Wire Wire Line
	13100 2600 14650 2600
Wire Wire Line
	13100 2700 14650 2700
Wire Wire Line
	14650 2800 14550 2800
Wire Wire Line
	14550 2800 14550 2700
Connection ~ 14550 2700
Wire Wire Line
	14650 2900 14150 2900
Connection ~ 14150 2900
Wire Wire Line
	14650 3000 14150 3000
Connection ~ 14150 3000
Wire Wire Line
	14650 3100 14150 3100
Connection ~ 14150 3100
Wire Wire Line
	14650 3200 14150 3200
Connection ~ 14150 3200
Wire Wire Line
	14650 3300 14150 3300
Connection ~ 14150 3300
Wire Wire Line
	14550 3400 14650 3400
Wire Wire Line
	14250 3400 14150 3400
Connection ~ 14150 3400
Wire Wire Line
	14500 3500 14650 3500
Wire Wire Line
	14200 3850 14150 3850
Connection ~ 14150 3850
Wire Wire Line
	14500 3850 14550 3850
Wire Wire Line
	14550 3850 14550 3600
Wire Wire Line
	14550 3600 14650 3600
Wire Wire Line
	14150 4000 14650 4000
Wire Wire Line
	14600 4000 14600 3700
Wire Wire Line
	14600 3700 14650 3700
Connection ~ 14150 4000
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
	1350 1050 1950 1050
Wire Wire Line
	1950 1050 1950 1700
Connection ~ 1950 1550
Wire Wire Line
	2250 1550 2250 1400
Connection ~ 2250 1550
Wire Wire Line
	1950 1550 2700 1550
Wire Wire Line
	14300 5200 14450 5200
Wire Wire Line
	14300 5450 14450 5450
Wire Wire Line
	14300 5700 14450 5700
Wire Wire Line
	15050 5700 14850 5700
Wire Wire Line
	15050 5200 15050 5800
Wire Wire Line
	14850 5450 15050 5450
Connection ~ 15050 5700
Wire Wire Line
	14850 5200 15050 5200
Connection ~ 15050 5450
Wire Wire Line
	7400 5200 8500 5200
Wire Wire Line
	9300 5200 9450 5200
Wire Wire Line
	9450 5200 9450 5500
Wire Wire Line
	14650 900  14650 750 
Wire Wire Line
	14650 750  14500 750 
Wire Wire Line
	14650 4000 14650 3800
Connection ~ 14600 4000
Wire Wire Line
	13950 2200 14650 2200
Wire Wire Line
	13050 1400 14650 1400
Connection ~ 13950 1400
Connection ~ 13950 1700
Wire Wire Line
	14650 1700 13950 1700
Wire Wire Line
	13950 1700 13950 900 
Connection ~ 13750 1400
Wire Wire Line
	13750 1550 13750 1400
Wire Wire Line
	13750 1850 13750 2000
Wire Wire Line
	13750 1900 14650 1900
Wire Wire Line
	13950 2200 13950 1900
Connection ~ 13950 1900
Connection ~ 13750 1900
Wire Wire Line
	13750 2300 13750 2400
Wire Wire Line
	12650 1100 12650 1750
Wire Wire Line
	12650 1400 12750 1400
Wire Wire Line
	13450 1550 13450 1400
Connection ~ 13450 1400
Wire Wire Line
	13200 1550 13200 1400
Connection ~ 13200 1400
Wire Wire Line
	13450 1850 13450 2600
Connection ~ 13450 2600
Wire Wire Line
	13200 1850 13200 2700
Connection ~ 13200 2700
Wire Wire Line
	14150 3600 14200 3600
Connection ~ 14150 3600
Wire Wire Line
	14500 3600 14500 3500
Wire Wire Line
	12650 1100 12750 1100
Connection ~ 12650 1400
Wire Wire Line
	13050 1100 13950 1100
Connection ~ 13950 1100
Wire Wire Line
	5150 7200 5500 7200
Wire Wire Line
	5500 7300 5150 7300
Wire Wire Line
	5150 8000 5550 8000
Wire Wire Line
	5550 7900 5550 8100
Wire Wire Line
	5150 7900 5550 7900
Connection ~ 5550 8000
Wire Wire Line
	3650 9600 3450 9600
Wire Wire Line
	3450 9300 3450 9700
Wire Wire Line
	3650 9500 3450 9500
Connection ~ 3450 9600
Wire Wire Line
	3650 9400 3450 9400
Connection ~ 3450 9500
Wire Wire Line
	3650 9300 3450 9300
Connection ~ 3450 9400
Wire Wire Line
	3450 7100 3450 7200
Wire Wire Line
	3450 7200 3650 7200
Wire Wire Line
	4300 1150 4350 1150
Wire Wire Line
	4350 1150 4350 1700
Wire Wire Line
	4550 1050 4550 1100
Wire Wire Line
	4550 1400 4550 1700
Wire Wire Line
	4300 1050 4550 1050
Wire Wire Line
	2700 1550 2700 1400
Wire Wire Line
	2700 950  2700 1100
Wire Wire Line
	1850 950  3500 950 
Connection ~ 2700 950 
Connection ~ 3100 950 
Wire Wire Line
	4850 1700 4850 1400
Wire Wire Line
	4850 1100 4850 950 
Connection ~ 4850 950 
Wire Wire Line
	5200 1400 5200 1700
Wire Wire Line
	5200 850  5200 1100
Connection ~ 5200 950 
Wire Wire Line
	6200 650  6200 900 
Wire Wire Line
	6700 650  6700 750 
Connection ~ 6200 650 
Wire Wire Line
	1850 1250 1850 950 
Connection ~ 6700 650 
Wire Wire Line
	8050 1050 8050 1700
Wire Wire Line
	6700 1700 6700 1600
Wire Wire Line
	6700 1050 6700 1300
Connection ~ 6700 1200
Wire Wire Line
	11800 1200 11800 1050
Wire Wire Line
	7150 1200 7150 1050
Connection ~ 7150 1200
Wire Wire Line
	4300 950  6000 950 
Wire Wire Line
	6000 950  6000 1200
$Comp
L R R11
U 1 1 5B2116AD
P 9800 1350
F 0 "R11" V 9880 1350 50  0000 C CNN
F 1 "100k" V 9800 1350 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 9730 1350 50  0001 C CNN
F 3 "" H 9800 1350 50  0001 C CNN
	1    9800 1350
	1    0    0    -1  
$EndComp
Wire Wire Line
	9800 1500 10000 1500
Wire Wire Line
	9050 1200 10000 1200
Connection ~ 9800 1200
Connection ~ 9400 1200
$Comp
L GND #PWR029
U 1 1 5B211CA6
P 9400 1700
F 0 "#PWR029" H 9400 1450 50  0001 C CNN
F 1 "GND" H 9400 1550 50  0000 C CNN
F 2 "" H 9400 1700 50  0001 C CNN
F 3 "" H 9400 1700 50  0001 C CNN
	1    9400 1700
	1    0    0    -1  
$EndComp
Wire Wire Line
	9400 1500 9400 1700
$Comp
L GND #PWR030
U 1 1 5B211EB3
P 11200 1700
F 0 "#PWR030" H 11200 1450 50  0001 C CNN
F 1 "GND" H 11200 1550 50  0000 C CNN
F 2 "" H 11200 1700 50  0001 C CNN
F 3 "" H 11200 1700 50  0001 C CNN
	1    11200 1700
	1    0    0    -1  
$EndComp
Wire Wire Line
	11200 1500 11200 1700
$Comp
L C C16
U 1 1 5B21210D
P 11450 1350
F 0 "C16" H 11475 1450 50  0000 L CNN
F 1 "1.0uF" H 11475 1250 50  0000 L CNN
F 2 "Capacitors_SMD:C_0805_HandSoldering" H 11488 1200 50  0001 C CNN
F 3 "" H 11450 1350 50  0001 C CNN
	1    11450 1350
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR031
U 1 1 5B21224D
P 11450 1700
F 0 "#PWR031" H 11450 1450 50  0001 C CNN
F 1 "GND" H 11450 1550 50  0000 C CNN
F 2 "" H 11450 1700 50  0001 C CNN
F 3 "" H 11450 1700 50  0001 C CNN
	1    11450 1700
	1    0    0    -1  
$EndComp
Wire Wire Line
	11450 1700 11450 1500
Wire Wire Line
	11200 1200 11800 1200
Connection ~ 11450 1200
$Comp
L +5V #PWR032
U 1 1 5B213392
P 7550 1050
F 0 "#PWR032" H 7550 900 50  0001 C CNN
F 1 "+5V" H 7550 1190 50  0000 C CNN
F 2 "" H 7550 1050 50  0001 C CNN
F 3 "" H 7550 1050 50  0001 C CNN
	1    7550 1050
	1    0    0    -1  
$EndComp
$Comp
L +5V #PWR033
U 1 1 5B2133FE
P 9050 1050
F 0 "#PWR033" H 9050 900 50  0001 C CNN
F 1 "+5V" H 9050 1190 50  0000 C CNN
F 2 "" H 9050 1050 50  0001 C CNN
F 3 "" H 9050 1050 50  0001 C CNN
	1    9050 1050
	1    0    0    -1  
$EndComp
Wire Wire Line
	7550 1200 7550 1050
Wire Wire Line
	6400 1200 7550 1200
Wire Wire Line
	9050 1050 9050 1200
$Comp
L Q_NMOS_GSD Q2
U 1 1 5B213C07
P 10700 3950
F 0 "Q2" H 10900 4000 50  0000 L CNN
F 1 "BSS138" H 10900 3900 50  0000 L CNN
F 2 "TO_SOT_Packages_SMD:SOT-23" H 10900 4050 50  0001 C CNN
F 3 "" H 10700 3950 50  0001 C CNN
	1    10700 3950
	1    0    0    -1  
$EndComp
$Comp
L Q_PMOS_GSD Q3
U 1 1 5B2140EA
P 11400 3300
F 0 "Q3" H 11600 3350 50  0000 L CNN
F 1 "DMG3415U" H 11600 3250 50  0000 L CNN
F 2 "TO_SOT_Packages_SMD:SOT-23" H 11600 3400 50  0001 C CNN
F 3 "" H 11400 3300 50  0001 C CNN
	1    11400 3300
	1    0    0    1   
$EndComp
$Comp
L R R14
U 1 1 5B20FB85
P 10800 3500
F 0 "R14" V 10880 3500 50  0000 C CNN
F 1 "100R" V 10800 3500 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 10730 3500 50  0001 C CNN
F 3 "" H 10800 3500 50  0001 C CNN
	1    10800 3500
	1    0    0    -1  
$EndComp
$Comp
L R R12
U 1 1 5B20FCEB
P 10250 3950
F 0 "R12" V 10330 3950 50  0000 C CNN
F 1 "100R" V 10250 3950 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 10180 3950 50  0001 C CNN
F 3 "" H 10250 3950 50  0001 C CNN
	1    10250 3950
	0    1    1    0   
$EndComp
$Comp
L GND #PWR034
U 1 1 5B20FFCD
P 10800 4450
F 0 "#PWR034" H 10800 4200 50  0001 C CNN
F 1 "GND" H 10800 4300 50  0000 C CNN
F 2 "" H 10800 4450 50  0001 C CNN
F 3 "" H 10800 4450 50  0001 C CNN
	1    10800 4450
	1    0    0    -1  
$EndComp
Wire Wire Line
	10400 3950 10500 3950
Wire Wire Line
	10800 3750 10800 3650
Text GLabel 9900 3950 0    60   Input ~ 0
BATT_ENABLE
$Comp
L R R13
U 1 1 5B210658
P 10800 3100
F 0 "R13" V 10880 3100 50  0000 C CNN
F 1 "10k" V 10800 3100 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 10730 3100 50  0001 C CNN
F 3 "" H 10800 3100 50  0001 C CNN
	1    10800 3100
	1    0    0    -1  
$EndComp
Wire Wire Line
	10800 3350 10800 3250
Wire Wire Line
	10800 3300 11200 3300
Connection ~ 10800 3300
$Comp
L R R15
U 1 1 5B2109D8
P 11500 3750
F 0 "R15" V 11580 3750 50  0000 C CNN
F 1 "7K5" V 11500 3750 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 11430 3750 50  0001 C CNN
F 3 "" H 11500 3750 50  0001 C CNN
	1    11500 3750
	1    0    0    -1  
$EndComp
$Comp
L R R16
U 1 1 5B210A53
P 11500 4150
F 0 "R16" V 11580 4150 50  0000 C CNN
F 1 "2K7" V 11500 4150 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 11430 4150 50  0001 C CNN
F 3 "" H 11500 4150 50  0001 C CNN
	1    11500 4150
	1    0    0    -1  
$EndComp
Wire Wire Line
	10800 4450 10800 4150
Wire Wire Line
	11500 4000 11500 3900
Wire Wire Line
	11500 3600 11500 3500
$Comp
L +BATT #PWR035
U 1 1 5B2111BF
P 5200 850
F 0 "#PWR035" H 5200 700 50  0001 C CNN
F 1 "+BATT" H 5200 990 50  0000 C CNN
F 2 "" H 5200 850 50  0001 C CNN
F 3 "" H 5200 850 50  0001 C CNN
	1    5200 850 
	1    0    0    -1  
$EndComp
Wire Wire Line
	10800 2950 10800 2850
Wire Wire Line
	10800 2850 11500 2850
Wire Wire Line
	11500 2650 11500 3100
$Comp
L +BATT #PWR036
U 1 1 5B211666
P 11500 2650
F 0 "#PWR036" H 11500 2500 50  0001 C CNN
F 1 "+BATT" H 11500 2790 50  0000 C CNN
F 2 "" H 11500 2650 50  0001 C CNN
F 3 "" H 11500 2650 50  0001 C CNN
	1    11500 2650
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR037
U 1 1 5B2116E0
P 11500 4450
F 0 "#PWR037" H 11500 4200 50  0001 C CNN
F 1 "GND" H 11500 4300 50  0000 C CNN
F 2 "" H 11500 4450 50  0001 C CNN
F 3 "" H 11500 4450 50  0001 C CNN
	1    11500 4450
	1    0    0    -1  
$EndComp
Wire Wire Line
	11500 4450 11500 4300
Connection ~ 11500 2850
Text GLabel 11850 3950 2    60   Input ~ 0
BATT_LEVEL
Wire Wire Line
	11850 3950 11500 3950
Connection ~ 11500 3950
$Comp
L R R9
U 1 1 5B214A92
P 7800 650
F 0 "R9" V 7880 650 50  0000 C CNN
F 1 "47k" V 7800 650 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 7730 650 50  0001 C CNN
F 3 "" H 7800 650 50  0001 C CNN
	1    7800 650 
	0    1    1    0   
$EndComp
Wire Wire Line
	3100 650  7650 650 
Wire Wire Line
	7950 650  8350 650 
Wire Wire Line
	8050 650  8050 750 
Text GLabel 7900 5100 2    60   Input ~ 0
STAT
Wire Wire Line
	3100 950  3100 650 
Text GLabel 3250 1150 0    60   Input ~ 0
STAT
Text GLabel 7900 5300 2    60   Input ~ 0
VDD
Wire Wire Line
	3250 1150 3500 1150
Wire Wire Line
	7400 5100 7900 5100
Text GLabel 8350 650  2    60   Input ~ 0
VDD
Connection ~ 8050 650 
Wire Wire Line
	7400 5300 7900 5300
Wire Wire Line
	7400 3700 8100 3700
Wire Wire Line
	9900 3950 10100 3950
Wire Wire Line
	8100 3800 7400 3800
$Comp
L PWR_FLAG #FLG038
U 1 1 5B21F294
P 5600 850
F 0 "#FLG038" H 5600 925 50  0001 C CNN
F 1 "PWR_FLAG" H 5600 1000 50  0000 C CNN
F 2 "" H 5600 850 50  0001 C CNN
F 3 "" H 5600 850 50  0001 C CNN
	1    5600 850 
	1    0    0    -1  
$EndComp
Wire Wire Line
	5600 850  5600 950 
Connection ~ 5600 950 
Wire Wire Line
	7400 3100 8100 3100
Wire Wire Line
	8100 3200 7400 3200
$EndSCHEMATC
