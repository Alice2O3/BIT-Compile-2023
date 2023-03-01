import os

def parse_line(str):
    return list(map(int, str.split(" ")))

with open("../../data.txt", 'r') as f:
    n, m = parse_line(f.readline())
    Ma = []
    for i in range(n):
        v = parse_line(f.readline())
        Ma.append(v)
    m2, t = parse_line(f.readline())
    Mb = []
    for i in range(m2):
        v = parse_line(f.readline())
        Mb.append(v)

Mc = [[0 for x in range(t)] for y in range(n)]
for i in range(n):
    for j in range(t):
        for k in range(m):
            Mc[i][j] += Ma[i][k] * Mb[k][j]

