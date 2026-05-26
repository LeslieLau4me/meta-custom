# hostapd_%.bbappend
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# 添加自定义配置文件
SRC_URI:append = " file://hostapd.conf"

# 覆盖配置文件
do_install:append() {
    # 原始配方使用 UNPACKDIR，新版本中 UNPACKDIR 可能指向 WORKDIR
    CUSTOM_CONF="${UNPACKDIR}/hostapd.conf"
    
    if [ ! -f "$CUSTOM_CONF" ]; then
        CUSTOM_CONF="${WORKDIR}/hostapd.conf"
    fi
    
    if [ -f "$CUSTOM_CONF" ]; then
        install -m 0600 $CUSTOM_CONF ${D}${sysconfdir}/hostapd.conf
        bbnote "Installed custom hostapd.conf"
    else
        bbnote "Custom hostapd.conf not found, using default"
        # 如果找不到，保持原始配方的行为
        if [ -f ${UNPACKDIR}/hostapd.conf.default ] || [ -f ${S}/hostapd/hostapd.conf ]; then
            bbnote "Using default configuration"
        fi
    fi
}