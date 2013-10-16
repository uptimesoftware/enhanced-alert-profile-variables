# Enhanced Alert Profile Variables

See http://uptimesoftware.github.io for more information.

### Tags 
 plugin   alerting  

### Category

{ page.category }}

### Version Compatibility


  
    * Enhanced Alert Profile Variables 2.5 - 7.2
  

  
    * Enhanced Alert Profile Variables 2.3 - 7.1
  

  
    * Enhanced Alert Profile Variables 2.1 - 7.0
  

  
    * Enhanced Alert Profile Variables 2.0 - 6.0
  

  
    * Enhanced Alert Profile Variables 1.0 - 5.5, 5.4, 5.3
  


### Description
This mod adds more variables to alert profiles. The variables are listed below.


### Supported Monitoring Stations

7.2, 7.1, 7.0, 6.0, 5.5, 5.4, 5.3

### Supported Agents
None; no agent required

### Installation Notes
<p><a href="https://github.com/uptimesoftware/uptime-plugin-manager">Install using the up.time Plugin Manager</a></p>


### Dependencies
<p>n/a</p>


### Input Variables


### Output Variables

* $DESCRIPTION$ - System description* $CUSTOMx$ - (1-4) Custom fields in each element* $NOTIFICATIONNUMBER$ - Number of alerts that have been sent out (escalations)* $OUTAGENUM$ - unique number for each outage. Will not go up during escalations.* $DAY$ - Alert date/time; day number (ex. 27)* $MONTH$ - Alert date/time; month (ex. July; 07)* $YEAR$ - Alert date/time; year (ex. 2013)* $12HOUR$ - Alert date/time; hours (ex. 11)* $24HOUR$ - Alert date/time; hours (ex. 23)* $MINUTES$ - Alert date/time; minutes (ex. 27)* $AMPM$ - Alert date/time; AM or PM (ex. PM)* $URLOUTPUT$ - Same as the $OUTPUT$ variable but urlencoded for HTTP* $URLSERVICENAME$ - Same as the $SERVICENAME$ variable but urlencoded for HTTP* $NOTIFICATIONNUMBER$ - Number of notifications for current alert. Used during escalation. (first alert is 1, second is 2, etc)* $OUTAGENUM$ - Unique identifier for each alert for each monitor. The identifier does not change until the status changes.

### Languages Used
* Java

