SUMMARY = "DBus Wrapper Library"
DESCRIPTION = "A C library wrapper for DBus asynchronous functionality"
LICENSE = "CLOSED"

SRC_URI = "git://git@192.168.1.10:8822/dcu/dbuswrap_c.git;protocol=ssh;branch=main"

S = "${WORKDIR}/git"

# 不需要 SRCREV 使用 main 分支的最新提交
# 或者指定具体提交
SRCREV = "${AUTOREV}"

DEPENDS = " \
    dbus \
    glib-2.0 \
    libevent \
"

RDEPENDS:${PN} = " \
    dbus \
    glib-2.0 \
    libevent \
"

inherit cmake pkgconfig

OECMAKE_SOURCEPATH = "${S}"

EXTRA_OECMAKE = " \
    -DCMAKE_INSTALL_PREFIX=${prefix} \
    -DCMAKE_BUILD_TYPE=Release \
"

# 使用模式匹配
FILES:${PN} = " \
    ${libdir}/libdbuswrap.so.[0-9]* \
    ${libdir}/libdbuswrap.so.[0-9].[0-9]* \
"

FILES:${PN}-dev = " \
    ${libdir}/libdbuswrap.so \
    ${includedir}/* \
    ${libdir}/pkgconfig/* \
    ${datadir}/cmake/* \
"