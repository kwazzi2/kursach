package by.bsuir.webapp.config;

import by.bsuir.webapp.model.tutor.Tutor;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import by.bsuir.webapp.service.TutorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;


@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {
    private static final Log logger = LogFactory.getLog(SchedulingConfig.class);
    private final TutorService tutorService;
    private final TutorRepository tutorRepository;
    @Value("${scheduling.cron}")
    private String cron;

    public SchedulingConfig(TutorService tutorService, TutorRepository tutorRepository) {
        this.tutorService = tutorService;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(3);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);

        taskRegistrar.addTriggerTask(() -> {
            for(Tutor tutor: tutorRepository.findAll()) {
                try {
                    tutorService.updateAverageRating(tutor.getId());
                } catch (Exception e) {
                    logger.warn("Cant update rating for tutor with id: " + tutor.getId(), e);
                }
            }

        }, triggerContext -> {
            CronTrigger trigger = new CronTrigger(cron);
            return trigger.nextExecutionTime(triggerContext);
        });
    }
}
