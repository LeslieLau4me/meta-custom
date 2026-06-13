SUMMARY = "Quectel/Realtek HCI Attach for Bluetooth"
LICENSE = "CLOSED"

#SRC_URI = "git://git@192.168.1.156:8989/com_rd/dcu_rd/os/i.mx9/quectel-hciattach.git;protocol=ssh;branch=yocto-fix"
SRC_URI = "git://git@192.168.1.156/com_rd/dcu_rd/os/i.mx9/quectel-hciattach.git;protocol=ssh;branch=yocto-fix"

# 可选：指定 patch 的搜索路径
# FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
S = "${WORKDIR}/git"
SRCREV = "${AUTOREV}"

# 关键是设置 CROSS_COMPILE 环境变量
do_compile() {
    cd ${S}
    
    # 设置 CROSS_COMPILE 变量并编译
    export CROSS_COMPILE="${TARGET_PREFIX}"
    oe_runmake CC="${CC}" CFLAGS="${CFLAGS}"
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 hciattach_quectel ${D}${sbindir}/
}

FILES:${PN} = "${sbindir}/hciattach_quectel"