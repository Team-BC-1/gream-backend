# Gream

<img src="https://github.com/Team-BC-1/gream/assets/75259783/ca5b76c7-411b-47a8-b3da-196cffc4cc89" width="600" height="600">

그림은 기프티콘 거래 플랫폼으로, 사용자는 회원가입을 통해 프로필을 생성하고, 상품을 검색하고 구매/판매할 수 있습니다.

프로필에는 닉네임, 구매/판매 내역, 관심 상품, 쿠폰 개수 등이 포함됩니다.

홈 화면에서는 다양한 상품을 카드 형식으로 보여주며, 상품 화면에서는 상품 사진과 정보, 거래 기록 등을 확인할 수 있습니다.

사용자는 즉시 구매 또는 구매 입찰을 통해 상품을 구매하고, 즉시 판매 또는 판매 입찰을 통해 상품을 판매할 수 있습니다.

프로필에서는 구매/판매 내역을 확인할 수 있습니다.

# Deployment

현재 블루/그린 배포 중에 있으며 [여기](https://mayst0522.com/)를 클릭하시면 이용가능하십니다.

# Features

> [API 명세서](https://teamsparta.notion.site/API-e46354a730fd48538a16c432e4438eaf)를 통해 보다 자세한 기능을 확인하실 수 있습니다.

- `그림`은 `기프티콘 실시간 거래 사이트`이다.


- `그림`은 `회원가입`을 해야한다.
    - `로컬 회원가입`과, `카카오 소셜 로그인`으로 회원가입 할 수 있다.
    - `로컬 회원가입`은 `loginId`, `nickname`, `password` 를 입력 받는다.
    - `카카오 소셜 로그인`은 `nickname` 을 `카카오 계정의 닉네임`을 가져온다.


- `사용자`는 `프로필`을 `조회`할 수 있다.
    - `닉네임`, `구매 내역`, `판매 내역`, `관심 상품내역`와, `쿠폰 개수`를 볼 수 있다.
    - `내역`은 `카드 형식`으로 표시하며, `좌우 스크롤 리스트`로 표시한다.


- `홈 화면`을 `조회`할 수 있다.
    - `홈 화면`에서는 여러 상품들의 `사진`과, `가격`, `브랜드`, `이름`, `즉시 구매가`를 `카드형식`으로 표시한다.
    - `홈 화면`에서는 `검색`을 할 수 있다.
    - `검색 결과`는 `홈화면 페이지를 재사용` 한다.
    - `홈 화면`에서는 `브랜드`를 `선택`할 수 있다.


- `상품 화면`을 `조회`할 수 있다.
    - `상품 화면`에서는 `사진`, `즉시 구매가`, `즉시 판매가`, `브랜드`, `이름`, `관심 상품`, `시세`, `거래 기록(채결거래, 판매 입찰, 구매입찰)`을 볼 수 있다.


- `상품`을 `구매`할 수 있다.
    - `즉시 구매`와 `구매 입찰`이 있다.
        - `즉시 구매`는 `가장 싼 판매입찰 가격`으로 `즉시 구매`를 한다.
        - `구매 입찰`은 `희망가`와 `마감 기한`을 `입력` 받는다.
            - `마감 기한`은 `7일`로 고정
    - `쿠폰`을 `적용`할 수 있다.
    - `구매 완료` 시 `프로필`의 `구매 내역`에서 기프티콘을 `확인`할 수 있다.


- `상품`을 `판매`할 수 있다.
    - `즉시 판매`와 `판매 입찰`이 있다.
        - `즉시 판매`는 `가장 비싼 구매입찰 가격`으로 `즉시 판매`를 한다.
        - `판매 입찰`은 `희망가`와 `마감 기한`을 `입력` 받는다.
            - `마감 기한`은 `7일`로 고정
    - `판매 완료` 시 `프로필`의 `판매 내역`에서 `확인`할 수 있다.

# Technologies Used

## Front-End

- Vite 5.0
- React 18.2.0
- MUI 5.15.4
- Zustand 4.4.7
- Axios 1.6.5
- React query 5.17.9
- React router dom 6.21.2

## Back-End

- Java 17
- Spring boot 3.1.7
- Spring security 6.1.6
- Spring data JPA 3.1.7
- Spring data Redis 3.1.7
- Spring Batch 5.0.4
- QueryDSL 5.0.0
- Swagger 2.3.0
- JWT
- MySQL 8.0.35
- Redis 7.1
- Prometheus
- Grafana

## Infrastructure

- S3
- ECR
- LoadBalancer
- Route53
- Codedeploy
- EC2
- ubuntu 22.04
- RDS
- ElastiCache for redis

# ERD

![image](https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2Fbc551285-b960-4cec-b0c8-55e2cb3d1edc%2F%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-01-25_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_7.05.45.png?table=block&id=afd6ceaf-9a4a-4fbb-bd08-55b1f33e5c51&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=&cache=v2)

# System Architecture

![image](https://github.com/Team-BC-1/gream/assets/75259783/2babc085-e043-45ea-93d7-8109359fe3a2)

# Project Structure

```
├── GreamApplication.java
├── domain
│   ├── admin
│   │   ├── controller
│   │   │   └── AdminController.java
│   │   └── dto
│   │       ├── request
│   │       │   ├── AdminCreateCouponRequestDto.java
│   │       │   ├── AdminGetRefundRequestDto.java
│   │       │   ├── AdminProductRequestDto.java
│   │       │   └── AdminRefundPassResponseDto.java
│   │       └── response
│   │           ├── AdminCreateCouponResponseDto.java
│   │           ├── AdminGetRefundResponseDto.java
│   │           ├── AdminProductResponseDto.java
│   │           └── AdminRefundPassRequestDto.java
│   ├── buy
│   │   ├── controller
│   │   │   ├── BuyBidController.java
│   │   │   ├── BuyNowController.java
│   │   │   └── UserBoughtHistoryController.java
│   │   ├── dto
│   │   │   ├── request
│   │   │   │   ├── BuyBidRequestDto.java
│   │   │   │   ├── BuyCancelBidRequestDto.java
│   │   │   │   ├── BuyCheckBidRequestDto.java
│   │   │   │   ├── BuyCheckOrderRequestDto.java
│   │   │   │   └── BuyNowRequestDto.java
│   │   │   └── response
│   │   │       ├── BuyBidResponseDto.java
│   │   │       ├── BuyCancelBidResponseDto.java
│   │   │       ├── BuyCheckBidResponseDto.java
│   │   │       ├── BuyCheckOrderResponseDto.java
│   │   │       └── BuyNowResponseDto.java
│   │   ├── entity
│   │   │   └── Buy.java
│   │   ├── mapper
│   │   │   └── BuyMapper.java
│   │   ├── provider
│   │   │   ├── BuyBidProvider.java
│   │   │   └── BuyNowProvider.java
│   │   ├── repository
│   │   │   ├── BuyRepository.java
│   │   │   ├── BuyRepositoryCustom.java
│   │   │   ├── BuyRepositoryCustomImpl.java
│   │   │   └── helper
│   │   │       └── BuyQueryOrderFactory.java
│   │   ├── scheduler
│   │   │   └── BuyDeadlineScheduler.java
│   │   └── service
│   │       ├── command
│   │       │   └── BuyCommandService.java
│   │       └── query
│   │           └── BuyQueryService.java
│   ├── common
│   │   └── model
│   │       └── BaseEntity.java
│   ├── coupon
│   │   ├── controller
│   │   │   └── CouponController.java
│   │   ├── dto
│   │   │   ├── request
│   │   │   │   ├── CouponAvailableRequestDto.java
│   │   │   │   └── CouponUnavailableRequestDto.java
│   │   │   └── response
│   │   │       ├── CouponAvailableResponseDto.java
│   │   │       └── CouponUnavailableResponseDto.java
│   │   ├── entity
│   │   │   ├── Coupon.java
│   │   │   ├── CouponStatus.java
│   │   │   └── DiscountType.java
│   │   ├── helper
│   │   │   └── CouponCalculator.java
│   │   ├── mapper
│   │   │   └── CouponMapper.java
│   │   ├── provider
│   │   │   └── CouponProvider.java
│   │   ├── repository
│   │   │   ├── CouponRepository.java
│   │   │   ├── CouponRepositoryCustom.java
│   │   │   └── CouponRepositoryCustomImpl.java
│   │   └── service
│   │       ├── command
│   │       │   └── CouponCommandService.java
│   │       └── qeury
│   │           └── CouponQueryService.java
│   ├── gifticon
│   │   ├── entity
│   │   │   └── Gifticon.java
│   │   ├── mapper
│   │   │   └── GifticonMapper.java
│   │   ├── repository
│   │   │   ├── GifticonRepository.java
│   │   │   ├── GifticonRepositoryCustom.java
│   │   │   └── GifticonRepositoryCustomImpl.java
│   │   └── service
│   │       ├── command
│   │       │   └── GifticonCommandService.java
│   │       └── query
│   │           └── GifticonQueryService.java
│   ├── oauth
│   │   └── kakao
│   ├── order
│   │   ├── entity
│   │   │   └── Order.java
│   │   ├── mapper
│   │   │   └── OrderMapper.java
│   │   ├── repository
│   │   │   └── OrderRepository.java
│   │   ├── service
│   │   │   ├── command
│   │   │   │   └── OrderCommandService.java
│   │   │   └── query
│   │   │       └── OrderQueryService.java
│   │   └── validator
│   │       └── ProductValidator.java
│   ├── payment
│   │   └── toss
│   │       ├── controller
│   │       │   └── TossPaymentController.java
│   │       ├── dto
│   │       │   ├── request
│   │       │   │   └── TossPaymentInitialRequestDto.java
│   │       │   └── response
│   │       │       ├── TossPaymentCardDto.java
│   │       │       ├── TossPaymentFailResponseDto.java
│   │       │       ├── TossPaymentInitialResponseDto.java
│   │       │       └── TossPaymentSuccessResponseDto.java
│   │       ├── entity
│   │       │   ├── OrderName.java
│   │       │   ├── PayType.java
│   │       │   └── TossPayment.java
│   │       ├── mapper
│   │       │   └── TossPaymentMapper.java
│   │       ├── repository
│   │       │   └── TossPaymentRepository.java
│   │       ├── service
│   │       │   ├── PaymentService.java
│   │       │   └── event
│   │       │       ├── TossPaymentEventListener.java
│   │       │       ├── TossPaymentSuccessCallback.java
│   │       │       └── TossPaymentSuccessEvent.java
│   │       └── validator
│   │           └── TossPaymentRequestValidator.java
│   ├── product
│   │   ├── controller
│   │   │   ├── ProductLikeController.java
│   │   │   └── ProductQueryController.java
│   │   ├── dto
│   │   │   ├── response
│   │   │   │   ├── BuyPriceToQuantityResponseDto.java
│   │   │   │   ├── BuyTradeResponseDto.java
│   │   │   │   ├── OrderTradeResponseDto.java
│   │   │   │   ├── ProductDetailsResponseDto.java
│   │   │   │   ├── ProductDislikeResponseDto.java
│   │   │   │   ├── ProductLikeResponseDto.java
│   │   │   │   ├── ProductLikesResponseDto.java
│   │   │   │   ├── ProductPreviewByNameResponseDto.java
│   │   │   │   ├── ProductPreviewResponseDto.java
│   │   │   │   └── SellPriceToQuantityResponseDto.java
│   │   │   └── unit
│   │   │       └── ProductCondition.java
│   │   ├── entity
│   │   │   ├── LikeProduct.java
│   │   │   └── Product.java
│   │   ├── mapper
│   │   │   └── ProductMapper.java
│   │   ├── provider
│   │   │   ├── BuyOrderQueryProvider.java
│   │   │   └── SellOrderQueryProvider.java
│   │   ├── repository
│   │   │   ├── LikeProductRepository.java
│   │   │   ├── LikeProductRepositoryCustom.java
│   │   │   ├── LikeProductRepositoryCustomImpl.java
│   │   │   ├── ProductRepository.java
│   │   │   ├── ProductRepositoryCustom.java
│   │   │   ├── ProductRepositoryCustomImpl.java
│   │   │   └── helper
│   │   │       ├── ProductQueryConditionFactory.java
│   │   │       └── ProductQueryOrderFactory.java
│   │   └── service
│   │       ├── command
│   │       │   ├── ProductCommandService.java
│   │       │   └── ProductLikeCommandService.java
│   │       └── query
│   │           └── ProductQueryService.java
│   ├── sell
│   │   ├── controller
│   │   │   ├── SellBidController.java
│   │   │   ├── SellNowController.java
│   │   │   └── UserSellHistoryController.java
│   │   ├── dto
│   │   │   ├── request
│   │   │   │   ├── SellBidRequestDto.java
│   │   │   │   ├── SellCancelBidRequestDto.java
│   │   │   │   └── SellNowRequestDto.java
│   │   │   └── response
│   │   │       ├── OrderAsSellerResponseDto.java
│   │   │       ├── SellBidResponseDto.java
│   │   │       ├── SellCancelBidResponseDto.java
│   │   │       ├── SellNowResponseDto.java
│   │   │       ├── SellTradeResponseDto.java
│   │   │       └── UserSellOnProgressResponseDto.java
│   │   ├── entity
│   │   │   └── Sell.java
│   │   ├── mapper
│   │   │   └── SellMapper.java
│   │   ├── provider
│   │   │   ├── ProductOrderQueryProvider.java
│   │   │   ├── SellBidProvider.java
│   │   │   ├── SellNowProvider.java
│   │   │   └── SellerOrderProvider.java
│   │   ├── repository
│   │   │   ├── SellRepository.java
│   │   │   ├── SellRepositoryCustom.java
│   │   │   ├── SellRepositoryCustomImpl.java
│   │   │   └── helper
│   │   │       └── SellQueryOrderFactory.java
│   │   ├── scheduler
│   │   │   └── SellDeadlineScheduler.java
│   │   └── service
│   │       ├── command
│   │       │   └── SellCommandService.java
│   │       ├── helper
│   │       │   └── deadline
│   │       │       ├── Deadline.java
│   │       │       └── DeadlineCalculator.java
│   │       └── query
│   │           └── SellQueryService.java
│   └── user
│       ├── controller
│       │   └── UserController.java
│       ├── dto
│       │   ├── request
│       │   │   ├── UserLoginRequestDto.java
│       │   │   ├── UserPointRefundRequestDto.java
│       │   │   ├── UserPointRequestDto.java
│       │   │   └── UserSignupRequestDto.java
│       │   └── response
│       │       ├── UserLoginResponseDto.java
│       │       ├── UserLogoutResponseDto.java
│       │       ├── UserPointRefundResponseDto.java
│       │       ├── UserPointResponseDto.java
│       │       └── UserSignupResponseDto.java
│       ├── entity
│       │   ├── Provider.java
│       │   ├── Refund.java
│       │   ├── User.java
│       │   └── UserRole.java
│       ├── mapper
│       │   ├── RefundMapper.java
│       │   └── UserMapper.java
│       ├── repository
│       │   ├── RefundRepository.java
│       │   └── UserRepository.java
│       └── service
│           ├── UserService.java
│           ├── command
│           │   └── RefundCommandService.java
│           └── query
│               └── RefundQueryService.java
├── global
│   ├── common
│   │   ├── ErrorResponseDto.java
│   │   ├── InvalidInputMapper.java
│   │   ├── InvalidInputResponseDto.java
│   │   ├── RestResponse.java
│   │   └── ResultCase.java
│   ├── config
│   │   ├── AsyncConfig.java
│   │   ├── BatchConfig.java
│   │   ├── OpenEntityManagerConfiguration.java
│   │   ├── QueryDslConfig.java
│   │   └── datasource
│   │       ├── DataSourceType.java
│   │       ├── TransactionRoutingDataSource.java
│   │       └── TransactionRoutingDataSourceConfig.java
│   ├── exception
│   │   ├── ExceptionHandlerFilter.java
│   │   ├── GlobalException.java
│   │   └── GlobalExceptionHandler.java
│   ├── jpa
│   │   └── AuditingConfig.java
│   ├── jwt
│   │   ├── JwtAuthFilter.java
│   │   ├── JwtLoginFilter.java
│   │   ├── JwtStatus.java
│   │   └── JwtUtil.java
│   ├── oauth
│   │   ├── OAuth2Filter.java
│   │   ├── OAuthAPIClient.java
│   │   ├── OAuthInfoResponse.java
│   │   ├── OAuthLoginParam.java
│   │   ├── RequestOAuthInfoService.java
│   │   └── kakao
│   │       ├── KakaoApiClient.java
│   │       ├── KakaoInfoResponse.java
│   │       ├── KakaoLoginParam.java
│   │       └── KakaoToken.java
│   ├── querydsl
│   │   └── QueryDslUtil.java
│   ├── redis
│   │   ├── RedisCacheName.java
│   │   ├── RedisConfig.java
│   │   ├── RedisUtil.java
│   │   └── RestPage.java
│   ├── resttemplate
│   │   └── RestTemplateConfig.java
│   ├── security
│   │   ├── LogoutHandlerImpl.java
│   │   ├── LogoutSuccessHandlerImpl.java
│   │   ├── UserDetailsImpl.java
│   │   ├── UserDetailsServiceImpl.java
│   │   └── WebSecurityConfig.java
│   ├── swagger
│   │   └── SwaggerConfig.java
│   └── validator
│       └── OrderCriteriaValidator.java
└── infra
    └── s3
        ├── S3Config.java
        └── S3ImageService.java
```
