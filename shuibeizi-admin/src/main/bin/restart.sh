#!/bin/bash
# comment 'restart'
.  /etc/profile
set -e
_path=$(cd $(dirname $0); pwd)

sh ${_path}/shutdown.sh
echo "begin start application"
sh ${_path}/startup.sh
