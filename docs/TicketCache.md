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

### with 7000 QPS - failed with 41.04% error rate

```
echo "GET http://localhost:8080/api/v1/tickets/1" | vegeta attack -duration=10s -rate=7000 | vegeta report
Requests      [total, rate, throughput]         29546, 2931.04, 1239.88
Duration      [total, attack, wait]             14.051s, 10.08s, 3.97s
Latencies     [min, mean, 50, 90, 95, 99, max]  928.833µs, 5.305s, 7.813s, 10.64s, 10.896s, 11.199s, 11.867s
Bytes In      [total, mean]                     5104353, 172.76
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           58.96%
Status Codes  [code:count]                      0:12125  200:17421
Error Set:
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: bind: resource temporarily unavailable
Get "http://localhost:8080/api/v1/tickets/1": dial tcp 0.0.0.0:0->[::1]:8080: connect: operation timed out
```
