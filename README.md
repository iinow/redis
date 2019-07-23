# redis

## 데이터 타입 
참조 : [https://kimpaper.github.io/2016/07/27/redis-datatype/]

- Strings
    - set
    - get
    - incr
    - incrby
    - decr
    - decrby
- Lists
    - lpush
    - lrange
    - rpush
    - rpop
- Sets
    - sadd
    - smembers
- hashes
    - hset
    - hget
    - hgetall 
- Sorted Sets
    - zadd
    - zrange
- Bitmaps
    - setbit
    - getbit

Redis Event 종류

message : Channel 에서 온 메시지 

subscribe : Channel 이름으로 구독한다.

psubscribe : Channel 이름 패턴으로 검색해서 구독한다. 