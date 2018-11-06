#!/bin/bash

set -e
WORK_PATH=$(cd $(dirname $0); pwd)
if [ -f ${WORK_PATH}/running.pid ]; then 
 kill `cat ${WORK_PATH}/running.pid`
 rm ${WORK_PATH}/running.pid
fi

_pids=$(ps -ef | grep "shuibeizi-sys-read" | grep -v grep | awk '{print $2}')
num=${#_pids[@]}
echo ${num}
if [ ${num} -gt 0 ];then

for _pid in ${_pids};
do
 kill -9 ${_pid}
done

fi

echo "The application is closed"

