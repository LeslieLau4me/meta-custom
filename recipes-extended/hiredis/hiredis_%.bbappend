# 如果源码在 ${WORKDIR}/git 或其他位置，指定正确的 S 路径
S = "${WORKDIR}/git"

# 或者如果源码直接在 ${WORKDIR} 下解压
# S = "${WORKDIR}/${PN}-${PV}"