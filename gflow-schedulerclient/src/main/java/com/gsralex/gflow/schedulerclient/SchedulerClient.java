package com.gsralex.gflow.schedulerclient;

public interface SchedulerClient {

    void scheduleAction(long actionId, String parameter);

    void scheduleGroup(long groupId, String parameter);

    void continueGroup(long groupId);

    void pauseGroup(long groupId);

    void stopGroup(long groupId);

}
