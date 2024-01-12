FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
COPY .editorconfig .
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .
COPY gradle gradle
COPY src src
#COPY backend-config backend-config
RUN chmod +x ./gradlew
RUN ./gradlew build

FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
RUN mkdir /opt/app
COPY --from=builder build/libs/*.jar /opt/app/spring-boot-application.jar
EXPOSE 8080
ENV	PROFILE local
#ENV DB_URL ${{ secrets.DB_URL }}
#ENV DB_ID ${{ secrets.DB_ID }}
#ENV DB_PASSWORD ${{ secrets.DB_PASSWORD }}
#ENV REDIS_URL ${{ secrets.REDIS_URL }}
#ENV JWT_SECRET_KEY ${{ secrets.JWT_SECRET_KEY }}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}" ,"/opt/app/spring-boot-application.jar"]