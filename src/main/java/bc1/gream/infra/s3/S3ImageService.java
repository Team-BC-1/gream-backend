package bc1.gream.infra.s3;

import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "s3 image upload")
@Service
@RequiredArgsConstructor
public class S3ImageService {

    private static final String gifticonDirectoryName = "gifticon/";
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public String getUrlAfterUpload(MultipartFile image) {

        String originalName = image.getOriginalFilename();
        String randomImageName = getRandomImageName(originalName);
        log.info("s3 upload name : {}", randomImageName);

        try {
            PutObjectRequest request = new PutObjectRequest(
                bucketName,
                randomImageName,
                image.getInputStream(),
                createObjectMetadata(originalName)
            )
                .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
        } catch (IOException e) {
            throw new GlobalException(ResultCase.IMAGE_UPLOAD_FAIL);
        }

        String url = amazonS3.getUrl(bucketName, randomImageName).toString();
        log.info("s3 url : {}", url);
        return url;
    }

    private String getRandomImageName(String originalName) {
        String random = UUID.randomUUID().toString();
        return gifticonDirectoryName + random + '-' + originalName;
    }

    private ObjectMetadata createObjectMetadata(String originalName) {
        ObjectMetadata metadata = new ObjectMetadata();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        metadata.setContentType("image/" + ext);
        return metadata;
    }
}
