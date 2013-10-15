This monitor adds the following variables to alert profiles:

$DAY$                - The day of the month the alert is sent (ex. 26)
$MONTH$              - The month the alert is sent (ex. 04)
$YEAR$               - The year the alert is sent (ex. 2011)
$12HOUR$             - The hour the alert is sent (ex. 03)
$24HOUR$             - The hour the alert is sent (ex. 15)
$MINUTES$            - The minute the alert is sent (ex. 26)
$AMPM$               - AM/PM

$DESCRIPTION$        - System description
$CUSTOM1$            - (1-4) Custom fields in each element
$CUSTOM2$            - (1-4) Custom fields in each element
$CUSTOM3$            - (1-4) Custom fields in each element
$CUSTOM4$            - (1-4) Custom fields in each element
$NOTIFICATIONNUMBER$ - Number of alerts that have been sent out (escalations)
$OUTAGENUM$          - unique number for each outage. Will not go up during escalations.

Installation:
- stop the up.time data collector (core)
- remove the old alert_mod.jar from the <uptime_dir>/core directory (if exists)
- place this updated alert_mod.jar in the <uptime_dir>/core directory and make sure it's owned by the "uptime" user if running on Linux/Solaris
- start the up.time data collector (core)

Done