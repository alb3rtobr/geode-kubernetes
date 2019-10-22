# Running Apache Geode on Kubernetes

This repo contains the source code and helm charts needed to deploy the following components in [Kubernetes](http://kubernetes.io/):
- [Apache Geode](http://geode.apache.org) cluster with a custom `MetricsPublishingService` to allow metrics gathering by Prometheus.
- Sample application to introduce data in the [Apache Geode](http://geode.apache.org) cluster using a web interface.



# Deployment

Components have to be deployed in the following order:
1. Prometheus operator
1. Geode cluster
1. Sample application


# Prometheus operator

```
$ helm install stable/prometheus-operator --name prometheus-operator 
```

Check status:
```
$ kubectl get pods -l "release=prometheus-operator"
NAME                                                 READY   STATUS    RESTARTS   AGE
prometheus-operator-grafana-6bbd95d9c4-4kfvf         2/2     Running   0          53s
prometheus-operator-operator-85f7fccfdf-vq67s        2/2     Running   0          53s
prometheus-operator-prometheus-node-exporter-n2bnm   1/1     Running   0          53s
```

Check Prometheus dashboard in `http://localhost:9090` by forwarding port:
```
$ kubectl port-forward prometheus-prometheus-operator-prometheus-0 9090
```

Check Grafana dashboard in `http://localhost:3000` (user `admin`, pass `prom-operator`) by forwarding port:
```
$ kubectl port-forward $(kubectl get pods --selector=app=grafana --output=jsonpath="{.items..metadata.name}") 3000
```

# Geode cluster

Deploy using helm chart:

```
helm install --name=geode-kub charts/geode-kub
```

## Custom MetricsPublishingService

`metrics-publishing-service` contains the source code of a custom `MetricsPublishingService`. This adds a [Micrometer](https://micrometer.io/)'s `PrometheusMeterRegistry` to the Geode `MeterRegistry` when members are created, so Prometheus is able to gather metrics from them.


# Sample web application

A specific region needs to be created in the Geode cluster before the application is deployed:

1. Open gfsh in application pod: <br/>`kubectl exec -it <geode app pod name> gfsh`
1. Connect to Geode cluster<br/>`gfsh>connect --locator=geode-locator-service[10334]`
1. Create region <br/>`create region --name=/test --type=PARTITION`

Then, deploy the app:
```
helm install --name=geode-app charts/geode-app
```
<kbd>![alt-text](https://github.com/azwickey-pivotal/geode-kubernetes/blob/master/screenshot.png)</kbd>

# Links

- ["Visualize your Geode metrics"](https://www.youtube.com/watch?v=stTWW5uBC5U&list=PLLLKxhUTSiRuoHYkB77NEu6Y3NJeLziU7&index=4&t=1s) (talk at SpringOne 2019)
- [Publishing Geode Metrics to External Monitoring Systems](https://cwiki.apache.org/confluence/display/GEODE/Publishing+Geode+Metrics+to+External+Monitoring+Systems) (Geode wiki)
- [Kubernetes monitoring with Prometheus in 15 minutes](https://itnext.io/kubernetes-monitoring-with-prometheus-in-15-minutes-8e54d1de2e13) (blog post)
- [Helm stable/prometheus-operator: adding new scraping targets and troubleshooting](https://blog.pilosus.org/posts/2019/06/01/prometheus-operator-no-active-targets/) (blog post)

- Repos:
  - [Prometheus operator](https://github.com/coreos/prometheus-operator)
  - [Geode registry example](https://github.com/moleske/geode-registry-example)
