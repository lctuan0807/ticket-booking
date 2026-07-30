# Before caching

## Vegeta

### with 1000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=1000 | vegeta report
Requests      [total, rate, throughput]         9994, 999.09, 685.76
Duration      [total, attack, wait]             14.034s, 10.003s, 4.031s
Latencies     [min, mean, 50, 90, 95, 99, max]  23.132ms, 2.048s, 2.126s, 3.826s, 4.112s, 6s, 7.473s
Bytes In      [total, mean]                     2866826, 286.85
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           96.30%
Status Codes  [code:count]                      200:9624  500:370
```

### with 5000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/2" | vegeta attack -duration=10s -rate=5000 | vegeta report
Requests      [total, rate, throughput]         25476, 2545.08, 351.25
Duration      [total, attack, wait]             39.983s, 10.01s, 29.973s
Latencies     [min, mean, 50, 90, 95, 99, max]  843µs, 6.285s, 7.801s, 11.617s, 11.752s, 11.945s, 30.008s
Bytes In      [total, mean]                     4058716, 159.32
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           55.13%
Status Codes  [code:count]                      0:11432  200:14044
Error Set:
Get "http://localhost:8080/api/v1/tickets/2": dial tcp 0.0.0.0:0->[::1]:8080: bind: resource temporarily unavailable
Get "http://localhost:8080/api/v1/tickets/2": dial tcp 0.0.0.0:0->[::1]:8080: connect: operation timed out
Get "http://localhost:8080/api/v1/tickets/2": context deadline exceeded (Client.Timeout exceeded while awaiting headers)
```

# Normal Caching (Cache - Aside Pattern)

### with 1000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=1000 | vegeta report
Requests      [total, rate, throughput]         10000, 999.98, 998.33
Duration      [total, attack, wait]             10.017s, 10s, 16.486ms
Latencies     [min, mean, 50, 90, 95, 99, max]  844.917µs, 121.049ms, 3.71ms, 495.7ms, 612.847ms, 764.523ms, 933.695ms
Bytes In      [total, mean]                     2930045, 293.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:10000
```

### with 3000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=3000 | vegeta report
Requests      [total, rate, throughput]         29996, 2999.37, 2847.47
Duration      [total, attack, wait]             10.534s, 10.001s, 533.502ms
Latencies     [min, mean, 50, 90, 95, 99, max]  915.042µs, 555.568ms, 560.654ms, 849.22ms, 923.443ms, 1.009s, 1.159s
Bytes In      [total, mean]                     8788828, 293.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:29996
```

### with 5000 QPS - failed with 10.51% error rate

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=5000 | vegeta report
Requests      [total, rate, throughput]         25590, 2481.52, 1342.07
Duration      [total, attack, wait]             17.063s, 10.312s, 6.751s
Latencies     [min, mean, 50, 90, 95, 99, max]  1.293ms, 5.536s, 5.199s, 11.694s, 11.993s, 12.208s, 13.093s
Bytes In      [total, mean]                     6709700, 262.20
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           89.49%
Status Codes  [code:count]                      0:2690  200:22900
Error Set:
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: bind: resource temporarily unavailable
```

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=5000 | vegeta report
Requests      [total, rate, throughput]         23226, 1842.31, 304.88
Duration      [total, attack, wait]             37.011s, 12.607s, 24.404s
Latencies     [min, mean, 50, 90, 95, 99, max]  1.232ms, 15.254s, 13.268s, 30.006s, 30.024s, 30.224s, 30.346s
Bytes In      [total, mean]                     3593111, 154.70
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           48.58%
Status Codes  [code:count]                      0:11674  200:11284  500:268
Error Set:
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->127.0.0.1:8080: bind: can't assign requested address
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: bind: resource temporarily unavailable
500
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: connect: operation timed out
Get "http://localhost:8080/api/v1/tickets/1": context deadline exceeded (Client.Timeout exceeded while awaiting headers)

Hit DB ~ 300 times
```

# Cache with Distributed Locking (Redisson)

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=5000 | vegeta report
Requests      [total, rate, throughput]         20768, 2055.64, 622.08
Duration      [total, attack, wait]             25.395s, 10.103s, 15.292s
Latencies     [min, mean, 50, 90, 95, 99, max]  1.241ms, 13.793s, 16.7s, 20.655s, 21.495s, 22.002s, 24.52s
Bytes In      [total, mean]                     5079130, 244.57
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           76.07%
Status Codes  [code:count]                      0:4109  200:15798  500:861
Error Set:
500
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: bind: resource temporarily unavailable
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: connect: operation timed out

Hit DB ~ 1-2 times
```
