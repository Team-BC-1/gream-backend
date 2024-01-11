package bc1.gream.test;

import bc1.gream.domain.product.entity.Product;

public interface ProductTest {

    Long TEST_PRODUCT_ID = 1L;
    String TEST_PRODUCT_BRAND = "스타벅스";
    String TEST_PRODUCT_NAME = "아이스 아메리카노";
    String TEST_PRODUCT_IMAGE_URL = "starbucks_ice_americano";
    String TEST_PRODUCT_DESCRIPTION = "얼어죽어도 스타벅스 아이스 아메리카노가 좋아요";
    Long TEST_PRODUCT_PRICE = 4_500L;

    Product TEST_PRODUCT = Product.builder()
        .brand(TEST_PRODUCT_BRAND)
        .name(TEST_PRODUCT_NAME)
        .imageUrl(TEST_PRODUCT_IMAGE_URL)
        .description(TEST_PRODUCT_DESCRIPTION)
        .price(TEST_PRODUCT_PRICE)
        .build();

    Long TEST_PRODUCT_SECOND_ID = 2L;
    String TEST_PRODUCT_SECOND_BRAND = "이디야";
    String TEST_PRODUCT_SECOND_NAME = "카페라떼";
    String TEST_PRODUCT_SECOND_IMAGE_URL = "ediya_caffe_latte";
    String TEST_PRODUCT_SECOND_DESCRIPTION = "더워죽어도 뜨거운 이디야 카페라떼가 좋아요";
    Long TEST_PRODUCT_SECOND_PRICE = 5_500L;

    Product TEST_PRODUCT_SECOND = Product.builder()
        .brand(TEST_PRODUCT_SECOND_BRAND)
        .name(TEST_PRODUCT_SECOND_NAME)
        .imageUrl(TEST_PRODUCT_SECOND_IMAGE_URL)
        .description(TEST_PRODUCT_SECOND_DESCRIPTION)
        .price(TEST_PRODUCT_SECOND_PRICE)
        .build();
}
