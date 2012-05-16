#!/bin/bash
# deploy.sh

HOST=192.168.134.7
USER=guvnor
WAR_NAME="e3soos.war"
DESTINATION=/opt/jboss-as/standalone/deployments/$WAR_NAME/

build()
{
	echo "[INFO] [deploy.sh] Building a war file..."
	pushd web
	play war --output=../$WAR_NAME --exclude=nbproject:test-result:tmp --%production
	popd
	echo "[INFO] [deploy.sh] The war was built successfully."
}

deploy()
{
	echo "[INFO] [deploy.sh] Deploying the war..."
	echo "" >> $WAR_NAME.dodeploy
	scp -C $WAR_NAME.dodeploy $USER@$HOST:/opt/jboss-as/standalone/deployments/$WAR_NAME.dodeploy $1 > /dev/null
	rm -f $WAR_NAME.dodeploy
	echo "[INFO] [deploy.sh] The war was deployed and the app was started."
}

undeploy()
{
	echo "[INFO] [deploy.sh] Undeploying the previously deployed war..."
        ssh $USER@$HOST 'cd /opt/jboss-as/standalone/deployments; rm -f e3soos.war.*; touch e3soos.war.undeploy; sleep 5s; rm -f e3soos.war.undeploy*;'
	echo "[INFO] [deploy.sh] The previous version was undeployed."
}

remove()
{
	echo "[INFO] [deploy.sh] Removing the previous version..."
	ssh $USER@$HOST 'rm -f e3soos.war.undeploy*; cd e3soos.war/WEB-INF; rm -fr application classes framework lib resources;'
	echo "[INFO] [deploy.sh] The previous version was removed."
}

copy()
{
	echo "[INFO] [deploy.sh] Copying the sources..."
        scp -rC $WAR_NAME/* $USER@$HOST:$DESTINATION
        echo "[INFO] [deploy.sh] The sources has been copied."
}

if [ "$1" = "--deployonly" ]
then
	deploy;
	exit $?;
fi

if [ "$1" = "--buildonly" ]
then
	build;
	exit $?;
fi

if [ "$1" = "--undeployonly" ]
then
	undeploy;
	exit $?;
fi

if [ "$1" = "" ]
then
	build;
	undeploy;
	remove;
	copy;
	deploy;
fi
