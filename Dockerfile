# 1. 자바 17버전 환경을 가져옵니다.
FROM eclipse-temurin:17-jdk-alpine

# 2. 방금 빌드한 jar 파일을 컨테이너 안으로 복사합니다.
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 3. 8080 포트를 열어둡니다.
EXPOSE 8080

# 4. 컨테이너가 켜질 때 이 명령어를 실행합니다.
ENTRYPOINT ["java", "-jar", "/app.jar"]