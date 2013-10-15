package com.uptimesoftware.uptime.base.notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
// MODS START
// Added for Tenzing - Joel Pereira - Aug 12, 2010
import com.uptimesoftware.uptime.base.entity.UptimeHostByNameLoader;
import com.uptimesoftware.uptime.base.entity.UptimeHost;
import org.apache.commons.httpclient.util.URIUtil;
// MODS END

public class AlertTemplateFiller {

        public AlertMessage fillTemplate(AlertMessage template, NotificationInfo info) {

                String subject = template.getSubject();
                if (subject == null) {
                        subject = "Missing subject";
                }
                String body = template.getBody();
                if (body == null) {
                        body = "Missing body";
                }

                TemplateParser templateParser = setupParser(info);

                templateParser.setBuffer(subject);
                String convertedSubject = templateParser.parse();

                templateParser.setBuffer(body);
                String convertedBody = templateParser.parse();

                return new AlertMessage(convertedSubject, convertedBody);
        }

        public TemplateParser setupParser(NotificationInfo info) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat datetimeFormat = new SimpleDateFormat("d/MMM/yyyy HH:mm");

                Calendar lastCheckCalendar = info.getLastCheck();

                TemplateParser templateParser = new TemplateParser();

                templateParser.setTag("$DATE$", dateFormat.format(lastCheckCalendar.getTime()));

                templateParser.setTag("$DATETIME$", datetimeFormat.format(lastCheckCalendar.getTime()));

                templateParser.setTag("$TIME$", timeFormat.format(lastCheckCalendar.getTime()));

                String typeString = info.notificationTypeToDisplayString();
                templateParser.setTag("$TYPE$", typeString);

                String hostnameString = formatNullString(info.getHostname());
                templateParser.setTag("$HOSTNAME$", hostnameString);

                String displaynameString = formatNullString(info.getDisplayName());
                templateParser.setTag("$DISPLAYNAME$", displaynameString);

                String hostStatusString = formatNullString(info.getHostStatus());
                templateParser.setTag("$HOSTSTATE$", hostStatusString);

                templateParser.setTag("$SERVICENAME$", info.getInstanceName());

                String serviceStateString = formatNullString(info.getFullState());
                templateParser.setTag("$SERVICESTATE$", serviceStateString);

                String outputString = formatNullString(info.getMonitorOutput());
                templateParser.setTag("$OUTPUT$", outputString);

                String statusUrl = formatNullString(info.getHostUrl());
                templateParser.setTag("$HOSTURL$", statusUrl);
                
                
                
                // MODS START
                // Added for PSCU - Joel Pereira - Jan 31, 2011
                SimpleDateFormat dayFormat = new SimpleDateFormat("d");
                SimpleDateFormat monthFormat = new SimpleDateFormat("M");
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat hour12Format = new SimpleDateFormat("HH");
                SimpleDateFormat hour24Format = new SimpleDateFormat("hh");
                SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
                SimpleDateFormat amPmFormat = new SimpleDateFormat("a");
                templateParser.setTag("$DAY$", dayFormat.format(lastCheckCalendar.getTime()));
                templateParser.setTag("$MONTH$", monthFormat.format(lastCheckCalendar.getTime()));
                templateParser.setTag("$YEAR$", yearFormat.format(lastCheckCalendar.getTime()));
                templateParser.setTag("$12HOUR$", hour12Format.format(lastCheckCalendar.getTime()));
                templateParser.setTag("$24HOUR$", hour24Format.format(lastCheckCalendar.getTime()));
                templateParser.setTag("$MINUTES$", minuteFormat.format(lastCheckCalendar.getTime()));
                templateParser.setTag("$AMPM$", amPmFormat.format(lastCheckCalendar.getTime()));
                
                // Added for Tenzing - Joel Pereira - Aug 12, 2010
                UptimeHostByNameLoader loader = new UptimeHostByNameLoader();
                if (hostnameString.equals("Test Host")) {
                    templateParser.setTag("$CUSTOM1$", "Test Host: Custom One");
                    templateParser.setTag("$CUSTOM2$", "Test Host: Custom Two");
                    templateParser.setTag("$CUSTOM3$", "Test Host: Custom Three");
                    templateParser.setTag("$CUSTOM4$", "Test Host: Custom Four");
                    templateParser.setTag("$DESCRIPTION$", "Test Host: Description Field");
                } else {
                    loader.setName(hostnameString);
                    UptimeHost entity = loader.load();
                    templateParser.setTag("$CUSTOM1$", formatNullString(entity.getCustom1()));
                    templateParser.setTag("$CUSTOM2$", formatNullString(entity.getCustom2()));
                    templateParser.setTag("$CUSTOM3$", formatNullString(entity.getCustom3()));
                    templateParser.setTag("$CUSTOM4$", formatNullString(entity.getCustom4()));
                    templateParser.setTag("$DESCRIPTION$", formatNullString(entity.getDescription()));
                }
                templateParser.setTag("$NOTIFICATIONNUMBER$", formatNullString(Integer.toString(info.getNotificationNumber())));
                templateParser.setTag("$OUTAGENUM$", formatNullString(info.getOutageID()));
                
                // New additions added for BCLC - Joel Pereira - Mar 9, 2012
                try {
                    outputString = URIUtil.encodeQuery(outputString);
                    // we also need to manually look for parenthesis () since they don't get converted by encodeQuery - Joel Pereira - May 10, 2012
                    outputString = outputString.replace("(", "%28");
                    outputString = outputString.replace(")", "%29");
                    templateParser.setTag("$URLOUTPUT$", outputString);
                    
                } catch (Exception e) {
                    templateParser.setTag("$URLOUTPUT$", e.getMessage());
                }
                
                try {
                    String str = URIUtil.encodeQuery(info.getInstanceName());
                    // we also need to manually look for parenthesis () since they don't get converted by encodeQuery - Joel Pereira - May 10, 2012
                    str = str.replace("(", "%28");
                    str = str.replace(")", "%29");
                    templateParser.setTag("$URLSERVICENAME$", str);
                } catch (Exception e) {
                    templateParser.setTag("$URLSERVICENAME$", e.getMessage());
                }
                // MODS END

                return templateParser;
        }

        private String formatNullString(String string) {
                if (string == null) {
                        return "n/a";
                }
                return string;
        }

}
