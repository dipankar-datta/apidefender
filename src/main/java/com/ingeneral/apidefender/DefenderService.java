package com.ingeneral.apidefender;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
public final class DefenderService {


    private  final Map<String, Lock> LOCK_MAP = new HashMap<>();

    private Long DEFAULT_UNLOCK_PERIOD = 10000L;

    @PostConstruct
    private void postConstruct() {
        new Thread(this::startMonitorThread).start();
        log.info("Monitor thread started");
    }

    public String lock(
            String sessionId,
            String ip,
            String resourceIdentifier,
            Optional<Long> defaultUnlockTimeSeconds) throws LockException {

        var lockKey = ip + "-" + sessionId + "-" + resourceIdentifier;

        if (this.LOCK_MAP.containsKey(lockKey)) {
            throw new LockException("Duplicate request not allowed until previous request completed");
        }

        var lock = new Lock(ip, lockKey, sessionId, now(), defaultUnlockTimeSeconds);

        this.LOCK_MAP.put(lockKey, lock);

        log.info("Resource {} locked.", lockKey);

        return lockKey;
    }

    public boolean releaseLock(String lockKey) throws LockException {

        if (this.LOCK_MAP.containsKey(lockKey)) {
            log.info("Resource {} unlocked.", lockKey);
            return this.LOCK_MAP.remove(lockKey) != null;
        } else {
            throw new LockException("Invalid Lock key");
        }
    }

    private void startMonitorThread() {

        while (true) {
            try {
                Thread.sleep(1000);
                this.monitorLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void monitorLock() {
        var expiredLocks = new ArrayList<Lock>();
        var currentTime = LocalDateTime.now();
        this.LOCK_MAP.values().forEach(lock -> {
            log.info("Default unlock time: {}", this.DEFAULT_UNLOCK_PERIOD);


            var timeDifference = ChronoUnit.MILLIS.between(lock.getLockTime(), currentTime);

            log.info("Time difference: {}", timeDifference);

            if (lock.defaultUnlockTimeSeconds.isPresent()) {
                if (timeDifference > lock.getDefaultUnlockTimeSeconds().get()) {
                    expiredLocks.add(lock);
                }
            } else if (timeDifference > this.DEFAULT_UNLOCK_PERIOD) {
                expiredLocks.add(lock);
            }

        });

        if (!expiredLocks.isEmpty()) {
            log.info("Clearing expired locks: {}",
                    expiredLocks.stream()
                            .map(lock -> lock.getKey())
                            .collect(Collectors.toList()));
        }

        expiredLocks.forEach(expiredLock -> {
            this.LOCK_MAP.remove(expiredLock.getKey());
        });
    }



    public class LockException extends Throwable {

        public LockException(String message) {
            super(message);
        }
    }

    @Getter
    @AllArgsConstructor
    private final class Lock {
        private String ip;
        private String key;
        private String sessionId;
        private LocalDateTime lockTime;
        private Optional<Long> defaultUnlockTimeSeconds;
    }
}




