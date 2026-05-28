# bitbake u-boot-imx -c fetch
UBOOT_SRC = "git://git@192.168.1.10:8822/dcu/imx9-uboot.git;protocol=ssh"
SRC_URI = "${UBOOT_SRC};branch=develop_v2025.04" 

# 最新提交
SRCREV = "${AUTOREV}"

INSANE_SKIP:${PN} += "src-uri-bad"