# Redis 설치 방법

2가지 설치 방법이 있다.
1. binary install
2. docker install
 
## binary install

```shell script
wget http://download.redis.io/releases/redis-5.0.8.tar.gz
tar xvzf redis-5.0.8.tar.gz
cd redis-5.0.8
make
src/redis-server &
```

## docker install
```shell script
docker run --name redis -d -p 6379:6379 redis
```

:::tip
기본 포트 6379
:::
