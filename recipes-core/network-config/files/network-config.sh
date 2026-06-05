#!/bin/sh
set -eu

CONFIG=${1:-/etc/network_conf.json}
OUTDIR=${2:-/etc/systemd/network}

if [ ! -f "$CONFIG" ]; then
    echo "ERROR: config file not found: $CONFIG" >&2
    exit 1
fi

mkdir -p "$OUTDIR"
find "$OUTDIR" -maxdepth 1 -type f -name '10-network-*.network' -delete

mask2prefix() {
    if [ -z "$1" ]; then
        return 1
    fi
    printf '%s\n' "$1" | awk -F. '{
        c=0
        for (i=1; i<=4; i++) {
            m=$i
            while (m > 0) {
                c += m % 2
                m = int(m / 2)
            }
        }
        print c
    }'
}

set_mac_address() {
    iface="$1"
    mac="$2"

    if [ -n "$iface" ] && [ -n "$mac" ]; then
        ip link set dev "$iface" address "$mac"
    fi
}

jq -c '.interfaces | to_entries[]' "$CONFIG" | while IFS= read -r entry; do
    iface=$(printf '%s' "$entry" | jq -r '.key')
    mac=$(printf '%s' "$entry" | jq -r '.value.mac_address // empty')
    ip=$(printf '%s' "$entry" | jq -r '.value.ip_address // empty')
    mask=$(printf '%s' "$entry" | jq -r '.value.subnet_mask // empty')
    gateway=$(printf '%s' "$entry" | jq -r '.value.gateway // empty')
    dhcp=$(printf '%s' "$entry" | jq -r '.value.dhcp_enable // false')

    dns_servers=$(printf '%s' "$entry" | jq -r '.value.dns_servers // [] | .[]')
    routes=$(printf '%s' "$entry" | jq -c '.value.routes // [] | .[]')
    target="$OUTDIR/10-network-${iface}.network"

    set_mac_address "$iface" "$mac"

    {
        echo '[Match]'
        echo "Name=${iface}"
        # [ -n "$mac" ] && echo "MACAddress=${mac}"
        echo
        echo '[Network]'
        if [ "$dhcp" = "true" ] || [ "$dhcp" = "yes" ] || [ "$dhcp" = "1" ]; then
            echo 'DHCP=ipv4'
        else
            echo 'DHCP=no'
            if [ -n "$ip" ] && [ -n "$mask" ]; then
                prefix=$(mask2prefix "$mask")
                echo "Address=${ip}/${prefix}"
            fi
            [ -n "$gateway" ] && echo "Gateway=${gateway}"
            if [ -n "$dns_servers" ]; then
                printf '%s\n' "$dns_servers" | while IFS= read -r dns; do
                    [ -n "$dns" ] && echo "DNS=${dns}"
                done
            fi
        fi

        if [ -n "$routes" ]; then
            printf '%s\n' "$routes" | while IFS= read -r route; do
                dest=$(printf '%s' "$route" | jq -r '.destination // empty')
                gw=$(printf '%s' "$route" | jq -r '.gateway // empty')
                metric=$(printf '%s' "$route" | jq -r '.metric // empty')
                if [ -n "$dest" ] && [ -n "$gw" ]; then
                    echo
                    echo '[Route]'
                    echo "Destination=${dest}"
                    echo "Gateway=${gw}"
                    [ -n "$metric" ] && echo "Metric=${metric}"
                fi
            done
        fi
    } > "$target"
    chmod 0644 "$target"
done

exit 0
