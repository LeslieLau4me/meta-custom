FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://lighttpd.conf \
    git://git@192.168.1.10:8822/webserver/html.git;protocol=ssh;branch=feature-yocto-custom;destsuffix=html \
"
SRCREV = "${AUTOREV}"

do_install:append() {
    # 创建网站根目录
    install -d ${D}/var/www/html
    
    # 安装自定义配置文件
    install -d ${D}${sysconfdir}/lighttpd
    install -m 0644 ${UNPACKDIR}/lighttpd.conf ${D}${sysconfdir}/lighttpd/lighttpd.conf
    
    # 复制 HTML 项目
    cp -r ${UNPACKDIR}/html/* ${D}/var/www/html/
    
    # /run 目录不需要创建，systemd 会在运行时自动创建
    # 也不需要 FILES 声明
}

inherit systemd
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

# 只需要声明网站文件
FILES:${PN} += "/var/www/html"