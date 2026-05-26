# 强制使用本地下载好的包
# 从 DL_DIR 直接取，永不联网
SRC_URI = "file://${DL_DIR}/hiredis-1.2.0.tar.gz"

# 你自己计算 sha256sum
SRC_URI[sha256sum] = "82ad632d31ee05da13b537c124f819eb88e18851d9cb0c30ae0552084811588c"

# 源码目录
S = "${WORKDIR}/hiredis-1.2.0"
