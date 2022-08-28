Запуск minicube
1) minikube start --cpus=4 --memory=12g --cni=flannel --kubernetes-version="v1.19.0"

Установка postgres (в папке проекта manifests необходимо выполнить следующие инструкции):
1) kubectl apply -f postgres-namespace.yaml
2) kubectl apply -f postgres-secret.yaml
3) helm -n postgres install otus -f my-postgresql-values.yaml ./postgresql