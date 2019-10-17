# Running Apache Geode on Kubernetes

Set of instructions and artifacts to get [Apache Geode](http://geode.apache.org) running on [Kubernetes](http://kubernetes.io/).

# Deployment of Geode cluster

Deploy using helm chart:

```
helm install --name=geode-kub charts/geode-kub
```

<kbd>![alt-text](https://github.com/azwickey-pivotal/geode-kubernetes/blob/master/screenshot.png)</kbd>

Create the test region:
1. Open gfsh in application pod: <br/>`kubectl exec -it <geode app pod name> gfsh`
1. Connect to Geode cluster<br/>`gfsh>connect --locator=geode-locator-service[10334]`
1. Create region <br/>`create region --name=/test --type=PARTITION`

# Deployment of sample application

```
helm install --name=geode-app charts/geode-app
```

# Deployment of Prometheus

```
$ helm install stable/prometheus-operator --name prometheus-operator --namespace monitoring
```

Check status:
```
$ kubectl --namespace monitoring get pods -l "release=prometheus-operator"
NAME                                                 READY   STATUS    RESTARTS   AGE
prometheus-operator-grafana-6bbd95d9c4-4kfvf         2/2     Running   0          53s
prometheus-operator-operator-85f7fccfdf-vq67s        2/2     Running   0          53s
prometheus-operator-prometheus-node-exporter-n2bnm   1/1     Running   0          53s
```


