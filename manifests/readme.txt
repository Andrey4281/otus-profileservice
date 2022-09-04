Запуск minicube
1) minikube start --cpus=4 --memory=12g --cni=flannel --kubernetes-version="v1.19.0"
kubectl --namespace postgres port-forward svc/otus-postgresql 5434:5432
kubectl --namespace backend port-forward svc/profile-service 7000:7000
kubectl --namespace backend port-forward svc/auth-service 7001:7001

Установка postgres (в папке проекта manifests необходимо выполнить следующие инструкции):
1) kubectl apply -f postgres-namespace.yaml
2) kubectl apply -f postgres-secret.yaml
3) helm -n postgres install otus -f my-postgresql-values.yaml ./postgresql
4) kubectl apply -f postgres-config.yaml
5) kubectl apply -f migration.yaml (для создания БД для authservice)
6) kubectl apply -f backend-namespace.yaml
7) kubectl apply -f postgres-config-backend.yaml
8) kubectl apply -f postgres-secret-backend.yaml
9) kubectl apply -f backend-services-config.yaml
10) kubectl apply -f profileservice-deployment.yaml
11) kubectl apply -f authservice-deployment.yaml
12) kubectl apply -f gateway-deployment.yaml