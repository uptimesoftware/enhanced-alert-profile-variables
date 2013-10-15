This monitor adds the following variables to alert profiles:

$DESCRIPTION$        - System description
$CUSTOMx$            - (1-4) Custom fields in each element
$NOTIFICATIONNUMBER$ - Number of alerts that have been sent out (escalations)
$OUTAGENUM$          - unique number for each outage. Will not go up during escalations.
$DAY$                - Alert date/time; day number (ex. 27)
$MONTH$              - Alert date/time; month (ex. July; 07)
$YEAR$               - Alert date/time; year (ex. 2013)
$12HOUR$             - Alert date/time; hours (ex. 11)
$24HOUR$             - Alert date/time; hours (ex. 23)
$MINUTES$            - Alert date/time; minutes (ex. 27)
$AMPM$               - Alert date/time; AM or PM (ex. PM)
$URLOUTPUT$          - Same as the $OUTPUT$ variable but urlencoded for HTTP
$URLSERVICENAME$     - Same as the $SERVICENAME$ variable but urlencoded for HTTP
$NOTIFICATIONNUMBER$ - Number of notifications for current alert. Used during escalation. (first alert is 1, second is 2, etc)
$OUTAGENUM$          - Unique identifier for each alert for each monitor. The identifier does not change while the monitor alert goes through escalation. Used for helpdesk software to track outages.

It is a customization that was made for Tenzing (Canada Web Hosting), BCLC and PSCU and should not be given to anyone else without proper authorization.
Joel (Aug 15, 2012) - Perhaps we can remove this "restriction".