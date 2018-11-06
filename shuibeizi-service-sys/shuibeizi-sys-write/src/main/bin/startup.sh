#!/bin/bash
# comment 'distribution-admin'
. /etc/profile
set -e
WORK_PATH=$(cd $(dirname $0); pwd)
JAR_PATH="${WORK_PATH}/../shuibeizi-sys-write.jar"
_ARGS="--spring.config.location=${WORK_PATH}/../config/application.yml --logging.config=${WORK_PATH}/../config/log4j2.xml"
JAVA_OPTS="-server -Xms512m -Xmx512m -Dloader.path=lib/"
CATALINA_LOG="${WORK_PATH}/../logs/catalina.log"
_PID="${WORK_PATH}/running.pid"

isRunning() {
  # Check for running app
  if [ -f "${_PID}" ]; then
    proc=$(cat ${_PID});
    if /bin/ps --pid ${proc} 1>&2 >/dev/null; then
      return 0
    fi
  fi
  return 1
}

if isRunning; then
    echo "The application is already running"
    exit 0
fi

if [ -f "${CATALINA_LOG}" ];then
 rm -f ${CATALINA_LOG}
 touch ${CATALINA_LOG}
else 
 touch ${CATALINA_LOG}
fi

nohup java ${JAVA_OPTS} -jar ${JAR_PATH} ${_ARGS} > ${CATALINA_LOG} 2>&1 &
echo $! > ${_PID}


