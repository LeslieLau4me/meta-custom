#!/bin/sh
set -eu
CONFIG=/etc/network_conf.json
iface=${1:-eth0}

tmpfile=$(mktemp)
jq --arg iface "$iface" '
  (.interfaces[$iface].dhcp_enable) = true
  | (.interfaces[$iface].ip_address) = ""
  | (.interfaces[$iface].subnet_mask) = ""
  | (.interfaces[$iface].gateway) = ""
' "$CONFIG" > "$tmpfile"

mv "$tmpfile" "$CONFIG"

/usr/bin/network-config.sh "$CONFIG" /etc/systemd/network
systemctl restart systemd-networkd

echo "[OK] Set DHCP mode for $iface"
