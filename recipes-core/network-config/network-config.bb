SUMMARY = "Network configuration service and scripts"
DESCRIPTION = "Provides network configuration service for static/dhcp IP setup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://network_conf.json \
    file://set_static_ip.sh \
    file://set_dhcp.sh \
    file://network-config.sh \
    file://network-config.service \
"

inherit systemd
S = "${UNPACKDIR}"
# No upstream source tree; use WORKDIR directly for unpacked recipe assets

SYSTEMD_SERVICE:${PN} = "network-config.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}/etc/systemd/network
    install -d ${D}${bindir}
    install -d ${D}/etc
    install -m 0644 ${UNPACKDIR}/network_conf.json ${D}/etc/
    install -m 0755 ${UNPACKDIR}/set_static_ip.sh ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/set_dhcp.sh ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/network-config.sh ${D}${bindir}/
    install -m 0644 ${UNPACKDIR}/network-config.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} = " \
    /etc/network_conf.json \
    /etc/systemd \
    /etc/systemd/network \
    /etc/systemd/network/* \
    ${bindir}/* \
    ${systemd_system_unitdir}/network-config.service \
"

RDEPENDS:${PN} = "systemd jq"
DEPENDS += "jq-native"

# Note: generation now performed during do_install to avoid override-syntax issues
