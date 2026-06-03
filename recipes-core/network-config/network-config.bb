SUMMARY = "Network configuration service and scripts"
DESCRIPTION = "Provides network configuration service for static/dhcp IP setup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://10-eth0.network \
    file://11-eth0-dhcp.network \
    file://set_static_ip.sh \
    file://set_dhcp.sh \
    file://network-config.sh \
    file://network-config.service \
"

inherit systemd

SYSTEMD_SERVICE:${PN} = "network-config.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    # 直接使用 UNPACKDIR
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}/etc/systemd/network
    install -d ${D}${bindir}
    
    # 从 UNPACKDIR 安装
    install -m 0644 ${UNPACKDIR}/10-eth0.network ${D}/etc/systemd/network/
    install -m 0644 ${UNPACKDIR}/11-eth0-dhcp.network ${D}/etc/systemd/network/
    
    install -m 0755 ${UNPACKDIR}/set_static_ip.sh ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/set_dhcp.sh ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/network-config.sh ${D}${bindir}/
    
    install -m 0644 ${UNPACKDIR}/network-config.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} = " \
    /etc/systemd/network/* \
    ${bindir}/* \
    ${systemd_system_unitdir}/network-config.service \
"

RDEPENDS:${PN} = "systemd"
