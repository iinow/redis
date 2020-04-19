# 비밀번호 설정하기

1. redis.conf 파일에서 비밀번호 설정하기

2. redis-cli 접근해서 비밀번호 설정하기

## redis.conf

```shell script
vim $REDIS_HOME/redis.conf

# 열고 아래 내용을 추가하면 된다. 
requirepass [비밀번호]

# 저장 후 실행 할 때 conf 파일을 로드하면 된다. 
redis-server $REDIS_HOME/redis.conf
```

## redis-cli 

* 아래와 같이 쌍따옴표 안에 비밀번호를 입력하고 Enter 누르면 된다. 
```shell script
redis-cli
CONFIG SET requirepass "123456"
```
