package com.gsralex.gflow.scheduler.client;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public interface ScheduleService {

    void ack(long taskId, boolean ok);

}
