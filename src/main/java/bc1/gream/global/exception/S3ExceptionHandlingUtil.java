package bc1.gream.global.exception;

import bc1.gream.global.common.ResultCase;
import bc1.gream.infra.s3.S3ImageService;
import io.vavr.control.Try;
import org.springframework.dao.OptimisticLockingFailureException;

public class S3ExceptionHandlingUtil {

    /**
     * 타겟함수에 대해 실행, 성공 시 반환값을 반환하고, 실패 시 s3 이미지를 삭제한 뒤 예외처리합니다.
     *
     * @param targetFunction Function<T, R> 타겟함수
     * @param s3ImageService s3 이미지 서비스
     * @param imageUrl       이미지 URL
     * @param resultCase     예외케이스
     * @param <T>            타켓함수의 제너릭 클래스
     * @return 타켓함수의 반환값
     * @author 임지훈
     */
    public static <T> T tryWithS3Cleanup(ThrowingFunction<T> targetFunction, S3ImageService s3ImageService, String imageUrl,
        ResultCase resultCase) {
        return Try.of(targetFunction::apply)
            .onFailure(ex -> handleException(ex, s3ImageService, imageUrl))
            .getOrElseThrow(() -> new GlobalException(resultCase));
    }

    /**
     * 타겟함수에 대해 실행, 실패 시 s3 이미지를 삭제한 뒤 예외처리합니다.
     *
     * @param targetRunnable Runnable 타겟함수
     * @param s3ImageService s3 이미지 서비스
     * @param imageUrl       이미지 URL
     * @param resultCase     예외케이스
     */
    public static void tryWithS3Cleanup(ThrowingRunnable targetRunnable, S3ImageService s3ImageService, String imageUrl,
        ResultCase resultCase) {
        Try.run(targetRunnable::run)
            .onFailure(ex -> handleException(ex, s3ImageService, imageUrl))
            .getOrElseThrow(() -> new GlobalException(resultCase));
    }

    private static void handleException(Throwable ex, S3ImageService s3ImageService, String imageUrl) {
        if (ex instanceof IllegalArgumentException || ex instanceof OptimisticLockingFailureException) {
            // s3ImageService.delete(imageUrl);
        }
    }

    @FunctionalInterface
    public interface ThrowingFunction<T> {

        T apply() throws Exception;
    }


    @FunctionalInterface
    public interface ThrowingRunnable {

        void run() throws Exception;
    }
}
