package bc1.gream.test;

import bc1.gream.domain.gifticon.entity.Gifticon;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public interface GifticonTest extends OrderTest {

    Long TEST_GIFTICON_ID = 1L;

    String TEST_GIFTICON_URL = "images/images.png";
    MultipartFile TEST_GIFTICON_FILE = new MockMultipartFile("file", "image.png", "image/png", "content".getBytes());
    String TEST_S3_IMAGE_URL = "https://cataas.com/cat";

    Gifticon TEST_GIFTICON_END = Gifticon.builder()
        .gifticonUrl(TEST_GIFTICON_URL)
        .build();

    Gifticon TEST_GIFTICON = Gifticon.builder()
        .gifticonUrl(TEST_GIFTICON_URL)
        .build();
}
