export ENV=${1:dev}

if [ "$@" = "prd" ]
  then
    ENV=prd
fi

docker build . --build-arg ENV=${ENV} -t apcffl-ui
docker run -it -e ENV=${ENV} -p 8080:8080 apcffl-ui
