
# bitbake linux-imx -c cleanall
# bitbake linux-imx -c fetch
# bitbake linux-imx
#SRC_URI = "git://git@192.168.1.156:8989/com_rd/dcu_rd/os/i.mx9/imx9-kernel.git;protocol=ssh;branch=develop_6.12.49"

SRC_URI = "git://git@192.168.1.156/com_rd/dcu_rd/os/i.mx9/imx9-kernel.git;protocol=ssh;branch=develop_6.12.49"
# 最新提交
SRCREV = "${AUTOREV}"

INSANE_SKIP:${PN} += "src-uri-bad"