# 클러스터 구성하기 

* 클러스터 구성은 최소 3개로 구성이 가능하다(2개 안됨)

:::tip
MongoDB 의 샤딩과 같은 개념이다.
:::

## 설정 파일

```shell script
cp $REDIS_HOME/redis.conf $REDIS_HOME/redis1.conf
cp $REDIS_HOME/redis.conf $REDIS_HOME/redis2.conf
cp $REDIS_HOME/redis.conf $REDIS_HOME/redis3.conf 


vim $REDIS_HOME/redis1.conf
port 7001
cluster-enabled yes
cluster-config-file nodes1.conf
cluster-node-timeout 5000
appendonly yes

vim $REDIS_HOME/redis2.conf
port 7002
cluster-enabled yes
cluster-config-file nodes2.conf
cluster-node-timeout 5000
appendonly yes

vim $REDIS_HOME/redis3.conf
port 7003
cluster-enabled yes
cluster-config-file nodes3.conf
cluster-node-timeout 5000
appendonly yes

# 저장 후 
redis-server $REDIS_HOME/redis1.conf &
redis-server $REDIS_HOME/redis2.conf &
redis-server $REDIS_HOME/redis3.conf &
redis-cli --cluster create 127.0.0.1:6301 127.0.0.1:6302 127.0.0.1:6303
```

## 클러스터 구성 확인

```shell script
redis-cli -p 7001
cluster nodes 
```

## 클러스터 노드 추가하기
```shell script
redis-cli --cluster add-node 127.0.0.1:7006 127.0.0.1:7000 --cluster-slave --cluster-master-id 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e

# 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e 이 값은 cluster nodes 에서 조회하면 해시 값을 나타낸다. 
```
