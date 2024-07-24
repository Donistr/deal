package org.example.deal.quartz.job;

import org.example.deal.service.SetMainBorrowerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetMainBorrowerJob implements Job {

    private final SetMainBorrowerService setMainBorrowerService;

    @Autowired
    public SetMainBorrowerJob(SetMainBorrowerService setMainBorrowerService) {
        this.setMainBorrowerService = setMainBorrowerService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        setMainBorrowerService.resendFailedMessage();
    }

}
