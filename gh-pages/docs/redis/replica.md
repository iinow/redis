# 레플리카 설정하기

## 설정하기

* 마스터 서버에서 데이터 입력이 들어오면 비동기로 복제본 서버 복사가 된다. 
* 아래 `redis.conf` 파일 내용을 확인해 보면 `#replicaof <masterip> <masterport>` 주석 처리되어 있는데 수정해서 사용하며 된다.

```shell script
vim $REDIS_HOME/redis.conf

# replicaof <masterip> <masterport>
replicaof 127.0.0.1 6301
```

* 사용할 때 마스터 서버를 실행한 후 복제본 서버를 실행한다. 

## 레플리카 옵션

* 기본적으로 `replica-read-only` 복제본 서버는 read 만 가능한데 설정으로 변경할 수 있다.

```shell script
vim $REDIS_HOME/redis.conf

replica-read-only yes
``` 
