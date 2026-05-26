SUMMARY = "Realtek HCI Attach for Bluetooth"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "file://rtk_hciattach"

S = "${WORKDIR}/rtk_hciattach"

EXTRA_OEMAKE = ""

do_compile() {
    oe_runmake CC="${CC}" CFLAGS="${CFLAGS}"
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 hciattach_quectel ${D}${sbindir}/
}

FILES:${PN} = "${sbindir}/hciattach_quectel"