# bitbake u-boot-imx -c fetch

# UBOOT_SRC = "git://git@192.168.1.156:8989/com_rd/dcu_rd/os/i.mx9/imx9-uboot.git;protocol=ssh"
UBOOT_SRC = "git://git@192.168.1.156/com_rd/dcu_rd/os/i.mx9/imx9-uboot.git;protocol=ssh"
SRC_URI = "${UBOOT_SRC};branch=develop_v2025.04"

# 最新提交
SRCREV = "${AUTOREV}"

# 跳过 URI 格式检查
INSANE_SKIP:${PN} += "src-uri-bad"