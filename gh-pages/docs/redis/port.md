# 포트 변경하기

아래와 같이 2가지 포트 변경 방법이 있다. 그냥 깔끔하게 파일로 관리하는게 좋은 것 같다.

1. 실행시 포트 지정
2. redis.conf 파일로 포트 지정

## 실행시 포트 지정
```shell script
./redis-server --port 6380
```

## redis.conf 파일로 포트 지정
```shell script
vim $REDIS_HOME/redis.conf

# port 값 변경 후 저장
port 6301

./redis-server $REDIS_HOME/redis.conf
```

## 접근시

```shell script
redis-cli -h [IP주소] -p [변경한 포트 번호]
```
