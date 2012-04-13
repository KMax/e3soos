#!/bin/bash

echo "[INFO] [deploy.sh] Building a war..."
cd web
play war --output=../e3soos.war --exclude=nbproject:test-result:tmp --%production
cd ..
echo "[INFO] [deploy.sh] The war has been succesfully built."

echo "[INFO] [deploy.sh] Undeploying the previous version..."
ssh guvnor@192.168.134.7 'cd /opt/jboss-as/standalone/deployments; mv -f e3soos.war.deployed e3soos.war.undeploy; sleep 2s; rm -f e3soos.war.undeploy*; cd e3soos.war/WEB-INF; rm -r application classes framework lib resources;'
echo "[INFO] [deploy.sh] The previous version has been undeployed."

echo "[INFO] [deploy.sh] Copying the sources..."
scp -rC e3soos.war/* guvnor@192.168.134.7:/opt/jboss-as/standalone/deployments/e3soos.war/
echo "[INFO] [deploy.sh] The sources has been copied."

echo "[INFO] [deploy.sh] Deploying..."
scp -C deploy.sh guvnor@192.168.134.7:/opt/jboss-as/standalone/deployments/e3soos.war.dodeploy $1 > /dev/null
echo "[INFO] [deploy.sh] The war has been deployed."
