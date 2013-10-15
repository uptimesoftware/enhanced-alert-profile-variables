package com.uptimesoftware.uptime.base.notification;

import java.util.Calendar;
import java.util.Properties;

import com.uptimesoftware.uptime.base.Uptime;
import com.uptimesoftware.uptime.base.erdc.ErdcCurrentStatus;
import com.uptimesoftware.uptime.base.erdc.ErdcSavedState;
import com.uptimesoftware.uptime.base.event.erdc.ErdcInstanceWrapper;
import com.uptimesoftware.uptime.base.event.erdc.NotificationStatus;
import com.uptimesoftware.uptime.base.users.User;
import com.uptimesoftware.uptime.base.users.UserLoader;
import com.uptimesoftware.uptime.base.util.UrlEncoder;
import com.uptimesoftware.uptime.configuration.UptimeConfig;

public class NotificationInfo {
        private NotificationStatus notificationType;
        private String hostname;
        private String displayName;
        private String instanceName;
        private String hostStatus;
        private String state;
        private String monitorOutput;
        private Calendar lastCheck;
        private Long acknowledgingUserId;
        private String acknowledgeMessage;
        private Long entityId;
        private String fullState;
        private ErdcSavedState previousState;
        
        private Long erdcInstanceId;
        private Long erdcBaseId;
        private boolean isVmwareObject;
        
        // MODS START
        // Added for Tenzing - Joel Pereira - Aug 12, 2010
        private int NotificationNumber;
        private String outageID;
        // MODS END

        public NotificationStatus getNotificationType() {
                return notificationType;
        }

        public void setNotificationType(NotificationStatus notificationType) {
                this.notificationType = notificationType;
        }

        public String getHostname() {
                return hostname;
        }

        public void setHostname(String hostname) {
                this.hostname = hostname;
        }

        public String getHostStatus() {
                return hostStatus;
        }

        public void setHostStatus(String hostStatus) {
                this.hostStatus = hostStatus;
        }

        public String getInstanceName() {
                return instanceName;
        }

        public void setInstanceName(String instanceName) {
                this.instanceName = instanceName;
        }

        public Calendar getLastCheck() {
                return lastCheck;
        }

        public void setLastCheck(Calendar lastCheck) {
                this.lastCheck = lastCheck;
        }

        public String getMonitorOutput() {
                return monitorOutput;
        }

        public void setMonitorOutput(String monitorOutput) {
                this.monitorOutput = monitorOutput;
        }

        public String getState() {
                return state;
        }

        public void setState(String state) {
                this.state = state;
        }

        public String getAcknowledgeMessage() {
                return acknowledgeMessage;
        }

        public void setAcknowledgeMessage(String acknowledgeMessage) {
                this.acknowledgeMessage = acknowledgeMessage;
        }

        public Long getAcknowledgingUserId() {
                return acknowledgingUserId;
        }

        public void setAcknowledgingUserId(Long acknowledgingUserId) {
                this.acknowledgingUserId = acknowledgingUserId;
        }

        public Long getEntityId() {
                return entityId;
        }

        public void setEntityId(Long entityId) {
                this.entityId = entityId;
        }

        public void setFullState(String fullState) {
                this.fullState = fullState;
        }

        public String getFullState() {
                return fullState;
        }

        public String getDisplayName() {
                return displayName;
        }

        public void setDisplayName(String displayName) {
                this.displayName = displayName;
        }

        String notificationTypeToDisplayString() {
                return notificationType.asTextString();
        }

        void populate(ErdcInstanceWrapper instanceWrapper, NotificationStatus notifyStatus, ErdcCurrentStatus currentStatus) {
                setInstanceName(instanceWrapper.getName());
                setHostname(instanceWrapper.getHostname());
                setDisplayName(instanceWrapper.getHostDisplayName());
                setEntityId(instanceWrapper.getEntityId());
                setHostStatus(instanceWrapper.getHostStatus());
                setNotificationType(notifyStatus);
                setLastCheck(currentStatus.getLastCheck());
                setMonitorOutput(currentStatus.getUntruncatedCurrentOutput());
                setState(currentStatus.getStateString());
                setFullState(currentStatus.getCurrentState().toString());
                setAcknowledgingUserId(currentStatus.getAcknowledgedByUserId());
                setAcknowledgeMessage(currentStatus.getAcknowledgedComment());
                setPreviousState(currentStatus.getPreviousState());
                erdcInstanceId = instanceWrapper.getId();
                erdcBaseId = instanceWrapper.getErdcBaseId();
                isVmwareObject = instanceWrapper.isVmwareObject();
                
                // MODS START
                // Added for Tenzing - Joel Pereira - Aug 12, 2010
                setNotificationNumber(currentStatus.getNotificationCount());
                String outagenum = new String();
                // outagenum should be a unique ID string "InstanceID+DateTime"
                outagenum = "" + currentStatus.getInstanceId() + currentStatus.getLastStateChangeDate().getTime();
                setOutageID(outagenum);
                // MODS END
        }

        public Long getErdcInstanceId() {
                return erdcInstanceId;
        }

        public Long getErdcBaseId() {
                return erdcBaseId;
        }

        public boolean isVmwareObject() {
                return isVmwareObject;
        }
        
        public String getHostUrl() {
                String coreUrl = getCoreUrl();
                StringBuilder url = new StringBuilder(coreUrl);

                url.append("/main.php?section=Profile&subsection=&dlsection=s_status&id=" + entityId + "&name="
                                + UrlEncoder.encode(hostname));

                return url.toString();
        }

        protected String getCoreUrl() {
                UptimeConfig config = Uptime.getConfiguration();
                return config.getHttpContext();
        }

        Properties populateProfileOptions(Properties profileOptions) {
                profileOptions.put("SVCNAME", getInstanceName());
                profileOptions.put("SVCSTATUS", getState());
                profileOptions.put("SVCOUTPUT", getMonitorOutput());
                String hostStatus = getHostStatus();
                if (hostStatus == null) {
                        hostStatus = "";
                }
                profileOptions.put("HOSTSTATUS", hostStatus);
                profileOptions.put("HOSTNAME", getHostname());
                profileOptions.put("DISPLAYNAME", getDisplayName());
                profileOptions.put("ALERTTYPE", notificationTypeToDisplayString());
                return profileOptions;
        }

        public AlertMessage getPopulatedAcknowledgeMessage() {

                String instanceName = getInstanceName();
                String displayName = getDisplayName();
                String subject = "Acknowledged [" + instanceName + " (" + displayName + ") ]";

                Long ackUserId = getAcknowledgingUserId();
                UserLoader loader = new UserLoader();
                loader.setId(ackUserId);
                User ackuser = loader.execute();

                String body = ackuser.getFirstName() + " " + ackuser.getLastName() + " (" + ackuser.getEmailAddress() + ")\n"
                                + "acknowledged the " + getState() + " status of " + instanceName + " (" + displayName + ")  \n"
                                + "with comment:\n\n" + getAcknowledgeMessage() + "\n";
                return new AlertMessage(subject, body);
        }

        public AlertMessage fillTemplate(AlertMessage template) {
                AlertTemplateFiller filler = new AlertTemplateFiller();
                return filler.fillTemplate(template, this);
        }

        public void setPreviousState(ErdcSavedState previousState) {
                this.previousState = previousState;
        }

        public ErdcSavedState getPreviousState() {
                return previousState;
        }
        
        // MODS START
        // Added for Tenzing - Joel Pereira - Aug 12, 2010
        public int getNotificationNumber() {
		return NotificationNumber;
	}
	public void setNotificationNumber(int alertNumber) {
		this.NotificationNumber = alertNumber;
	}
        public String getOutageID() {
		return outageID;
	}
	public void setOutageID(String outID) {
		this.outageID = outID;
	}
        // MODS END
}
