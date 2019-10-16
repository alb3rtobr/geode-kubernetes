# Running Apache Geode on Kubernetes

Set of instructions and artifacts to get [Apache Geode](http://geode.apache.org) running on [Kubernetes](http://kubernetes.io/).

# Deployment

Deploy using helm chart:

```
helm install --name=geode-kub geode-kub
```

<kbd>![alt-text](https://github.com/azwickey-pivotal/geode-kubernetes/blob/master/screenshot.png)</kbd>

Create the test region:
1. Open gfsh in application pod: <br/>`kubectl exec -it <geode app pod name> gfsh`
1. Connect to Geode cluster<br/>`gfsh>connect --locator=geode-locator-service[10334]`
1. Create region <br/>`create region --name=/test --type=PARTITION`


