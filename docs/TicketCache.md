## Before caching

- with 1000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/2" | vegeta attack -name=2000qps -duration=10s -rate=1000 | tee benchmark/results_200qps.bin | vegeta report                                                                           12:59:33
tee: benchmark/results_200qps.bin: No such file or directory
Requests      [total, rate, throughput]         10000, 1000.07, 999.43
Duration      [total, attack, wait]             10.006s, 9.999s, 6.408ms
Latencies     [min, mean, 50, 90, 95, 99, max]  2.316ms, 551.354ms, 498.324ms, 1.117s, 1.279s, 1.748s, 3.026s
Bytes In      [total, mean]                     2890000, 289.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:10000
```

- with 5000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/2" | vegeta attack -name=2000qps -duration=10s -rate=5000 | tee benchmark/results_200qps.bin | vegeta report                                                             ✔ 0|0|1|0 10s 13:00:01
tee: benchmark/results_200qps.bin: No such file or directory
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

- with 10000 QPS

```
echo "GET http://localhost:8080/api/v1/tickets/2" | vegeta attack -name=2000qps -duration=10s -rate=10000 | tee benchmark/results_200qps.bin | vegeta report                                                         ✔ 0|0|1|0 40s 13:01:01
tee: benchmark/results_200qps.bin: No such file or directory
Requests      [total, rate, throughput]         25058, 2484.54, 702.82
Duration      [total, attack, wait]             14.943s, 10.086s, 4.857s
Latencies     [min, mean, 50, 90, 95, 99, max]  1.053ms, 4.787s, 5.055s, 11.417s, 11.682s, 12.118s, 14.569s
Bytes In      [total, mean]                     3035078, 121.12
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           41.91%
Status Codes  [code:count]                      0:14556  200:10502
```

## Normal Caching (Cache - Aside Pattern)

- with 1000 QPS - absolutely work fine
- with 5000 QPS - failed with 2.3% error rate

```
echo "GET http://localhost:8080/api/v1/tickets/2" | vegeta attack -name=2000qps -duration=10s -rate=5000 | tee benchmark/results_200qps.bin | vegeta report                                                         10s 13:02:34
Requests      [total, rate, throughput]         42773, 4042.23, 1137.47
Duration      [total, attack, wait]             36.719s, 10.582s, 26.138s
Latencies     [min, mean, 50, 90, 95, 99, max]  3.087ms, 2.383s, 823.027ms, 6.105s, 6.313s, 6.383s, 30.013s
Bytes In      [total, mean]                     12028896, 281.23
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           97.65%
Status Codes  [code:count]                      0:1006  200:41767
Error Set:
Get "http://localhost:8080/api/v1/tickets/2": dial tcp 0.0.0.0:0->127.0.0.1:8080: bind: can't assign requested address
Get "http://localhost:8080/api/v1/tickets/2": context deadline exceeded (Client.Timeout exceeded while awaiting headers)
```

- with 10000 QPS - failed with 34% error rate

```
echo "GET http://localhost:8080/api/v1/tickets/2" | vegeta attack -name=2000qps -duration=10s -rate=10000 | tee benchmark/results_200qps.bin | vegeta report                                                        37s 13:03:54
Requests      [total, rate, throughput]         31037, 3070.17, 645.75
Duration      [total, attack, wait]             31.735s, 10.109s, 21.626s
Latencies     [min, mean, 50, 90, 95, 99, max]  587.083µs, 4.747s, 3.642s, 10.172s, 10.225s, 10.283s, 30.005s
Bytes In      [total, mean]                     5901984, 190.16
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           66.03%
Status Codes  [code:count]                      0:10544  200:20493
Error Set:
Get "http://localhost:8080/api/v1/tickets/2": dial tcp 0.0.0.0:0->[::1]:8080: bind: resource temporarily unavailable
Get "http://localhost:8080/api/v1/tickets/2": dial tcp 0.0.0.0:0->[::1]:8080: connect: operation timed out
Get "http://localhost:8080/api/v1/tickets/2": context deadline exceeded (Client.Timeout exceeded while awaiting headers)
```

## Advanced Caching (Distributed Lock with Redisson)
