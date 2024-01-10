
REPOSITORY=/home/ubuntu/gream

# shellcheck disable=SC2164
cd $REPOSITORY

# shellcheck disable=SC2046
kill -9 `ps -ef|grep java|awk '{print $2}'`

JAR_NAME=$(ls -tr build/libs/*.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

chmod u+x /home/ubuntu/gream/custum.env

source /home/ubuntu/gream/custum.env

nohup java -jar $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &