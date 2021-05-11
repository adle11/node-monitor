# Node Monitor



## Deploy using docker

1. Create user network (name: **nm_network**)
    ```shell
    docker network create --driver bridge nm_network
    ```

2. Build mongo image
    ```shell
    docker build -t mongo mongo:latest
    ```

3. Run mongo container
    ```shell
    docker run -d -p 27017-27019:27017-27019 -v mongodata:/data/db --network nm_network --network-alias mongodb --name mongodb -d mongo
    ```

4. Build NodeMonitor
    ```shell
    docker build -t nodemonitor:0.0.1 .
    ```
   
5. Run NodemMonitor
    ```shell
    docker run -p 8080:8080/tcp --name=nodemonitor --network nm_network -d nodemonitor:0.0.1
    ```

## Deploy using Kubernetes

1. Prepare and start minikube
    ```shell
    minikube start
    ```
    Also it is required to point to minikube's docker-daemon. First, display minikube's docker-env:
    ```shell
    minikube --shell=powershell docker-env
    ```

    And then suggested command, for example:
    ```shell
    minikube -p minikube --shell=powershell docker-env | Invoke-Expression
    ```

2. Build Docker image
    Docker image must be built after minikube is started and shell is configured.
    ```shell
    mvn clean install
    docker build -t nodemonitor:0.0.1 .
    ```

3. Run Docker image
    In order to run created image in Docker run following command:
    ```shell
    docker run --name "nodemonitor" -p 8080:8080/tcp -d nodemonitor:0.0.1
    ```

4. Create Kubernetes deployment
    In order to run created image in Minikube run following command:
    ```shell
    kubectl create deployment nodemonitor --image=nodemonitor:0.0.1 --dry-run -o=yaml > nodemonitor-deployment.yaml
    echo --- >> nodemonitor-deployment.yaml
    kubectl create service clusterip nodemonitor --tcp=8080:8080 --dry-run -o=yaml >> nodemonitor-deployment.yaml
    ```

5. Apply prepared deployment
    ```shell
    kubectl apply -f nodemonitor-deployment.yaml
    ```
    It is required to expose service outside the cluster. It can be done be port forwarding:
    ```shell
    kubectl port-forward svc/nodemonitor 8080:8080
    ```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.1/maven-plugin/reference/html/#build-image)

