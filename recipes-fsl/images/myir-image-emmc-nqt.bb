require recipes-fsl/images/myir-image-emmc.bb

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
"

IMAGE_INSTALL:remove = " \
    tools-testapps \
    tools-profile \
    serialcheck \
    libdrm-tests \
    tslib tslib-calibrate tslib-conf tslib-tests \
    myir-lvgl \
    alsa-utils \
    auto-wifi \
    myir-test-function \
    opencv \
    graphics \
    multimedia \
"

IMAGE_INSTALL:append = " \
    redis \
    redis-plus-plus \
    jq \
"

IMAGE_INSTALL += "rtk-hciattach"

IMAGE_FEATURES:remove = "dev-pkgs staticdev-pkgs doc-pkgs man-pkgs locale-pkgs debug-tweaks"
IMAGE_LINGUAS = "en-us"

IMAGE_OVERHEAD_FACTOR = "1.0"


# IMAGE_FSTYPES = "ext4.gz"
IMAGE_FSTYPES += "wic wic.zst"
IMAGE_ROOTFS_SIZE = "819200"

NETWORK_FILES_SRC := "${THISDIR}/files/systemd"

copy_systemd_network_configs() {
    # 需要手动创建的目录
    install -d ${IMAGE_ROOTFS}/usr/local/bin

    # 复制网络配置文件
    cp -f ${NETWORK_FILES_SRC}/10-eth0.network      ${IMAGE_ROOTFS}/etc/systemd/network/
    cp -f ${NETWORK_FILES_SRC}/11-eth0-dhcp.network  ${IMAGE_ROOTFS}/etc/systemd/network/

    # 复制脚本到标准路径
    cp -f ${NETWORK_FILES_SRC}/set_static_ip.sh      ${IMAGE_ROOTFS}/usr/local/bin/
    cp -f ${NETWORK_FILES_SRC}/set_dhcp.sh           ${IMAGE_ROOTFS}/usr/local/bin/
    cp -f ${NETWORK_FILES_SRC}/network-config.sh     ${IMAGE_ROOTFS}/usr/local/bin/

    # systemd服务
    cp -f ${NETWORK_FILES_SRC}/network-config.service ${IMAGE_ROOTFS}/etc/systemd/system/

    # 执行权限
    chmod 755 ${IMAGE_ROOTFS}/usr/local/bin/*.sh

    # 启用开机自启
    systemctl --root=${IMAGE_ROOTFS} enable network-config.service
}

ROOTFS_POSTPROCESS_COMMAND += "copy_systemd_network_configs;"