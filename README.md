# Running Apache Geode on Kubernetes

Set of instructions and artifacts to get [Apache Geode](http://geode.incubator.apache.org) running on [Kubernetes](http://kubernetes.io/).

## Build Docker image to include shell scripts

Go to the `image` directory and build the Docker image using:

`docker build . -t <your image name and tag>`

# Running on PKS

*Steps*:

1. `kubectl create -f locator-controller.yaml`
1. `kubectl create -f locator-service.yaml`
1. `kubectl create -f server-controller.yaml`
1. `kubectl create -f server-service.yaml`

You can scale the number of servers by using the following command:

`kubectl scale --replicas=3 -f server-controller.yaml`

# Running Sample App

<kbd>![alt-text](https://github.com/azwickey-pivotal/geode-kubernetes/blob/master/screenshot.png)</kbd>

*Steps*:

1. `Deploy Geode to cluster (see above steps)`
1. `kubectl create -f app-service.yml`
1. `kubectl create -f app-rs.yml`
1. SSH info application pod, launch gfsh, create region
   `kubectl exec -it <geode app pod name> bash`
   `gfsh`
   `gfsh>connect --locator=geode-locator[10334]`
   `create region --name=/test --type=PARTITION`
