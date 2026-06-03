#!/bin/sh

ip route add 192.168.10.0/24 via 192.168.12.1 dev eth0
iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE

echo "route/iptables config successfully"
