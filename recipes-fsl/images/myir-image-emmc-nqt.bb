require recipes-fsl/images/myir-image-emmc.bb

# 移除不需要的包
IMAGE_INSTALL:remove = " \
    packagegroup-qt6-imx \
    qtbase qtdeclarative qttools \
    weston xserver-xorg xinit \
    alsa-lib pulseaudio \
    python3-cryptography \
    python3-cryptography-native \
    optee-test \
    optee-examples \
    uutils-coreutils \
    python3-maturin \
    tslib \
    tslib-calibrate \
    tslib-conf \
    tslib-tests \
    myir-lvgl \
    packagegroup-imx-ml \
    tools-testapps \
    tools-profile \
    serialcheck \
    libdrm-tests \
    alsa-utils \
    auto-wifi \
    myir-test-function \
    opencv \
    graphics \
    multimedia \
"

# 添加需要的包（注意：不要包含 custom-fstab）
IMAGE_INSTALL:append = " \
    redis \
    redis-plus-plus \
    jq \
    dbuswrap \
    rtk-hciattach \
    lighttpd \
    php-cgi \
    php-fpm \
    network-config \
"

# 镜像配置
IMAGE_FEATURES:remove = "dev-pkgs staticdev-pkgs doc-pkgs man-pkgs locale-pkgs debug-tweaks"
IMAGE_LINGUAS = "en-us"
IMAGE_OVERHEAD_FACTOR = "1.0"
IMAGE_FSTYPES += "wic wic.zst"
IMAGE_ROOTFS_SIZE = "819200"

# 开机自启动 lighttpd
SYSTEMD_AUTO_ENABLE:pn-php-fpm = "enable"

# 使用自定义 WKS 文件
WKS_FILE = "imx93-custom.wks.in"
WKS_SEARCH_PATH:prepend = "${THISDIR}/../../wic:"

# 添加自定义 fstab 和目录
add_custom_fstab() {
    install -d ${IMAGE_ROOTFS}/mnt/data_bak
    cat >>${IMAGE_ROOTFS}/etc/fstab <<EOF
/dev/mmcblk0p3  /mnt/data_bak  ext4  defaults,nofail  0  0
/dev/mmcblk0p4  /var/log       ext4  defaults,nofail  0  0
EOF
}

ROOTFS_POSTPROCESS_COMMAND += "add_custom_fstab;"