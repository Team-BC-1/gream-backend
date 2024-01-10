
REPOSITORY=/home/ubuntu/gream

# shellcheck disable=SC2164
cd $REPOSITORY

# shellcheck disable=SC2010
JAR_NAME=$(ls $REPOSITORY/ | grep 'SNAPSHOT.jar' | tail -n 1)
echo "$JAR_NAME"
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
echo "$JAR_PATH"

CURRENT_PID=$(pgrep -f jar)

if [ -z $CURRENT_PID ]
then
  echo "> Nothing to end."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi
chmod +x $JAR_PATH
echo "> $JAR_PATH deploy"
nohup java -jar $JAR_PATH --spring.profiles.active=prod > /dev/null 2> /dev/null < /dev/null &