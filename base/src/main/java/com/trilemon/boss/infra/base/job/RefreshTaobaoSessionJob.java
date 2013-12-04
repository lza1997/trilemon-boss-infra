package com.trilemon.boss.infra.base.job;

import com.trilemon.boss.infra.base.dao.AppUserDAO;
import com.trilemon.boss.infra.base.model.AppUser;
import com.trilemon.boss.infra.base.service.AppService;
import com.trilemon.boss.infra.base.service.TaobaoSessionService;
import com.trilemon.jobqueue.service.AbstractCronQueueService;
import com.trilemon.jobqueue.service.queue.JobQueue;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author kevin
 */
//@Component
public class RefreshTaobaoSessionJob extends AbstractCronQueueService<Long> {
    private final static Logger logger = LoggerFactory.getLogger(RefreshTaobaoSessionJob.class);
    @Autowired
    private TaobaoSessionService taobaoSessionService;
    @Autowired
    private AppService appService;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private JobQueue<Long> jobQueue;

    @PostConstruct
    public void init() {
        setJobQueue(jobQueue);
        setTag("job-queue[base-refresh-session]");
        setCron("0 0 1 * * ?");
        start();
        appService.addThreads(getThreadPoolExecutorMap());
        logger.info("add [{}] thread[{}] to monitor.", getThreadPoolExecutorMap().size(), getThreadPoolExecutorMap());
    }

    @Override
    public void process(Long id) throws Exception {
        AppUser appUser = appUserDAO.selectByPrimaryKey(id);
        taobaoSessionService.refreshSession(appUser.getUserId(), appUser.getAppKey());
    }

    @Override
    public void fillQueue() {
        logger.info("start to fill exec queue.");
        int elemCount = 0;
        int pageNum = 1;
        int pageSize = 100;
        while (true) {
            try {
                List<Long> ids = appUserDAO.paginateIds((pageNum - 1) * pageSize, pageSize);
                if (CollectionUtils.isEmpty(ids)) {
                    break;
                } else {
                    fillQueue(ids);
                    elemCount += ids.size();
                }
            } catch (Throwable e) {
                logger.error("exec plan error", e);
            }
        }
        logger.info("end to fill exec queue[{}].", elemCount);
    }
}