package bc1.gream.threadpool;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Disabled
@ActiveProfiles("test")
public class AsyncTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    @DisplayName("AsyncConfig 에 대한 설정값에 따른 성능개선치를 측정합니다.")
    public void AsyncConfig_설정에따른효율_측정() {
        // GIVEN
        long startTime = System.currentTimeMillis();

        // WHEN
        int callCount = 1;
        for (int i = 0; i < callCount; i++) {
            asyncService.doSomethingAsync();
        }

        // THEN
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time taken: " + elapsedTime + " milliseconds");
    }
}