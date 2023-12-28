#!/bin/bash

# 'your_process_name'을 실제 프로세스의 이름 또는 식별자로 대체하세요
process_name="application.jar"

# 프로세스가 실행 중인지 확인합니다
if pgrep -x "$process_name" > /dev/null
then
    echo "$process_name 종료 중..."
    # 프로세스에 종료 신호를 보냅니다
    pkill -SIGTERM "$process_name"
    echo "$process_name 정상적으로 종료되었습니다."
else
    echo "$process_name이(가) 실행되고 있지 않습니다."
fi

##!/bin/bash
#
#ROOT_PATH="/home/ubuntu/app/trello-project"
#JAR="$ROOT_PATH/application.jar"
#STOP_LOG="$ROOT_PATH/stop.log"
#SERVICE_PID=$(pgrep -f $JAR) # 실행중인 Spring 서버의 PID
#
#if [ -z "$SERVICE_PID" ]; then
#  echo "서비스 NouFound" >> $STOP_LOG
#else
#  echo "서비스 종료 " >> $STOP_LOG
#  kill "$SERVICE_PID"
#  # kill -9 $SERVICE_PID # 강제 종료를 하고 싶다면 이 명령어 사용
#fi
