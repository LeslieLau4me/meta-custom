# redis_%.bbappend
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:remove = "http://download.redis.io/releases/${BP}.tar.gz"
SRC_URI:append = " file://${DL_DIR}/redis-7.2.8.tar.gz"

SRC_URI:append = " file://redis.conf"

SRC_URI[sha256sum] = "66edcb676acf0f611e1cd8d69aa294c0256cb1658094c73bad529d1546eb89ac"

# 确保使用正确的 S 路径
S = "${WORKDIR}/redis-${PV}"

# 禁用 jemalloc
EXTRA_OEMAKE += "MALLOC=libc"

# 覆盖配置文件
do_install:append() {
    # 原始配方使用 UNPACKDIR，新版本中 UNPACKDIR 可能指向 WORKDIR
    CUSTOM_CONF="${UNPACKDIR}/redis.conf"
    
    if [ ! -f "$CUSTOM_CONF" ]; then
        CUSTOM_CONF="${WORKDIR}/redis.conf"
    fi
    
    if [ -f "$CUSTOM_CONF" ]; then
        install -m 0644 $CUSTOM_CONF ${D}${sysconfdir}/redis/redis.conf
        bbnote "Installed custom redis.conf"
    else
        bbnote "Custom redis.conf not found, using default"
        # 如果找不到，保持原始配方的行为
        if [ -f ${UNPACKDIR}/redis.conf.default ]; then
            install -m 0644 ${UNPACKDIR}/redis.conf.default ${D}${sysconfdir}/redis/redis.conf
        fi
    fi
}

inherit systemd
# 启用 systemd 开机自启
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

