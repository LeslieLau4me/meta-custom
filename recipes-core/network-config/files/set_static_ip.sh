#!/bin/sh

set -eu
CONFIG=/etc/network_conf.json
iface=${1:-eth0}
ip_address=${2:?usage: $0 <iface> <ip_address> <subnet_mask> [gateway]}
subnet_mask=${3:?usage: $0 <iface> <ip_address> <subnet_mask> [gateway]}
gateway=${4:-}

tmpfile=$(mktemp)
jq --arg iface "$iface" \
   --arg ip "$ip_address" \
   --arg mask "$subnet_mask" \
   --arg gateway "$gateway" '
  (.interfaces[$iface].ip_address) = $ip
  | (.interfaces[$iface].subnet_mask) = $mask
  | (.interfaces[$iface].gateway) = $gateway
  | (.interfaces[$iface].dhcp_enable) = false
' "$CONFIG" > "$tmpfile"

mv "$tmpfile" "$CONFIG"

/usr/bin/network-config.sh "$CONFIG" /etc/systemd/network
systemctl restart systemd-networkd

echo "[OK] Set static IP for $iface: $ip_address/$subnet_mask gateway=$gateway"
