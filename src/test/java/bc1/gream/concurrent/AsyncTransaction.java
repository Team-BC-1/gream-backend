package bc1.gream.concurrent;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AsyncTransaction {

    @Transactional
    public void run(Runnable runnable) {
        runnable.run();
    }
}
