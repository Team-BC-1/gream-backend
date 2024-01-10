
REPOSITORY=/home/ubuntu/gream

# shellcheck disable=SC2164
cd $REPOSITORY

# shellcheck disable=SC2010
CURRENT_PID=$(pgrep -fl action | grep java | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

JAR_NAME=$(ls -tr build/libs/*.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

chmod u+x /home/ubuntu/gream/custum.env

source /home/ubuntu/gream/custum.env
env

nohup java -jar $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &