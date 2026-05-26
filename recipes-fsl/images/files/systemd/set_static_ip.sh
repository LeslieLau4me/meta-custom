#!/bin/sh
chmod 644 /etc/systemd/network/10-eth0.network
chmod 000 /etc/systemd/network/11-eth0-dhcp.network
systemctl restart systemd-networkd
echo "[OK] Switch to staitic IP:192.168.12.100"
